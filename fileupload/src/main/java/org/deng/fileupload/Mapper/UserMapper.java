package org.deng.fileupload.Mapper;

import org.apache.ibatis.annotations.*;
import org.deng.fileupload.Pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    //login ->返回User对象
    @Select("select * from user where username=#{username} and password=#{password}" )
    User loginUser(@Param("username") String username, @Param("password") String password);

    //获取批量 用户
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{keyword}, '%')")
    List<User> searchUsers(@Param("keyword") String keyword);

    //删除
    @Delete("delete from user where username=#{username}")
    boolean deleteUser(@Param("username") String username);
}

