package com.webapp.webapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordSentence {
    //单词原型
    private String word;
    //单词外文
    private String sentence;
    //单词中文
    private String senteceMean;
}
