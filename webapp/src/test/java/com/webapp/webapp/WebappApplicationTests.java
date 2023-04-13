package com.webapp.webapp;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class WebappApplicationTests {
    @Resource
    private StudyMapper studyMapper;
    @Test
    public void test(){
        System.out.println(studyMapper.AllWordsId());
    }
}
