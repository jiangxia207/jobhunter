package com.spider.cartoon.common;

public class CommonConstants {

    
    public static final Integer zero = new Integer(0);
    
    
    
    public enum SourceType {
        mkz("漫客栈"),
        mg("玫瑰");
        
        SourceType(String type) {
            this.type = type;
        }

        private String type;

        public String getType(){
            return type;
        }
    }
    
    public enum FeeType {
        free("免费"),
        charge("收费"),
        vip("VIP");
        
        FeeType(String type) {
            this.type = type;
        }

        private String type;

        public String getType(){
            return type;
        }
    }
}
