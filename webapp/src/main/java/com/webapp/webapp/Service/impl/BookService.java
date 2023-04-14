package com.webapp.webapp.Service.impl;

import com.webapp.webapp.Exception.MyException;
import com.webapp.webapp.Mapper.BookMapper;
import com.webapp.webapp.Mapper.UserMapper;
import com.webapp.webapp.Pojo.Book;
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
     * @param UserName 用户名
     * @return 词书信息
     * @throws Exception
     */
    @Override
    public Book getCurrentBook(String UserName) throws Exception{
        Integer id = userMapper.GetUserIdByUserName(UserName);
        id = userMapper.GetBookIdByUserId(id);
        if(id == null)
            throw new MyException("用户没有正在学习的词书!");
        return bookMapper.GetBookByBookId(id);
    }
}
