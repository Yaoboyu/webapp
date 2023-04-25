package com.webapp.webapp.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webapp.webapp.Mapper.TestMapper;
import com.webapp.webapp.Service.impl.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//测试用的没必要看
@Slf4j
@RestController
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    TestMapper testMapper;
    @GetMapping("/test")
    public String test1(){
        List<String> list = testMapper.list();
        List<String> l = new ArrayList<String>();
        for(String s :list)
            if(!l.contains(s))
                l.add(s);
        list = l;
        String trans;
        int id,cnt = 1;
        for(String word:list){
            if(cnt <= 889){
                System.out.println("--------------------" + (cnt)++ + "-----------------------");
                continue;
            }
            trans = trans(word);
            testMapper.add('"' + word + '"',trans);
            id = testMapper.serch(word);
            testMapper.update(id,word);
            System.out.println("-------------------------------" + (cnt++) + "-------------------------");
        }
        return "?";
    }
    String trans(String word){
        Map<String,Object> mp = testService.add(word);
        JSONArray array = (JSONArray) mp.get("translateResult");
        List<String> list = JSONObject.parseArray(array.toJSONString(),String.class);
        String trans = list.get(0);
        int lastindex = trans.lastIndexOf(',');
        trans = trans.substring(8,lastindex);
        return trans;
    }
    /*String test2(){
        List<Integer> l1 = testService.id();
        List<String> l2 = testService.word();
        Map<String,Integer> mp = handle(l2,l1);
        int cnt = 1;
        for(String s : mp.keySet()){
            testService.update(mp.get(s),s);
            System.out.println("---------------------------" + (cnt++) + "------------------------------------");
        }
        return "拿下!";
    }
    Map<String, Integer> handle(List<String> list1, List<Integer> list2) {
        Map<String, Integer> map = IntStream.range(0, list1.size()).collect(HashMap::new, (m, i) -> m.put(list1.get(i), list2.get(i)), (m, n) -> {});
        return map;}*/
}
