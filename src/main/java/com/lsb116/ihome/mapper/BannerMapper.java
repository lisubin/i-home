package com.lsb116.ihome.mapper;


import com.lsb116.ihome.entity.Banner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BannerMapper {

    @Select("select * from h_banner")
    List<Banner> findAll();

    @Select("select * from h_banner where id=#{bannerId}")
    Banner getById(@Param("bannerId") Integer id);

    @Insert("insert into h_banner(name,img,url) values(#{name},#{img},#{url})")
    void add(Banner banner);

    @Update("update h_banner set name=#{name},img=#{img},url=#{url} where id=#{id}")
    void update(Banner banner);

    @Delete("delete from h_banner where id=#{id}")
    void deleteById(Integer id);
}
