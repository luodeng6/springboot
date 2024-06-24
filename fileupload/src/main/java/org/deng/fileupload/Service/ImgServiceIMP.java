package org.deng.fileupload.Service;

import java.io.File;

public class ImgServiceIMP implements ImgService {

    @Override
    public Boolean deleteAllImg() {
       try {
            File directory = new File("target/classes/static/upload");
            deleteDirectoryContents(directory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void deleteDirectoryContents(File directory) {
        File[] files = directory.listFiles();
        if (files != null) { // 防止 directory.listFiles() 返回 null
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectoryContents(file);
                }
                if (!file.delete()) {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
    }
}
