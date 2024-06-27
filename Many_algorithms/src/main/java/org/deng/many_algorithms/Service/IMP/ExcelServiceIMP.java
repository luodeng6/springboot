package org.deng.many_algorithms.Service.IMP;

import org.deng.many_algorithms.Service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service的作用是将一个类标记为Spring的Bean，使其成为Spring IoC容器中的一个Bean，并可以被Spring的依赖注入功能所使用。
@Service
public class ExcelServiceIMP implements ExcelService {

    /**
     * 读取Excel文件中的数据
     *
     * @param filePath Excel文件的路径
     * @return 包含Excel文件数据的列表，每个子列表代表一行数据
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    public List<List<String>> readExcelList(String filePath) throws IOException {

        List<List<String>> data = new ArrayList<>();
        // 创建文件输入流从指定的文件路径读取文件
        FileInputStream file = new FileInputStream(filePath);
        // 创建XSSFWorkbook对象，打开文件输入流以读取Excel工作簿
        Workbook workbook = new XSSFWorkbook(file);
        // 获取工作簿中的第一个工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 循环遍历每一行，将每个单元格的值转为字符串并存储在列表中，
        // 最后将行数据添加到总体数据列表中
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }

        workbook.close();
        file.close();
        return data;
    }


    /**
     * 读取Excel文件中的数据，并将数据转换为Map, 其中key为Excel文件第一行的标题,跳过空行和空单元格，避免生成空键值对
     *
     * @param filePath Excel文件的路径
     * @return 包含Excel文件数据的Map列表，每个Map代表一行数据
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    @Override
    public List<Map<String, String>> readExcelMap(String filePath) throws IOException {

        List<Map<String, String>> data = new ArrayList<>();
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        // 获取第一行作为字段名
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            file.close();
            return data; // 如果没有头行，返回空数据
        }
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.toString().trim());
        }

        // 读取数据行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue; // 跳过空行
            }
            Map<String, String> rowData = new HashMap<>();
            boolean rowHasData = false; // 检查行是否有数据
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    rowData.put(headers.get(j), cell.toString().trim());
                    rowHasData = true;
                }
            }
            if (rowHasData) {
                data.add(rowData);
            }
        }

        workbook.close();
        file.close();
        return data;
    }
}
