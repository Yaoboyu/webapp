package com.webapp.webapp.Mapper;

import com.webapp.webapp.Pojo.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper {
    /**
     * 根据用户id查询所学词书id
     * @param bookId 用户id
     * @return 词书id
     */
    @Select("SELECT * from book where id = #{Bookid}")
    Book GetBookByBookId(long bookId);

    /**
     * 更新用户词书状态
     * @param userId 用户id
     * @param bookId 词书id
     * @param status 状态
     */
    @Update("update user_book set status = #{status} where user_id = #{userId} and book_id = #{bookId}")
    void UpdateBookStatus(long userId, Integer bookId, Integer status);

    /**
     * 插入用户词书信息
     * @param userId 用户id
     * @param bookId 词书id
     */
    @Insert("INSERT INTO user_book (user_id, book_id) VALUES (#{userId}, #{bookId})")
    void InsertUserBook(long userId, Integer bookId);

    /**
     * 判断用户是否已经有词书
     * @param userId 用户id
     * @param bookId 词书id
     * @return
     */
    @Select("SELECT EXISTS(SELECT * FROM user_book WHERE user_id = #{userId} AND book_id = #{bookId})")
    boolean GetBookByUserId(long userId, Integer bookId);
}
