package com.webapp.webapp.Controller;

import com.webapp.webapp.Pojo.Result;
import com.webapp.webapp.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/books")
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

    /**
     * 更新词书状态
     * @param bookId 词书id
     * @param status 状态
     * @return
     */
    @PutMapping()
    public Result updateBookStatus(@RequestParam Integer bookId, @RequestParam Integer status)throws Exception{
        log.info("更新用户词书{}状态为{}", bookId, status);
        bookService.updateBookStatus(bookId, status);
        return Result.success();
    }

    /**
     * 用户添加词书
     * @param bookId 词书id
     * @return
     */
    @PostMapping({"/{bookId}"})
    public Result addBook(@PathVariable Integer bookId)throws Exception{
        log.info("用户添加词书{}", bookId);
        if(bookService.ifBookAdded(bookId) == true){
            return Result.error("用户已经有词书了");
        }
        if(bookService.ifBookExisted(bookId) == false){
            return Result.error("词书不存在");
        }
        bookService.addBook(bookId);
        return Result.success();
    }

    /**
     * 用户选定为当前在背词书
     * @param bookId 词书id
     * @return
     */
    @PostMapping({"/current/{bookId}"})
    public Result setCurrentBook(@PathVariable Integer bookId)throws Exception{
        log.info("用户选定词书{}为当前在背词书", bookId);
        if(bookService.ifBookAdded(bookId) == false){
            log.info("用户添加词书{}", bookId);
            if(bookService.ifBookAdded(bookId) == true){
                return Result.error("用户已经有词书了");
            }
            if(bookService.ifBookExisted(bookId) == false){
                return Result.error("词书不存在");
            }
            bookService.addBook(bookId);
        }
        bookService.updateBookStatus(bookId, 1);
        return Result.success();
    }
}
