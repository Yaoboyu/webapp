package com.yaoboyu.webapp.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    private int id;
    private String word;
    private int count = 0;
    private LocalDate lasttime = null;
}

