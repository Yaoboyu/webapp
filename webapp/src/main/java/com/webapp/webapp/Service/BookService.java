package com.webapp.webapp.Service;

import com.webapp.webapp.Pojo.Book;

public interface BookService {
    Book getCurrentBook(String UserName) throws Exception;
}
