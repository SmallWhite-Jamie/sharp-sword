package com.jamie.framework.mapper;


import com.jamie.framework.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {
    @Select("select * from t_user where name like #{name}")
    List<User> findByName(String name);

    @Select("select * from t_user where id = #{id}")
    User findById(String id);

    List<User> findAll();

    @Insert("insert into t_user ( id, name, age, addr ) values (#{id}, #{name}, #{age}, #{addr})")
    void insert(User user);

}
