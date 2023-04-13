package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Mapper.UserMapper;
import com.webapp.webapp.Mapper.WordMapper;
import com.webapp.webapp.Pojo.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StudyService implements com.webapp.webapp.Service.StudyService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    WordMapper wordMapper;
    public List<Word> getWordsByUserName(String UserName){
        final int size = 10;

        int userid = userMapper.GetUserIdByUserName(UserName);
        List<Integer> list = wordMapper.getWordLearnedIdsByUserId(userid);
        if(list.size() > 0)
            return getWords(list);
        else
            list = wordMapper.getWordCompletedByUserId(userid);
        int bookid = userMapper.GetBookIdByUserId(userid);
        List<Integer> bookWordIds = wordMapper.getWordByBookId(bookid);
        for(int i : list)
            bookWordIds.remove(i);
        int len = bookWordIds.size();
        if(len < size)
           return getWords(bookWordIds);
        int p = len / size;
        List<Integer> index = new ArrayList<Integer>();
        Random r;
        for(int i = 0;i < size;i++){
            r = new Random(System.currentTimeMillis());
            int rr = Math.abs(r.nextInt()) % p + i * p;
            index.add(bookWordIds.get(rr));
        }
        List<Word> ans = new ArrayList<Word>();
        ans = getWords(index);
        return ans;
    }
    List<Word> getWords(List<Integer> list){
        List<Word> ans = new ArrayList<Word>();
        for(int i : list)
            ans.add(wordMapper.getWordById(i));
        return ans;
    }
}
