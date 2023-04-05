package com.yaoboyu.webapp.Mapper;

import com.yaoboyu.webapp.Pojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordMapper {
    @Select("select * from ${title}")   //妈的根据参数查表明必须用$恶心死老子了,真的脑残这MySQL
    List<Word> ListAllWord(String title);
}
