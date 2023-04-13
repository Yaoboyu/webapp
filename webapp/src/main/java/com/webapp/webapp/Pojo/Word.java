package com.webapp.webapp.Pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Word
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    /**
     * 单词翻译，中文短释义
     */
    private String translation;
    /**
     * 单词英文
     */
    private String word;
    /**
     * 自增主键
     */
    private long id;
}