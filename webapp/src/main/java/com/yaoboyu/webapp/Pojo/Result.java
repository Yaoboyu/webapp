package com.yaoboyu.webapp.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private int code;   //响应结果 1代表成功 0代表失败
    private String msg;    //服务器响应备注信息
    private Object data;    //响应结果
    public static Result success(Object data){
        return new Result(1,"success",data);
    }   //定义success方法便于返回响应
    public static Result success(){
        return new Result(1,"success",null);
    }   //同上定义便于返回无data响应
}
