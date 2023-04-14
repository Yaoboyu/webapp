package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Service.impl.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result study(@PathVariable String userName){
        log.info("用户{}想要学词", userName);
        return Result.success(studyService.getWordsByUserName(userName));
    }
}
