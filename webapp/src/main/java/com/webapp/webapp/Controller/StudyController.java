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
    @GetMapping("/words/{username}")
    public Result study(@PathVariable String username){
        log.info("用户{}想要学词",username);
        return Result.success(studyService.getWordsByUserName(username));
    }
}
