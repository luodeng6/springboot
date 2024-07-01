package org.deng.fileupload.demos.web;
import org.deng.fileupload.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class excelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/readExcel")
    public List<Map<String, String>> readExcel() throws IOException {
        //获取当前项目的主目录。这种方法在任何Java项目中都适用
        System.out.println(System.getProperty("user.dir"));
        return excelService.readExcelMap(System.getProperty("user.dir") + "/" + "考试安排.xlsx");
    }

    @PostMapping("/readExcelByupload")
    public Map<String, Object> readExcelByupload(@RequestParam("file") MultipartFile file) throws IOException {

        Map<String, Object> result_map = new HashMap<>();
        List<Map<String, String>> dataList;
        try {
            // 判断文件是否为空
            if (file.isEmpty()) {
                return null; // 返回适当的空数据或错误提示
            }

            dataList = excelService.readExcelMapFromMultipartFile(file);
            result_map.put("code", 200);
            result_map.put("fileName", file.getOriginalFilename());
            result_map.put("fileSize", file.getSize());
            result_map.put("dataSize", dataList.size());
            result_map.put("data", dataList);

            System.out.println("文件名：" + file.getOriginalFilename());
            System.out.println("文件大小：" + file.getSize());
            System.out.println("数据行数：" + dataList.size());

            //保存文件到本地
            //String filePath = System.getProperty("user.dir") + "/" + file.getOriginalFilename();
            //file.transferTo(new File(filePath));

            // 直接调用服务层方法读取Excel数据
            return result_map;
        } catch (Exception e) {
            e.printStackTrace();
            result_map.put("code", 500);
            result_map.put("message", e.getMessage());
            result_map.put("data", null);
            result_map.put("message_cn", "读取Excel文件失败，后端发生异常。请确定您上传的Excel文件格式正确。");
            return result_map;
        }
    }
}
