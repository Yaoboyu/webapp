package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Config.ThreadLocalConfig;
import com.webapp.webapp.Exception.MyException;
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
        long userId = user.getUserId();
        Long bookId = userMapper.GetBookIdByUserId(userId);
        if(bookId == null)
            return null;
        return bookMapper.GetBookByBookId(bookId);
    }
}
