package com.webapp.webapp.Pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Book
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    /**
     * 词书id，自增主键
     */
    private long id;
    /**
     * 封面地址
     */
    private String cover;
    /**
     * 词书大小
     */
    private String size;
    /**
     * 词书标题
     */
    private String title;
    /**
     * 单词数
     */
    private long wordNum;
}