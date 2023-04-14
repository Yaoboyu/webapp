package com.webapp.webapp.Mapper;

import com.webapp.webapp.Pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface BookMapper {
    /**
     * 根据用户id查询所学词书id
     * @param bookId 用户id
     * @return 词书id
     */
    @Select("SELECT * from webapp.book_list where book_list.id = #{Bookid}")
    Book GetBookByBookId(int bookId);
}
