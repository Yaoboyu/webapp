package com.yaoboyu.webapp.Service;

import com.yaoboyu.webapp.Pojo.Word;

import java.util.List;

public interface WordService {
    //List<Word> ListAllBook(String title);
    List<Word> ListByPage(String title,Integer pagenum,Integer pagesize);
}
