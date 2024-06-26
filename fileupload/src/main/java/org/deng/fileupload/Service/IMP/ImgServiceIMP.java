package org.deng.fileupload.Service.IMP;

import org.deng.fileupload.Service.ImgService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 分页函数
     * @param items       待分页的List数据
     * @param currentPage 当前页码,要获取的数据的页码
     * @param pageSize    每页的大小
     * @param <T>         泛型，适用于任何类型的List
     * @return 当前页的数据
     */
    public <T> List<T> paginate(List<T> items, int currentPage, int pageSize) {
        // 计算起始索引和结束索引
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, items.size());

        // 返回当前页的数据
        if (startIndex >= items.size() || startIndex < 0) {
            return Collections.emptyList(); // 返回空的List
        }
        return new ArrayList<>(items.subList(startIndex, endIndex));
    }
}
