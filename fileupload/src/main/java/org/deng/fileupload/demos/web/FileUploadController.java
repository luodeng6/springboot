package org.deng.fileupload.demos.web;
import org.deng.fileupload.Mapper.ImgMapper;
import org.deng.fileupload.Pojo.Img;
import org.deng.fileupload.Service.HttpAPiService;
import org.deng.fileupload.Service.IMP.HttpApiServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${homeurl}")
    private String homeUrl;

    @Autowired
    private ImgMapper imgMapper;


    @PostMapping("/upload")
    public Map<String, Object> multipleFileUpload(@RequestParam("files") MultipartFile[] files) throws IOException {

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> fileResults = new ArrayList<>();

        // 检查是否选择了文件
        if (files.length == 0) {
            result.put("message", "没有选择文件！");
            result.put("files", Collections.emptyList());
            result.put("result", false);
            return result;
        }

        //输出文件数
        System.out.println("文件数："+files.length);
        long totalSize = 0;

        // 检查目录是否存在，如果不存在则创建
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 处理每一个文件
        for (MultipartFile file : files) {
            Map<String, Object> fileResult = new HashMap<>();

            if (file.isEmpty()) {
                fileResult.put("message", "文件为空！");
                fileResults.add(fileResult);
                continue;
            }

            try {
                // 获取文件名
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                //文件名处理
                fileName = fileName.replaceAll("[\\\\/:*?\"<>|#%$()]", "");
                // 获取时间戳
                String timestamp = String.valueOf(System.currentTimeMillis());
                // 文件名加时间戳
                fileName = timestamp + "_" + fileName;



                /*  写入文件到指定路径->文件名
                    resolve(fileName) 是一种用于构建文件路径的方法。它通常用于文件上传或路径操作中，
                    以确保生成的路径是规范化的、易于管理的，并且避免手动拼接路径字符串可能引起的错误。
                 */
                Files.copy(file.getInputStream(), uploadPath.resolve(fileName));


                // 示例中的 uploadPath 需要根据实际情况修改
                String fileUrl = homeUrl + "upload/" + fileName;
                fileResult.put("message", "文件上传成功！");
                fileResult.put("url", fileUrl);
                fileResult.put("result", true);
                fileResult.put("size", file.getSize());
                fileResult.put("fileName", fileName);
                fileResult.put("fileStyle", file.getContentType());

                // 调用接口 获取图片标题描述
                HttpAPiService httpAPiService = new HttpApiServiceIMP();

                Map<String, Object> DataMap = httpAPiService.OneDayOneSay();
                Map<String, Object> ResultMap = (Map<String, Object>) DataMap.get("result");
                String desc = (String) ResultMap.get("content");


                imgMapper.addImg(new Img(fileUrl, new Date(System.currentTimeMillis()), fileName, (int) file.getSize(),desc));
                totalSize += file.getSize();
            } catch (IOException e) {
                e.printStackTrace();
                fileResult.put("message", "文件上传失败！内部错误！");
                fileResult.put("result", false);
            }
            fileResults.add(fileResult);
        }

        result.put("totalSize", totalSize);
        result.put("num", files.length);
        result.put("message", "批量文件上传结果！");
        result.put("files", fileResults);
        result.put("result", true);
        return result;
    }

}