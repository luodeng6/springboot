package org.deng.many_algorithms;

import org.deng.many_algorithms.Service.ExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ManyAlgorithmsApplicationTests {


    @Autowired
    ExcelService excelService;

    //测试读取Excel文件 List<List<String>>
    @Test
    void TestReadExcelFile() throws IOException {
        String filePath = "C:\\Users\\Administrator\\Downloads\\考试安排.xlsx";
        System.out.println(excelService.readExcelList(filePath));
    }

    // 测试读取Excel文件 Map<String, List<String>>
    @Test
    void TestReadExcelFileMap() throws IOException {
        System.out.println(excelService.readExcelMap("C:\\Users\\Administrator\\Downloads\\考试安排.xlsx"));
    }

}
