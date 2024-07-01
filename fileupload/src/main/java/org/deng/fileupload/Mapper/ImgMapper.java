package org.deng.fileupload.Mapper;
import org.apache.ibatis.annotations.Param;
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

    //修改图片信息->自动映射变量
    boolean updateImg(Img img);

    // 通过id 获取一个图片
    Img getImgById(int id);

    //分页查询图片 根据每一页的数量和页码查询 -> 自动映射变量
    // 注意：这里的offset参数是从0开始的，所以要减去1
    // SELECT * FROM img LIMIT @pageSize OFFSET (@pageNum - 1) * @pageSize;
    List<Img> getImgByPage( @Param("pageSize") int pageSize,@Param("offset") int offset);


}
