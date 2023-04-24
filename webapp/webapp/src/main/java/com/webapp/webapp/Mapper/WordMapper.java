package com.webapp.webapp.Mapper;

import com.webapp.webapp.Pojo.Word;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WordMapper {
    /**
     * 根据用户id查询用户学剩下的单词,所以有了判断句:status = 0
     * @param userId 用户id
     * @return 用户学剩下的词组id列表
     */
    @Select("select word_id from webapp.user_word where user_id = #{userId} and status = 0")
    List<Integer> getWordLearnedIdsByUserId(long userId);

    /**
     * 根据id查单词(一对一查辣,很简单的逻辑)
     * @param id 这是单词的id,对不起我偷懒了:)
     * @return 单词的类对象
     */
    @Select("select * from webapp.word where webapp.word.id = #{id}")
    Word getWordById(int id);

    /**
     * 这里我直接查询用户id所学过的单词id,没有判断status情况是因为我打算在这个函数执行之前就一定会执行上面找学剩下的函数,所以既然没有学剩下的那么就都是学完的了
     * @param userId 用户id
     * @return 用户学过的单词id列表
     */
    @Select("select word_id from webapp.user_word where user_id = #{userId}")
    List<Integer> getWordCompletedByUserId(long userId);

    /**
     * 根据书id查询这本书的单词(有特殊的情况是有些书下的单词竟然没有id?所以必须排除,不然全是null😡);
     * @param bookId 词书id
     * @return 这本书的单词id列表
     */
    @Select("select word_id from webapp.book_word where book_id = #{bookId} and word_id is not null")
    List<Integer> getWordByBookId(long bookId);

    /**
     * 更新单词状态
     * @param userId 用户id
     * @param wordId 单词id
     * @param status 状态
     */
    @Update("update user_word set status = #{status} where user_id = #{userId} and word_id = #{wordId}")
    void updateWordStatus(long userId, int wordId, int status);
}
