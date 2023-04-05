package com.yaoboyu.webapp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.yaoboyu.webapp.Mapper.WordMapper;
import com.yaoboyu.webapp.Pojo.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService implements com.yaoboyu.webapp.Service.WordService {
    @Autowired
    private WordMapper wordMapper;  //注入mapper对象
    public List<Word> ListByPage(String title,Integer pagenum,Integer pagesize) {
        PageHelper.startPage(pagenum,pagesize);
        return wordMapper.ListAllWord("webapp.wordlist" + title);   //直接调用mapper层方法
    }
}
