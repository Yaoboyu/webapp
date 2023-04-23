package com.webapp.webapp.Service;

import com.webapp.webapp.Dto.WordSentence;
import com.webapp.webapp.Pojo.Word;

import java.util.List;

public interface StudyService {
    /**
     * 根据用户名返回用户应该要学的词组
     * @param UserName 用户名
     * @return 单词词组
     */
    List<Word> getWordsByUserName(String UserName) throws Exception;

    void updateWordStatus(String userName, Integer wordId, Integer status) throws Exception;
    WordSentence wordSentence(String word);
    String wordDetail(String word);
}
