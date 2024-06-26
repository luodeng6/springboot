package org.deng.fileupload.demos.web;

import org.deng.fileupload.Mapper.ImgMapper;
import org.deng.fileupload.Mapper.UserMapper;
import org.deng.fileupload.Pojo.Img;
import org.deng.fileupload.Pojo.Result;
import org.deng.fileupload.Pojo.User;
import org.deng.fileupload.Service.IMP.ImgServiceIMP;
import org.deng.fileupload.Service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    //自动装配ImgMapper
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 处理获取所有图片的请求，返回包含所有图片信息的结果映射
     *
     * @return 返回图片信息
     */
    @RequestMapping(value = "/getAllimages", method = RequestMethod.GET)
    public Map<String, Object> getAllimages() {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //调用ImgMapper的selectAll方法，获取所有图片信息
            resultMap.put("result", true);
            resultMap.put("message", "获取成功");
            resultMap.put("data", imgMapper.getAllImg());
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);
            resultMap.put("message", "获取失败,内部错误");
            resultMap.put("data", e.getMessage());
            return resultMap;
        }
    }


    //删除所有图片

    /**
     * 处理删除所有图片的请求，返回删除结果
     *
     * @return 返回删除结果
     */
    @RequestMapping(value = "/deleteAllImages", method = RequestMethod.GET)
    public Map<String, Object> deleteAllImages() {
        try {
            //调用ImgMapper的deleteAll方法，删除所有图片
            imgMapper.deleteAllImg();
            // 删除图片文件
            ImgService imgService = new ImgServiceIMP();
            imgService.deleteAllImg();

            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("result", true);
            resultMap.put("message", "删除成功");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);
            resultMap.put("message", "删除失败,内部错误");
            resultMap.put("data", e.getMessage());
            return resultMap;
        }
    }


    /**
     * 登录接口
     * 处理登录请求，返回登录结果
     *
     * @param loginForm 登录表单
     * @param request   请求对象
     * @return 返回登录结果
     */
    @PostMapping("/logindo")
    public Result loginDo4(@RequestBody User loginForm, HttpServletRequest request) {
        // 在这里处理接收到的对象
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        return getResult(request, userMapper.loginUser(username, password));
    }

    private Result getResult(HttpServletRequest request, User chackuser) {
        System.out.println(chackuser);
        if (chackuser != null) {
            //创建Session
            HttpSession session = request.getSession();
            session.setAttribute("username", chackuser.getUsername());
            return new Result("登录成功", 200, true, "POST");
        } else {
            return new Result("登录失败，请检查用户名和密码是否正确！", 200, false, "POST");
        }
    }

    /**
     * 删除所有非图片和视频的文件->包括数据库里的和本地文件
     *
     * @return 返回删除结果
     */
    @Value("${file.upload-dir}")//读取配置文件中的上传目录
    private String uploadDir;

    @GetMapping(value = "/deleteAllFilesNotImgNotVideo")
    public Map<String, Object> deleteAllFilesNotImgNotVideo() {
        try {
            List<Img> NotImgOrVideoList = imgMapper.getAllNotImgOrVideo();
            System.out.println("非图片和视频文件列表：");
            System.out.println(NotImgOrVideoList);
            System.out.println("非图片和视频文件列表长度：" + NotImgOrVideoList.size());

            //调用ImgMapper的deleteAll方法，删除所有图片
            imgMapper.deleteNotImgOrVideo();
            int deleteCount = 0;

            for (Img img : NotImgOrVideoList) {

                try {
                    // 构建文件路径
                    String filePath = uploadDir + "/" + img.getName();
                    System.out.println("删除文件路径：" + filePath);
                    File file = new File(filePath);
                    //删除本地文件
                    if (file.delete()) {
                        deleteCount++;
                    } else {
                        System.out.println("删除失败：文件不存在！->" + filePath);
                    }
                } catch (Exception e) {
                    System.out.println("删除失败：其他原因！->" + e.getMessage());
                }
            }

            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("result", true);
            resultMap.put("message", "删除成功");
            resultMap.put("NotImgOrVideoListdata", NotImgOrVideoList);
            resultMap.put("NotImgOrVideoListLength", NotImgOrVideoList.size());
            resultMap.put("deleteCountSuccess", deleteCount);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);
            resultMap.put("message", "删除失败,内部错误");
            resultMap.put("data", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 分页获取图片信息
     *
     * @param page 页码
     *             默认为1
     * @param size 每页显示数量
     *             默认为10
     * @return 返回图片信息
     */
    @GetMapping(value = "/getSomgImgPages")
    public Map<String, Object> getSomgImgPages(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Map<String, Object> resultMap = new HashMap<>();

            ImgService imgService = new ImgServiceIMP();

            //获取所有图片信息
            List<Img> AllImgList = imgMapper.getAllImg();
            //获取分页数据
            List<Img> CurrentImgList = imgService.paginate(AllImgList, page, size);

            resultMap.put("result", true);
            resultMap.put("message", "获取成功");
            resultMap.put("totalSize", AllImgList.size());
            resultMap.put("getSize", CurrentImgList.size());
            resultMap.put("data", CurrentImgList);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);
            resultMap.put("message", "获取失败,内部错误");
            resultMap.put("data", e.getMessage());
            resultMap.put("Size", size);
            resultMap.put("Page", page);
            return resultMap;
        }
    }

    // 测试练手的接口

    /**
     * 把数据库里所有图片的链接由localhost改为指定的ip
     *
     * @param ip 指定的ip
     * @return 返回修改结果
     */
    @GetMapping(value = "/ChangeImgNameLocalhostToIp")
    public Map<String, Object> ChangeImgNameLocalhostToIp(@RequestParam(value = "ip") String ip) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //获取所有图片信息
            List<Img> AllImgList = imgMapper.getAllImg();
            //修改图片链接
            for (Img img : AllImgList) {
                img.setUrl(img.getUrl().replace("localhost", ip));
                img.setUrl(img.getUrl().replace("127.0.0.1",ip));
                imgMapper.updateImg(img);
            }

            resultMap.put("result", true);
            resultMap.put("message", "修改成功");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", false);
            resultMap.put("message", "修改失败,内部错误");
            resultMap.put("data", e.getMessage());
            return resultMap;
        }
    }



   /**
     * 路径参数测试接口
     * @param id 图片id
     * @return 返回图片信息
     */
    @GetMapping(value = "/getImgById/{id}")
    public Map<String, Object> getImgById(@PathVariable("id") int id) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //调用ImgMapper的selectById方法，获取指定id的图片信息
            Img img = imgMapper.getImgById(id);
            resultMap.put("result", true);
            resultMap.put("message", "获取成功");
            resultMap.put("data", img);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);
            resultMap.put("message", "获取失败,内部错误");
            resultMap.put("data", e.getMessage());
            return resultMap;
        }
    }


    /**
     * 路径参数测试接口->实现下载功能
     * @param id 图片id
     * @return 直接返回图片文件
     */
    @GetMapping(value = "/getImgFileById/{id}")
    public ResponseEntity<Resource> getImgFileById(@PathVariable("id") int id) {
        try {
            // 调用 ImgMapper 的 getImgById 方法，获取指定 id 的图片信息
            Optional<Img> optionalImg = Optional.ofNullable(imgMapper.getImgById(id));

            // 判断图片是否存在
            if (optionalImg.isPresent()) {
                Img img = optionalImg.get();

                File imgFile = new File(uploadDir + "/" + img.getName());

                if (imgFile.exists()) {
                    Resource resource = new FileSystemResource(imgFile);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imgFile.getName() + "\"");
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);

                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(imgFile.length())
                            .contentType(MediaType.parseMediaType("image/jpeg"))
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


