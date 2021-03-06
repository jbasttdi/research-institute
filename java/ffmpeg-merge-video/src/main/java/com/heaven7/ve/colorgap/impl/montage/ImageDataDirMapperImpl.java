package com.heaven7.ve.colorgap.impl.montage;


import com.heaven7.utils.FileUtils;
import com.heaven7.ve.Constants;
import com.heaven7.ve.colorgap.impl.ImageDataDirMapper;

import java.io.File;

/**
 * @author heaven7
 */
@Deprecated
public class ImageDataDirMapperImpl implements ImageDataDirMapper {

    @Override
    public String mapDataDir(String imageResDir) {
        //imageResDir like ...1/resource/12312313213/
        //                 ...1/data/12312313213/
        String parentDir = FileUtils.getParentDir(imageResDir, 2, true);
        if(imageResDir.endsWith("/") || imageResDir.endsWith("\\")){
            imageResDir = imageResDir.substring(0, imageResDir.length() - 1);
        }
        String[] strs = imageResDir.split("/");
        if(strs.length == 1){
            strs = imageResDir.split("\\\\");
        }
        String lastDir = strs[strs.length-1];
        return parentDir + File.separator + Constants.DIR_DATA + File.separator + lastDir;
    }

    @Override
    public String mapHighLightDir(String imageResDir) {
        String parentDir = FileUtils.getParentDir(imageResDir, 2, true);
        if(imageResDir.endsWith("/") || imageResDir.endsWith("\\")){
            imageResDir = imageResDir.substring(0, imageResDir.length() - 1);
        }
        String[] strs = imageResDir.split("/");
        if(strs.length == 1){
            strs = imageResDir.split("\\\\");
        }
        String lastDir = strs[strs.length-1];
        //data\highlight\moved_imgs
        return parentDir + File.separator + Constants.DIR_DATA + File.separator
                + Constants.DIR_HIGH_LIGHT + File.separator + lastDir;
    }
}
