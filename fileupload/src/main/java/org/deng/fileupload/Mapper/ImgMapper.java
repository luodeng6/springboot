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
}
