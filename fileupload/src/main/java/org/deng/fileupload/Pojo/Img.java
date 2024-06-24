package org.deng.fileupload.Pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (Img)实体类
 *
 * @author makejava
 * @since 2024-06-23 19:53:11
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Img{
    private Integer id;
    private String url;
    private Date uploadtime;
    private String name;
    private Integer size;

    // 自定义构造函数
    public Img(String fileUrl, Date date, String fileName, int size) {
        this.url = fileUrl;
        this.uploadtime = date;
        this.name = fileName;
        this.size = size;
    }
    /*
     构造函数特点：
     1.构造函数名称必须与类名完全相同。
     2.构造函数没有返回类型，包括 void。
     3.如果没有明确定义构造函数，Java 会提供一个默认的无参构造函数（不带任何操作）。
     4.可以定义多个构造函数，通过参数列表的不同来区分，这称为方法的重载。
      */
}

