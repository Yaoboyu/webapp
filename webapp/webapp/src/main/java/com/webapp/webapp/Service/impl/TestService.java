package com.webapp.webapp.Service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.webapp.webapp.Mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
//测试用的没必要看
@Service
public class TestService {
/*    @Autowired
    StudyMapper studyMapper;*/
    @Autowired
    TestMapper testMapper;
    /*public List<Integer> id(){
        return studyMapper.AllWordsId();
    }
    public List<String> word(){
        return studyMapper.AllWordsWord();
    }
    public void update(int id,String word){
        studyMapper.update(id,word);
    }*/
    public Map<String,Object> add(String i){
        String url = "http://fanyi.youdao.com/translate?doctype=json&type=AUTO&i=" + i;
        String res = HttpUtil.get(url);
        Map<String, Object> map = JSON.parseObject(res, new TypeReference<Map<String, Object>>() {});
        return map;
    }
}
