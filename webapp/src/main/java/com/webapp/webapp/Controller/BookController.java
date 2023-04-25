package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    /**
     *  用户id从线程变量中获取
     * @return 词书信息封装Result
     * @throws Exception 如果用户没有正在学的词书报异常
     */
    @GetMapping()
    public Result getCurrentBook()throws Exception{
        log.info("查询用户所学词书");
        return Result.success(bookService.getCurrentBook());
    }

}
