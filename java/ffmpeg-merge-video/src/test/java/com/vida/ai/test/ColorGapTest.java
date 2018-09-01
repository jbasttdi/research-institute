package com.vida.ai.test;

import com.heaven7.java.visitor.ResultVisitor;
import com.heaven7.java.visitor.collection.VisitServices;
import com.heaven7.utils.FileUtils;
import com.heaven7.ve.MediaResourceItem;
import com.heaven7.ve.collect.ColorGapPerformanceCollector;
import com.heaven7.ve.colorgap.ColorGapContext;
import com.heaven7.ve.colorgap.ColorGapManager;
import com.heaven7.ve.colorgap.ColorGapParam;
import com.heaven7.ve.colorgap.impl.*;
import com.heaven7.ve.starter.KingdomStarter;
import com.heaven7.ve.test.TestUtils;

import java.io.File;
import java.util.List;

/**
 * @author heaven7
 */
public class ColorGapTest {

    private static final SimpleColorGapContext sInitContext = new SimpleColorGapContext();

    static {
        init();
    }

    public static void main(String[] args) {
        String music = "E:\\tmp\\music_cut\\M7.mp3";
        String outDir = "F:\\videos\\ClothingWhite\\works";
        List<String> files = FileUtils.getFiles(new File("F:\\videos\\ClothingWhite"), "mp4");
        List<MediaResourceItem> list = VisitServices.from(files).map(new ResultVisitor<String, MediaResourceItem>() {
            @Override
            public MediaResourceItem visit(String s, Object param) {
                MediaResourceItem item = TestUtils.createVideoItem(s);
                item.setWidth(1280);
                item.setHeight(720);
                return item;
            }
        }).getAsList();
        ColorGapTest cgt = new ColorGapTest();
        cgt.start(music,  list, outDir);
    }

    public void start(String musicPath, List<MediaResourceItem> items, String outDir){
        SimpleColorGapContext context = new SimpleColorGapContext();
        sInitContext.copySystemResource(context);
        context.setKingdom(KingdomStarter.getKingdom(KingdomStarter.TYPE_DRESS));
        context.setColorGapPerformanceCollector(new ColorGapPerformanceCollector());
        //gap
        ColorGapManager cgm = new ColorGapManager(context, new MediaAnalyserImpl(),
               context.getMusicCutter(), new MusicShaderImpl(), new PlaidFillerImpl());
        cgm.setStoryLineShader(new StoryLineShaderImpl());
        cgm.setShotRecognizer(new SimpleShotRecognizer());
        //for batch image .need this.
        cgm.preLoadData(new ColorGapParam.Builder()
                //.setResourceDataDir()
                .build());
        cgm.fill(new String[]{musicPath}, null, items, new FFMpegFiller(outDir));
    }

    private static void init(){
        ColorGapContext.InitializeParam ip = new ColorGapContext.InitializeParam();
        ip.setTestType(SimpleColorGapContext.TEST_TYPE_LOCAL);

        sInitContext.setInitializeParam(ip);
        Launcher.launch(sInitContext);
    }
}
