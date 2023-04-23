package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Service.impl.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/study")
public class StudyController {
    @Autowired
    StudyService studyService;

    /**
     * 如果没有学剩下的就是随机出十个没学过的(词书保证匹配),如果有学剩下的就简单了直接出学剩下的
     * @param userName 用户名
     * @return 返回用户要学习的词组列表,
     */
    @GetMapping("/words/{userName}")
    public Result study(@PathVariable String userName) throws Exception{
        log.info("用户{}想要学词", userName);
        return Result.success(studyService.getWordsByUserName(userName));
    }

    /**
     * 更新单词状态
     * @param userName 用户名
     * @param wordId 单词id
     * @param status 状态
     * @return
     */
    @PutMapping("/words")
    public Result updateWordStatus(@RequestParam String userName, @RequestParam Integer wordId, @RequestParam Integer status) throws Exception{
        log.info("更新用户{}单词{}的状态为{}", userName, wordId, status);
        studyService.updateWordStatus(userName, wordId, status);
        return Result.success();
    }

    /**
     * 根据单词原型查询对应例句(包含中文翻译)
     * @param word 单词字符串
     * @return 单词例句
     */
    @GetMapping("/sentence/{word}")
    public Result wordSentence(@PathVariable String word){
        log.info("查询单词{}例句",word);
        return Result.success(studyService.wordSentence(word));
    }

    /**
     * 根据单词原型返回详情页(html格式需要前端手动添加dom元素)
     * @param word 单词原型
     * @return 单词详细释义
     */
    @GetMapping("/detail/{word}")
    public Result wordDeatil(@PathVariable String word){
        log.info("查询单词{}详细释义",word);
        return Result.success(studyService.wordDetail(word));
    }
}
