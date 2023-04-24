package com.webapp.webapp.Utils;

import cn.hutool.http.HttpUtil;
import com.webapp.webapp.Dto.WordSentence;
import org.jetbrains.annotations.TestOnly;

public class WordUtils {
    //这个我自己写的比较乱了总之返回的是单词的例句和翻译
    public static WordSentence serch(String word) throws Exception{
        //发送get请求
        String s = HttpUtil.createGet("https://dict.hjenglish.com/w/" + word)
                //这个请求头.header是自己项目需要加的，可以省略
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .header("Cookie","HJ_UID=0f406091-be97-6b64-f1fc-f7b2470883e9; HJ_CST=1; HJ_CSST_3=1;TRACKSITEMAP=3%2C; HJ_SID=393c85c7-abac-f408-6a32-a1f125d7e8c6; _REF=; HJ_SSID_3=4a460f19-c0ae-12a7-8e86-6e360f69ec9b; _SREF_3=; HJ_CMATCH=1")
                .execute()
                //返回参数格式utf-8
                .charset("utf-8")
                .body(),s1;
        int l = s.indexOf("<p class=\"def-sentence-from\">");
        int r = s.indexOf(".",l);
        int l1 = s.indexOf("<p class=\"def-sentence-to\">");
        int r1 = s.indexOf("。",l1);
        s1 = s.substring(l,r + 1);
        String s2 = s.substring(l1,r1 + 1);
        l1 = s2.indexOf("\n");
        l = s1.indexOf("\n");
        s1 = s1.substring(l);
        s2 = s2.substring(l);
        int i = 0;
        while(s1.substring(i,i + 1).equals("\n") || s1.substring(i,i + 1).equals(" "))
            i++;
        s1 = s1.substring(i);
        s1 = s1.replace("&#39;","");
        i = 0;
        while(s2.substring(i,i + 1).equals("\n") || s2.substring(i,i + 1).equals(" "))
            i++;
        s2 = s2.substring(i);
        s2 = s2.replace("&#39;","");
        return new WordSentence(word,s1,s2);
    }
    //返回的是html格式的原型
    public static String wordDetail(String word){
        String s = HttpUtil.createGet("https://dict.hjenglish.com/w/" + word)
                //这个请求头.header是自己项目需要加的，可以省略
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .header("Cookie","HJ_UID=0f406091-be97-6b64-f1fc-f7b2470883e9; HJ_CST=1; HJ_CSST_3=1;TRACKSITEMAP=3%2C; HJ_SID=393c85c7-abac-f408-6a32-a1f125d7e8c6; _REF=; HJ_SSID_3=4a460f19-c0ae-12a7-8e86-6e360f69ec9b; _SREF_3=; HJ_CMATCH=1")
                .execute()
                //返回参数格式utf-8
                .charset("utf-8")
                .body(),s1;
        int end = s.indexOf("<footer class=\"word-details-pane-footer\">");
        int begin = s.indexOf("<div class=\"word-details-pane-content\"");
        s = s.substring(begin,end);
        s = s.replace("a href=","a hhref=");
        return s;
    }
}
