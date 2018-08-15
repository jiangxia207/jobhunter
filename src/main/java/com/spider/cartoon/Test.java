package com.spider.cartoon;

public class Test {
    
    public static void main(String[] args)
    {
        String aa ="http://img.fox800.xyz/images/book_43_chapter_1998_20.jpg?x-oss-process=image/resize,m_lfit,w_640,limit_0/auto-orient,1/quality,Q_90";
        System.out.println(aa.split("\\?")[0]);
    }

}
