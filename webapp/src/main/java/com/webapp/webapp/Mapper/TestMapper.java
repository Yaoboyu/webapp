package com.webapp.webapp.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
//测试用的可以不看
@Mapper
public interface TestMapper {
    @Select("SELECT webapp.book_word.word FROM webapp.book_word where isnull(word_id)")
    List<String> list();
    @Insert("insert into webapp.word(word,translation) value (${word},${trans})")
    void add(String word,String trans);
    @Select("select webapp.word.id from webapp.word where word = #{word}")
    int serch(String word);
    @Update("UPDATE webapp.book_word SET word_id = #{id} WHERE word = #{word}")
    void update(int id,String word);
}
