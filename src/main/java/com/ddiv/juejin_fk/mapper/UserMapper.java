package com.ddiv.juejin_fk.mapper;

import com.ddiv.juejin_fk.pojo.JuejinUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 查找用户
     *
     * @param userName 用户名
     * @return 用户对象，含密码
     */
    @Select("select user_id,user_name,user_password from juejin_user where is_delete = 0 and user_name = #{userName}")
    JuejinUser findByUserName(String userName);

    /**
     * 新增用户
     *
     * @param juejinUser 用户信息
     *                   只需：MD5加密后密码、用户名
     * @return
     */
    @Insert("insert into juejin_user(user_name,user_password) values (#{userName},#{userPassword}) ")
    void insertUser(JuejinUser juejinUser);

    @Update("update juejin_user set user_image = #{image} where user_id = #{userId}")
    void updateUserImage(Integer userId, String image);

    @Select("select user_id,user_name,user_password from juejin_user where is_delete=0 and user_id=#{userId}")
    JuejinUser findByUserId(Integer userId);

    @Update("update juejin_user set user_password = #{password} where user_id = #{userId}")
    void updateUserPassword(Integer userId, String password);

    @Select("select user_id,user_name,user_image,user_resume from juejin_user where is_delete=0 and user_id=#{userId}")
    JuejinUser getSimpleUserInfo(Integer userId);

    @Update("update juejin_user set is_delete=1 where user_id=#{user_id}")
    void deleteUser(Integer userId);

    @Update("update juejin_user set user_name=#{userName} where user_id=#{userId}")
    void updateUserName(JuejinUser user);
}


