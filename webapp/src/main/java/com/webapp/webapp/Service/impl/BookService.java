package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Config.ThreadLocalConfig;
import com.webapp.webapp.Mapper.BookMapper;
import com.webapp.webapp.Mapper.UserMapper;
import com.webapp.webapp.Pojo.Book;
import com.webapp.webapp.Pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements com.webapp.webapp.Service.BookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 得到用户现在所学的词书
     * @return 词书信息
     * @throws Exception
     */
    @Override
    public Book getCurrentBook() throws Exception{
        User user = ThreadLocalConfig.getUser();
        long userId = user.getId();
        Long bookId = userMapper.GetBookIdByUserId(userId);
        if(bookId == null)
            return null;
        return bookMapper.GetBookByBookId(bookId);
    }

    /**
     * 更新词书状态
     * @param bookId 词书id
     * @param status 状态
     */
    @Override
    public void updateBookStatus(Integer bookId, Integer status) {
        User user = ThreadLocalConfig.getUser();
        long userId = user.getId();
        bookMapper.UpdateBookStatus(userId, bookId, status);
    }

    /**
     * 判断用户是否已经有词书
     * @param bookId 词书id
     * @return
     */
    @Override
    public boolean ifBookAdded(Integer bookId) {
        User user = ThreadLocalConfig.getUser();
        long userId = user.getId();
        return bookMapper.GetBookByUserId(userId, bookId);
    }

    /**
     * 判断词书是否存在
     * @param bookId 词书id
     * @return
     */
    @Override
    public boolean ifBookExisted(Integer bookId) {
        return bookMapper.GetBookByBookId(bookId) != null;
    }

    /**
     * 用户添加词书
     * @param bookId 词书id
     */
    @Override
    public void addBook(Integer bookId) {
        User user = ThreadLocalConfig.getUser();
        long userId = user.getId();
        bookMapper.InsertUserBook(userId, bookId);
    }
}
