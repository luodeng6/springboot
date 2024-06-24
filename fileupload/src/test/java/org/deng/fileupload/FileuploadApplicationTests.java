package org.deng.fileupload;

import org.deng.fileupload.Mapper.ImgMapper;
import org.deng.fileupload.Pojo.Img;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class FileuploadApplicationTests {

    @Autowired
    ImgMapper imgMapper;

    //测试图片增加接口
    @Test
    void CheckAddImg() {
        Img img = new Img("http://www.baidu.com",  new Date(1234567890000L), "test.jpg",1024);

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

}
