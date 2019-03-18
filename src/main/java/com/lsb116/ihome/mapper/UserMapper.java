package com.lsb116.ihome.mapper;


import com.lsb116.ihome.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from sys_user")
    List<User> findAll();

    @Select("select * from sys_user where id=#{id}")
    User getById(Integer id);

    @Insert("insert into sys_user(username,password,realname) values(#{username},#{password},#{realname})")
    void add(User user);

    @Update("update sys_user set username=#{username},password=#{password},realname=#{realname} where id=#{id}")
    void update(User user);

    @Delete("delete from sys_user where id=#{id}")
    void deleteById(Integer id);
}
