package com.heaven7.ve.colorgap;

import com.heaven7.java.base.util.Logger;
import com.heaven7.utils.CommonUtils;
import com.heaven7.utils.Context;
import com.heaven7.ve.cross_os.IMediaResourceItem;
import com.heaven7.ve.cross_os.ITimeTraveller;
import com.heaven7.ve.cross_os.VEFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by heaven7 on 2018/3/15 0015.
 */

public class MediaItem implements CutItemDelegate{

   // public static final int FLAG_HOLD     = 0x0001;
    public static final int FLAG_INVALID  = 0x0002;

    public MetaInfo.ImageMeta imageMeta;
    public IMediaResourceItem item; //path, duration, date
    private List<ITimeTraveller> videoParts;  //切割后的parts.
    private int flags;

    public void clearFlags(){
        flags = 0;
    }
    public void deleteFlags(int flags){
        this.flags &= ~flags;
    }
    public void addFlags(int flags){
        this.flags |= flags;
    }
    public boolean isValid(){
        return (flags & FLAG_INVALID) != FLAG_INVALID;
    }
    public void addVideoPart(ITimeTraveller part) {
        if(videoParts == null){
            videoParts = new ArrayList<>();
        }
        videoParts.add(part);
    }

    public List<ITimeTraveller> getVideoParts() {
        return videoParts;
    }

    public void dump() {
        final long maxDuration = CommonUtils.timeToFrame(item.getDuration(), TimeUnit.MILLISECONDS);
        Logger.d("MediaItem", "dump", "video path: " + item.getFilePath() + " ---- \n total duration(in frames) = " + maxDuration
                + " ,parts = " + videoParts);
    }

    public MediaPartItem asPart(Context context) {
        ITimeTraveller tt = VEFactory.getDefault().newTimeTraveller();
        tt.setStartTime(0);
        tt.setEndTime(CommonUtils.timeToFrame(item.getDuration(), TimeUnit.MILLISECONDS));
        return new MediaPartItem(context, (MetaInfo.ImageMeta) imageMeta.copy(), this.item, tt);
    }

    @Override
    public MetaInfo.ImageMeta getImageMeta() {
        return imageMeta;
    }
    @Override
    public List<FrameTags> getVideoTags() {
        return imageMeta.getAllVideoTags();
    }
    @Override
    public IMediaResourceItem getItem() {
        return item;
    }
}
