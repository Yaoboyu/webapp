package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Book;
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
     *
     * @param userName 用户名
     * @return 词书信息封装Result
     * @throws Exception 如果用户没有正在学的词书报异常
     */
    @GetMapping("/{userName}")
    public Result getCurrentBook(@PathVariable String userName)throws Exception{
        log.info("查询用户{}所学词书",userName);
        return Result.success(bookService.getCurrentBook(userName));
    }

}