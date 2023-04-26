package com.webapp.webapp.Mapper;

import com.webapp.webapp.Pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {
    /**
     *
     * 根据用户名查询用户id
     * @param userName 用户名
     * @return 用户id
     */
    @Select("SELECT id  from user where user_name = #{userName}")
    int GetUserIdByUserName(String userName);

    /**
     * 根据用户id查询词书id
     * @param userId 用户id
     * @return 所学词书id
     */
    @Select("SELECT book_id from user_book where user_id = #{userId} and status = 1")
    Long GetBookIdByUserId(long userId);

    /**
     * 根据用户名及密码查询用户信息
     * @param user 用户信息
     * @return 用户信息
     */
    @Select("SELECT * from user where user_name = #{userName} and password = #{password}")
    User GetUserByUserNameAndPassword(User user);

    /**
     * 插入用户信息
     * @param user 用户信息
     * @return 用户信息
     */
    @Insert("INSERT INTO user (user_name, password, email) VALUES (#{userName}, #{password}, #{email})")
    void InsertUser(User user);

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Select("SELECT * from user where user_name = #{userName}")
    User GetUserByUserName(String userName);
}
