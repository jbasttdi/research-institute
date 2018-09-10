package com.heaven7.ve.colorgap;

import com.heaven7.utils.Context;
import com.heaven7.ve.anno.SystemResource;
import com.heaven7.ve.collect.ColorGapPerformanceCollector;
import com.heaven7.ve.kingdom.FileResourceManager;
import com.heaven7.ve.kingdom.Kingdom;
import com.heaven7.ve.template.VETemplate;
import com.heaven7.ve.test.ShotAssigner;
import com.heaven7.ve.utils.Flags;

/**
 * @author heaven7
 */
public interface ColorGapContext extends Context {

    /** test type local */
    int TEST_TYPE_LOCAL        = 1;
    /** test type local server */
    int TEST_TYPE_LOCAL_SERVER = 2;
    /** test type server */
    int TEST_TYPE_SERVER       = 3;

    int FLAG_ASSIGN_SHOT_TYPE = 0x0001;
    int FLAG_ASSIGN_SHOT_CUTS = 0x0002;

    @SystemResource
    void setInitializeParam(InitializeParam ip);
    InitializeParam getInitializeParam();

    void setMontageParameter(MontageParam param);
    MontageParam getMontageParameter();

    FileResourceManager getFileResourceManager();
    VETemplate getTemplate();

    /**
     * get test type .default is {@linkplain #TEST_TYPE_SERVER}
     * @return the test type
     */
    int getTestType();

    Kingdom getKingdom();
    void setKingdom(Kingdom kingdom);

    void setColorGapPerformanceCollector(ColorGapPerformanceCollector collector);

    ColorGapPerformanceCollector getColorGapPerformanceCollector();

    /** may be null */
    MusicCutter getMusicCutter();
    @SystemResource
    void setMusicCutter(MusicCutter provider);

    /**
     * copy the system resource to target color gap context.
     * @param dst the dst color gap context.
     */
    void copySystemResource(ColorGapContext dst);

    static String getTestTypeString(int testType) {
        switch (testType){
            case ColorGapContext.TEST_TYPE_LOCAL_SERVER:
                return "TEST_TYPE_LOCAL_SERVER";
            case ColorGapContext.TEST_TYPE_SERVER:
                return "TEST_TYPE_SERVER";
            case ColorGapContext.TEST_TYPE_LOCAL:
                return "TEST_TYPE_LOCAL";
        }
        return null;
    }

    /**
     * the init param
     */
    class InitializeParam{

        private int testType = ColorGapContext.TEST_TYPE_SERVER;
        /** the template file dir of json-data */
        private String templateDir;
        /** the effect file dir of json-data */
        private String effectDir;
        /** the effect file dir of resource */
        private String effectResourceDir;
        private boolean debug;
        /** the debug output dir */
        private String debugOutDir;
        private TransitionDelegate transitionDelegate;

        //--------------------- just for test ------------------------
        private int flags;
        private ShotAssigner shotAssigner;

        public TransitionDelegate getTransitionDelegate() {
            return transitionDelegate;
        }
        public void setTransitionDelegate(TransitionDelegate transitionDelegate) {
            this.transitionDelegate = transitionDelegate;
        }

        public String getEffectResourceDir() {
            return effectResourceDir;
        }
        public void setEffectResourceDir(String effectResourceDir) {
            this.effectResourceDir = effectResourceDir;
        }

        public String getDebugOutDir() {
            return debugOutDir;
        }
        public void setDebugOutDir(String debugOutDir) {
            this.debugOutDir = debugOutDir;
        }

        public boolean isDebug() {
            return debug;
        }
        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public int getTestType() {
            return testType;
        }
        public void setTestType(int testType) {
            this.testType = testType;
        }

        public String getTemplateDir() {
            return templateDir;
        }
        public void setTemplateDir(String templateDir) {
            this.templateDir = templateDir;
        }

        public String getEffectDir() {
            return effectDir;
        }
        public void setEffectDir(String effectDir) {
            this.effectDir = effectDir;
        }

        //----------------- just for test some -----------------
        public int getFlags() {
            return flags;
        }
        public void setFlags(int flags) {
            this.flags = flags;
        }

        public ShotAssigner getShotAssigner() {
            return shotAssigner;
        }
        public void setShotAssigner(ShotAssigner shotAssigner) {
            this.shotAssigner = shotAssigner;
        }
        public boolean hasFlag(int flag) {
            return Flags.hasFlags(this.flags, flag);
        }
    }
}
