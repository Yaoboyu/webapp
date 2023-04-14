package com.webapp.webapp.Service;

import com.webapp.webapp.Pojo.Book;

public interface BookService {
    /**
     * 拿到用户所学的词书
     * @param UserName 用户名
     * @return 返回所学的书类对象
     * @throws Exception 可能没学书就返回没学的异常
     */
    Book getCurrentBook(String UserName) throws Exception;
}
