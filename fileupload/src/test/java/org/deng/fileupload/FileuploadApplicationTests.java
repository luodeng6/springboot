package org.deng.fileupload;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deng.fileupload.Mapper.ImgMapper;
import org.deng.fileupload.Pojo.Img;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class FileuploadApplicationTests {

    @Autowired
    ImgMapper imgMapper;

    //测试图片增加接口
    @Test
    void CheckAddImg() {
        Img img = new Img("http://www.baidu.com", new Date(1234567890000L), "test.jpg", 1024);

        System.out.println(imgMapper.addImg(img));
    }

    //测试 获取所有图片接口
    @Test
    void CheckGetAllImg() {
        List<Img> imgs = imgMapper.getAllImg();
        for (Img img : imgs) {
            System.out.println(img);
        }
    }

    //测试 删除非图片视频文件
    @Test
    void CheckDeleteImgAndvieo() {
        System.out.println(imgMapper.deleteNotImgOrVideo());
    }

    // 测试获取非图片视频文件
    @Test
    void CheckGetNotImgOrVideo() {
        List<Img> imgs = imgMapper.getAllNotImgOrVideo();
        for (Img img : imgs) {
            System.out.println(img);
        }
    }

    //测试修改图片url信息
    @Test
    void CheckGetImgUrl() {
        // 从数据库里获取一个img对象
        Img img = imgMapper.getImgById(1256);
        System.out.println("获取的img对象：");
        System.out.println(img);
        String OldUrl = img.getUrl();
        String NewUrl = OldUrl.replace("localhost", "127.0.0.1");
        img.setUrl(NewUrl);
        // 修改img对象的url属性
        System.out.println(imgMapper.updateImg(img));
        ;
    }

    //java 请求

    @Test
    void checkJavaHttp() throws IOException {
        // 定义要访问的URL
        String url = "https://api.oioweb.cn/api/common/yiyan";

        // 创建一个URL对象
        URL obj = new URL(url);

        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // 设置请求方法为GET
        connection.setRequestMethod("GET");

        // 设置请求头
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // 获取响应代码
        int responseCode = connection.getResponseCode();
        System.out.println("响应代码: " + responseCode);

        // 读取响应内容
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 检查数据类型
        System.out.println("响应数据类型: " + response.getClass().getName());


        // 打印响应内容
        System.out.println("响应内容: " + response.toString());

        //转为Map对象
        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将JSON字符串转换为Map
            Map map = objectMapper.readValue(response.toString(), Map.class);
            // 打印Map内容
            System.out.println(map);
            System.out.println(map.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 测试mysql分页查询
    @Test
    void checkMysqlPage()  {

        int pagSize = 10;//每页显示10条数据
        int pageNum = 1;//第一页
        //计算偏移量:
        int offset = (pageNum - 1) * pagSize;
        System.out.println(imgMapper.getImgByPage(pagSize, offset));

    }

}
