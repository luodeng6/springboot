package org.deng.many_algorithms.Controller;

import org.deng.many_algorithms.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class excelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/readExcel")
    public List<Map<String, String>> readExcel() throws IOException {
        //获取当前项目的主目录。这种方法在任何Java项目中都适用
        System.out.println(System.getProperty("user.dir"))   ;
       return  excelService.readExcelMap(System.getProperty("user.dir")+"/"+"考试安排.xlsx");
    }

}
