package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Config.ThreadLocalConfig;
import com.webapp.webapp.Dto.WordSentence;
import com.webapp.webapp.Exception.MyException;
import com.webapp.webapp.Mapper.UserMapper;
import com.webapp.webapp.Mapper.WordMapper;
import com.webapp.webapp.Pojo.Word;
import com.webapp.webapp.Utils.WordUtils;
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

    /**
     * ok大的要来辣,通过用户名得到用户应该学的随机单词,有学剩下的优先
     * @param
     * @return 单词列表
     */
    public List<Word> getWordsByUserName() throws Exception{
        final int size = 10; //size是设定一波学习的词组大小,我们默认是十个,但是如果说需要改这个数量的话就可以直接改size了
        long userid = ThreadLocalConfig.getUser().getId();//先拿到用户的id,我们这些操作基本上基于id操作的,最后一步呈现数据才是把id转成类对象
        List<Integer> list = wordMapper.getWordLearnedIdsByUserId(userid);//这是要先查一下用户是不是有学剩下的
        if(list.size() > 0)
            return getWords(list);//有学剩下的就好办了直接把id列表转换成词组列表返回了
        else
            list = wordMapper.getWordCompletedByUserId(userid); //没有学剩下的,那我就去拿他已经学过的单词id列表,方便后续排除掉这些学过的
        long bookid = userMapper.GetBookIdByUserId(userid);//根据用户id查他现学的词书id
        List<Integer> bookWordIds = wordMapper.getWordByBookId(bookid);//通过词书id查到词书下单词id列表
        for(int i : list)//上一步查到的单词列表只是这本书的所有单词对吧?那这些单词既有用户学过的,也有没学过的,这个for循环就是排除掉学过的只剩下没学的
            bookWordIds.remove(i);
        //至此,bookWordIds表就只剩下纯正的没学过的单词id了,接下来就是从这些id里面随机挑十个id的单词了!
        int len = bookWordIds.size();//列表大小
        if(len == 0) // 如果列表为空证明学完了这本书
            throw new MyException("所有单词已经学完,请选一本新词书!");
        if(len < size)//如果说列表里面剩下的单词都不够一次呈现的话,那就直接都拿上去咯还有什么可选的
           return getWords(bookWordIds);
        /*接下来的有点难理解了,需要读明白我这段话:
        目前我有一个bookwordids列表,装着的是所有要学但是还没学的单词id对吧,里面的id大的值可能有好几万,小的可能就几十几百,但是一本词书里面的单词
        就只有几千个,也就是说id值可能远远大于列表容量(长度),并且id没啥子规律,那其实有点难办了,列表里面的id也不是一段连续的数字,毫无规律啊,我要从这些
        凌乱的数字里面出十个,该怎么办呢?
        我想到的笨方法如下:
        虽然id无规律可循,但是这些id封装在一个List容器里面对不对,list好比一个数组,纵使你的数组的元素值有多么波动不管,但是数组的索引就是从0到数组的
        长度-1(len-1),那么0------(len-1)这段连续的索引随机出十个就好弄了,把这端数字分成十段,每段里面随机取一个,就得到了十个随机的索引值,再通过索引值
        找十个id值就实现了随机十个id
        好了,如果看明白这段文字基本上能理解下面的逻辑了
         */
        int p = len / size;//分段的长度
        List<Integer> index = new ArrayList<Integer>();//存放索引index的List容器
        Random r;
        for(int i = 0;i < size;i++){
            r = new Random(System.currentTimeMillis()); //一个随机数
            int rr = Math.abs(r.nextInt()) % p + i * p;//随机数取余控制了他的范围是在0-p之间的一个数字,加上后面的i*p即是对应的分段了
            index.add(bookWordIds.get(rr));//把这个随机索引放到index容器
        }
        return getWords(index);//拿下!
    }



    /**
     * 通过用户名和单词id更新单词状态
     * @param wordId 单词id
     * @param status 状态
     */
    @Override
    public void updateWordStatus(Integer wordId, Integer status) throws Exception {
        long userId = ThreadLocalConfig.getUser().getId();
        wordMapper.updateWordStatus(userId,wordId,status);
    }

    /**
     * 根据一个word_id列表查询所有这个列表里面id对应的单词
     * @param list 单词的id列表
     * @return 返回单词的列表
     */
    List<Word> getWords(List<Integer> list){
        List<Word> ans = new ArrayList<Word>();
        for(int i : list)
            ans.add(wordMapper.getWordById(i));
        return ans;
    }
    public WordSentence wordSentence(String word){
        try {
            return WordUtils.serch(word);
        } catch (Exception e) {
            throw new MyException("该单词无例句!");
        }
    }

    @Override
    public String wordDetail(String word) {
        return WordUtils.wordDetail(word);
    }
}
