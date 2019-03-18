package com.lsb116.ihome.mapper;


import com.lsb116.ihome.entity.Member;
import com.lsb116.ihome.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("select * from h_member")
    List<Member> findAll();

    @Select("select * from h_member where id=#{id}")
    Member getById(Integer id);

    @Select("select * from h_member where mobile=#{mobile} and password=#{password}")
    Member getMember(@Param("mobile") String mobile,@Param("password") String password);

    @Insert("insert into h_member(mobile,password) values(#{mobile},#{password})")
    void add(Member member);

    @Update("update h_member set mobile=#{mobile},password=#{password} where id=#{id}")
    void update(Member member);

    @Delete("delete from h_member where id=#{id}")
    void deleteById(Integer id);
}
