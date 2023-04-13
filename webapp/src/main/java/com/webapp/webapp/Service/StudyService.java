package com.webapp.webapp.Service;

import com.webapp.webapp.Pojo.Word;

import java.util.List;

public interface StudyService {
    public List<Word> getWordsByUserName(String UserName);
}
