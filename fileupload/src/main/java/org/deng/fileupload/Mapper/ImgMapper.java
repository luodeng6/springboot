package org.deng.fileupload.Mapper;
import org.deng.fileupload.Pojo.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ImgMapper {
    // 查询所有图片
    @Select("select * from img")
    List<Img> getAllImg();

    // 增加图片
    boolean addImg(Img img);

    // 删除图片
    void deleteAllImg();

    //删除非图片、视频文件->粗略认为文件名不包含.jpg、jpeg、png、webp和.mp4的为非图片、视频文件
    boolean deleteNotImgOrVideo();

    // 获取所有非图片、视频文件->测试用
    List<Img> getAllNotImgOrVideo();
}
