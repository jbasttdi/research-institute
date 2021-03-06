package com.heaven7.test;

import com.heaven7.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 给生成的人脸信息添加rects后缀
 */
public class AddSuffix_Rects {

    public static void main(String[] args) {
       /* String dir = "F:\\videos\\故事线\\rects\\婚礼0\\着装";
        addRects(dir);*/

     /*  String src = "F:\\videos\\故事线\\rects\\婚礼1\\晚宴";
       String dst = "F:\\test\\data\\story1\\dinner\\rects";
       copyFilesAndAddRects(src , dst, true);*/

        String src = "F:\\videos\\故事线\\婚礼4\\晚宴\\tags";
        String dst = "F:\\test\\data\\story4\\dinner\\tags";
        copyFilesAndAddRects(src , dst, false);
    }

    //拷贝到指定目录，并修改文件名添加rects后缀
    public static void copyFilesAndAddRects(String src, String dst, boolean addRectsSuffix) {
        final String extension = "csv";
        //check src
        File srcFile = new File(src);
        if(!srcFile.exists()){
            throw new IllegalStateException("src dir must exist.");
        }
        //create dst
        File dstFile = new File(dst);
        if(!dstFile.exists()){
            dstFile.mkdirs();
        }
        if(!dstFile.isDirectory()){
            throw new IllegalStateException();
        }
        //copy files
        List<String> list = new ArrayList<>();
        FileUtils.getFiles(srcFile, extension, list);
        for(String path : list){
            String fileName = FileUtils.getFileName(path);
            FileUtils.copyFile(new File(path), new File(dstFile, fileName + "." + extension));
        }
        if(addRectsSuffix) {
            addRects(dst);
        }
    }

    private static void addRects(String dir) {
        File file = new File(dir);
        FileUtils.checkDir(dir, true);
        List<String> files = new ArrayList<>();
        FileUtils.getFiles(file, "csv", files
                  /*  , new FileFilter() {
                  @Override
                  public boolean accept(File pathname) {
                    String fileDir = VEGapUtils.getFileDir(pathname.getAbsolutePath(), 1, false);
                    return "rects".equals(fileDir);
                  }
                }*/
        );

        for (String fn : files) {
            // System.out.println("file = " + fn);
            String fileName = FileUtils.getFileName(fn);
            String fileDir = FileUtils.getFileDir(fn, 1, true);
            File f = new File(fn);
            String extension = FileUtils.getFileExtension(f);
            String target = fileDir + File.separator + fileName + "_rects" + "." + extension;
            if (!f.renameTo(new File(target))) {
                System.out.println("rename failed. file = " + fn);
            }
        }
    }
}
