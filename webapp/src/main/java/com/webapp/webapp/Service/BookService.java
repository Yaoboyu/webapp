package com.webapp.webapp.Service;

import com.webapp.webapp.Pojo.Book;

public interface BookService {
    /**
     * 拿到用户所学的词书
     * @param UserName 用户名
     * @return 返回所学的书类对象
     * @throws Exception 可能没学书就返回没学的异常
     */
    Book getCurrentBook() throws Exception;

    /**
     * 更新词书状态
     * @param bookId 词书id
     * @param status 状态
     */
    void updateBookStatus(Integer bookId, Integer status);

    /**
     * 判断用户是否已经有词书
     * @param bookId 词书id
     * @return
     */
    boolean ifBookAdded(Integer bookId);

    /**
     * 判断词书是否存在
     * @param bookId 词书id
     * @return
     */
    boolean ifBookExisted(Integer bookId);

    /**
     * 用户添加词书
     * @param bookId 词书id
     */
    void addBook(Integer bookId);
}
