package com.heaven7.ve.colorgap.impl.montage;

import com.heaven7.java.base.util.Logger;
import com.heaven7.utils.Context;
import com.heaven7.utils.FileUtils;
import com.heaven7.ve.Constants;

import com.heaven7.ve.colorgap.MediaResourceScanner;
import com.heaven7.ve.cross_os.IMediaResourceItem;

import java.io.File;

/**
 * @author heaven7
 */
public class MediaHighLightScanner extends MediaResourceScanner{

    private static final String TAG = "MediaHighLightScanner";

    @Override
    public String scan(Context context, IMediaResourceItem item, String srcDir) {
        String fileName = FileUtils.getFileName(item.getFilePath());
        if(!srcDir.endsWith("resource")){
            Logger.e(TAG, "scan", "resource dir is not right.");
            return null;
        }
        String parentDir = FileUtils.getParentDir(srcDir, 1, true);
        //data/highlight/xxx.vhighlight
        return parentDir + File.separator + Constants.DIR_DATA + File.separator
                + Constants.DIR_HIGH_LIGHT + File.separator
                + fileName + "." + Constants.EXTENSION_VIDEO_HIGH_LIGHT;
    }

}
