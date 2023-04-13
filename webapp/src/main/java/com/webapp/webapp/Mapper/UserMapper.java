package com.webapp.webapp.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {
    /**
     *
     * 根据用户名查询用户id
     * @param UserName 用户名
     * @return 用户id
     */
    @Select("SELECT user_id  from webapp.user where user_name = #{UserName}")
    int GetUserIdByUserName(String UserName);

    /**
     * 根据用户id查询词书id
     * @param UserId 用户id
     * @return 所学词书id
     */
    @Select("SELECT book_id from webapp.user_book where webapp.user_book.user_id = #{UserId} and status = 1")
    Integer GetBookIdByUserId(int UserId);
}
