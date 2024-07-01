package org.deng.fileupload.Service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface ExcelService {
    /**
     * 读取Excel文件中的数据
     *
     * @param filePath Excel文件的路径
     * @return 包含Excel文件数据的列表，每个子列表代表一行数据
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    List<List<String>>  readExcelList(String filePath) throws IOException;


    /**
     * 读取Excel文件中的数据，并将数据转换为Map, 其中key为Excel文件第一行的标题
     *
     * @param filePath Excel文件的路径
     * @return 包含Excel文件数据的Map列表，每个Map代表一行数据
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    List<Map<String, String>> readExcelMap(String filePath) throws IOException;


    List<Map<String, String>> readExcelMapFromFile(File file) throws IOException, InvalidFormatException;
    List<Map<String, String>>  readExcelMapFromMultipartFile(MultipartFile multipartFile) throws IOException;
}
