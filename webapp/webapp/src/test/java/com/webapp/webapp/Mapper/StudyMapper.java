package com.webapp.webapp.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudyMapper {
    //1.根据用户名查用户id
    @Select("SELECT user_id  from webapp.user where user_name = #{UserName}")
    int GetIdByUserName(String UserName);
    //2.根据用户id查用户学的词书
    @Select("SELECT book_id from webapp.user_book where user_id = #{UserId} and status = 1")
    int GetBookIdByUserId(int UserId);
    //3.根据书id查词书单词
    @Select("select word from webapp.book_word where id = #{BookId}")
    List<String> GetWordsByBookId(int BookId);
    //4.根据用户id查学了但是没学会的单词id
    @Select("select word_id from webapp.user_word where user_id = #{UserId} and status = 0")
    List<Integer> GetWordIdsLearnedByUserId(int UserId);
    //5.根据用户id查用户学了的单词id,配合上面的函数可以得到用户学会了的单词
    @Select("select word_id from webapp.user_word where user_id = #{UserId}")
    List<Integer> GetWordIdsCompletedByUserId(int UserId);
    //当用户没有学一半跑了的单词之后,准备查一下用户词书中所有没学过的单词???
    //查询所有单词
    @Select("select word from webapp.word")
    List<String> AllWordsWord();
    //查询所有单词id
    @Select("select id from webapp.word")
    List<Integer> AllWordsId();
}
