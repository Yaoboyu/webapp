package com.webapp.webapp.Mapper;

import com.webapp.webapp.Pojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordMapper {
    @Select("select word_id from webapp.user_word where user_id = #{userid} and status = 0")
    List<Integer> getWordLearnedIdsByUserId(int userid);
    @Select("select * from webapp.word where webapp.word.id = #{id}")
    Word getWordById(int id);
    @Select("select word_id from webapp.user_word where user_id = #{userid}")
    List<Integer> getWordCompletedByUserId(int userid);
    @Select("select word_id from webapp.book_word where book_id = #{bookid} and word_id is not null")
    List<Integer> getWordByBookId(int bookid);
}
