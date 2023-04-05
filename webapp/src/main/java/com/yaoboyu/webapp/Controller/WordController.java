package com.yaoboyu.webapp.Controller;

import com.yaoboyu.webapp.Pojo.Result;
import com.yaoboyu.webapp.Pojo.Word;
import com.yaoboyu.webapp.Service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Slf4j
@RestController
public class WordController {
    @Autowired
    private WordService wordService;    //自动注入对象

    //根据词书名,页码,每页显示数量返回请求的单词信息
    @GetMapping("/wordlist/{title}/{pagenum}/{pagesize}")
    public Result ListByPage(@PathVariable String title,@PathVariable Integer pagenum,@PathVariable Integer pagesize) {
        log.info("返回词书{}的第{}页的单词,每页{}个", title, pagenum, pagesize);
        return Result.success(wordService.ListByPage(title, pagenum, pagesize));
    }
}
