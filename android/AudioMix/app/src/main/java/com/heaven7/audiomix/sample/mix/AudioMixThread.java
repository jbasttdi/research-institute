package com.heaven7.audiomix.sample.mix;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Process;

import com.heaven7.audiomix.sample.utils.MediaUtils;
import com.heaven7.core.util.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by heaven7 on 2018/12/4 0004.
 */
@TargetApi(18)
/*public*/ class AudioMixThread extends MediaMixThread {

    private static final String TAG = "AudioMixThread";
    private MediaCodec mEncoder;
    private MediaCodec mDecoder;
    private int mTrackIndex = -1;

    public AudioMixThread(String path) {
        super(path);
    }

    @Override
    protected void initImpl(MediaMuxer muxer) throws IOException {
        MediaFormat format = MediaUtils.getMediaFormat(getMediaExtractor(), MediaUtils.TYPE_AUDIO, getPath());
        MediaInfo info = getMediaInfo(format);
        //decoder
        mDecoder = MediaCodec.createDecoderByType(info.mime);
        mDecoder.configure(format, null, null, 0);
        // mDecoder.setCallback();
        mDecoder.start();
        //encoder
        mEncoder = MediaCodec.createByCodecName("OMX.google.aac.encoder");
        //aac
        MediaFormat mediaFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", SAMPLE_RATE, 2);
        mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE);
        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, MAX_INPUT_SIZE);
        mEncoder.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mEncoder.start();

        mTrackIndex = muxer.addTrack(mEncoder.getOutputFormat());
    }

    @Override
    public void release() {
        super.release();
        if (mEncoder != null) {
            mEncoder.stop();
            mEncoder.release();
        }
        if (mDecoder != null) {
            mDecoder.stop();
            mDecoder.release();
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        getMediaMixManageDelegate().markAudioStart();
        switch (getMediaType()) {
            case TYPE_AAC:
            case TYPE_M4A:
                readAndWriteDirectly(mTrackIndex);
                break;

            case TYPE_MP3:
                processSimpleAudio();
                break;

            default:
                throw new UnsupportedOperationException("wrong media type = " + getMediaType());
        }
        getMediaMixManageDelegate().markAudioEnd();
    }

    private void processSimpleAudio() {
        MediaMuxer muxer = getMediaMuxer();
        if (muxer == null || mTrackIndex < 0) {
            return;
        }
        MediaExtractor extractor = getMediaExtractor();
        long startTime = getStartTime();
        extractor.seekTo(startTime > 0 ? startTime : 0, MediaExtractor.SEEK_TO_CLOSEST_SYNC);

        decodeInputBuffer(extractor);
    }

    private void decodeInputBuffer(MediaExtractor extractor) {
        long endTime = getEndTime();
        long duration = getMediaInfo().duration;
        //decode input buffer
        for (; ; ) {
            int inputIndex = mDecoder.dequeueInputBuffer(TIMEOUT_US);
            Logger.d(TAG, "decodeInputBuffer", "inputIndex = " + inputIndex);
            if (inputIndex >= 0) {
                ByteBuffer buffer = getDecodeInputBuffer(inputIndex);
                buffer.clear();
                long sampleTime = extractor.getSampleTime();
                int sampleSize = extractor.readSampleData(buffer, 0);
                Logger.d(TAG, "decodeInputBuffer", "sampleTime = " + sampleTime);
                if (sampleSize > 0) {
                    if (endTime > 0 && sampleTime > endTime) {
                        break;
                    }
                    if(sampleTime > duration){
                        break;
                    }
                    mDecoder.queueInputBuffer(inputIndex, 0, sampleSize, sampleTime, 0);
                    extractor.advance();
                    decodeOutputBuffer();
                }else{
                    break;
                }
            }
        }
    }

    private void decodeOutputBuffer() {
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        int outputIndex = mDecoder.dequeueOutputBuffer(info, -1);
        if (outputIndex >= 0) {
            ByteBuffer buffer = getDecodeOutputBuffer(outputIndex);
            buffer.position(info.offset);
            buffer.limit(info.offset + info.size);
            byte[] chunk = new byte[info.size];
            buffer.get(chunk);
            buffer.clear();
            mDecoder.releaseOutputBuffer(outputIndex, false);
            if (info.size > 0) {
                encodeData(chunk, info.presentationTimeUs);
            }
        }
    }

    private void encodeData(byte[] data, long presentationTimeUs) {
        MediaMuxer muxer = getMediaMuxer();
        int inputIndex = mEncoder.dequeueInputBuffer(-1);
        if (inputIndex >= 0) {
            ByteBuffer buffer = getEncodeInputBuffer(inputIndex);
            buffer.clear();
            buffer.put(data);

            mEncoder.queueInputBuffer(inputIndex, 0, data.length, presentationTimeUs, 0);
        }

        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputIndex = mEncoder.dequeueOutputBuffer(bufferInfo, TIMEOUT_US);
        for (; outputIndex >= 0; ) {
            ByteBuffer outputBuffer = getEncodeOutputBuffer(outputIndex);
            outputBuffer.position(bufferInfo.offset);
            outputBuffer.limit(bufferInfo.offset + bufferInfo.size);

            muxer.writeSampleData(mTrackIndex, outputBuffer, bufferInfo);
            mEncoder.releaseOutputBuffer(outputIndex, false);

            outputIndex = mEncoder.dequeueOutputBuffer(bufferInfo, 0);
        }
    }

    @SuppressWarnings("deprecation")
    private ByteBuffer getDecodeInputBuffer(int index) {
        if (isApi21Above()) {
            return mDecoder.getInputBuffer(index);
        } else {
            return mDecoder.getInputBuffers()[index];
        }
    }

    @SuppressWarnings("deprecation")
    private ByteBuffer getDecodeOutputBuffer(int index) {
        if (isApi21Above()) {
            return mDecoder.getOutputBuffer(index);
        } else {
            return mDecoder.getOutputBuffers()[index];
        }
    }

    @SuppressWarnings("deprecation")
    private ByteBuffer getEncodeInputBuffer(int index) {
        if (isApi21Above()) {
            return mEncoder.getInputBuffer(index);
        } else {
            return mEncoder.getInputBuffers()[index];
        }
    }

    @SuppressWarnings("deprecation")
    private ByteBuffer getEncodeOutputBuffer(int index) {
        if (isApi21Above()) {
            return mEncoder.getOutputBuffer(index);
        } else {
            return mEncoder.getOutputBuffers()[index];
        }
    }

    private static boolean isApi21Above() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
