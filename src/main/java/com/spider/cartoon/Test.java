package com.spider.cartoon;

public class Test {
    
    public static void main(String[] args)
    {
        String aa ="//static.mkzcdn.com/home/assets/images/read/lazyload_img.png?v=6c7d958";
        aa = aa.replace("https://www.mkzhan.com/", "");
        System.out.println(aa.split("/")[0]);
        System.out.println(aa.split("/")[1].split("\\.")[0]);
        System.out.println(aa.startsWith("//static") );
      }

}
