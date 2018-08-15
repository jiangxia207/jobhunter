package com.spider.cartoon.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * String工具类
 * @author wubin
 * @date 2016年7月28日 下午1:36:20 
 * @version V1.1.0
 */
public class StringUtilies {

    /**
     * <p>Capitalizes a String changing the first character to title case as
     * per {@link Character#toTitleCase(char)}. No other characters are changed.</p>
     *
     * <p>For a word based algorithm, see {@link org.apache.commons.lang3.text.WordUtils#capitalize(String)}.
     * A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * StringUtils.capitalize("'cat'") = "'cat'"
     * </pre>
     *
     * @param str the String to capitalize, may be null
     * @return the capitalized String, {@code null} if null String input
     * @see org.apache.commons.lang3.text.WordUtils#capitalize(String)
     * @see #uncapitalize(String)
     * @since 2.0
     */
    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        final char newChar = Character.toTitleCase(firstChar);
        if (firstChar == newChar) {
            // already capitalized
            return str;
        }

        char[] newChars = new char[strLen];
        newChars[0] = newChar;
        str.getChars(1,strLen, newChars, 1);
        return String.valueOf(newChars);
    }
	/**
     * 获取一定长度的随机字符串
     * 
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length)
    {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
   
    

    /**
     * 字符串是否为NULL或空
     *
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        boolean y = false;
        if (s == null) {
            y = true;
        } else if (s.trim().equals("")) {
            y = true;
        }
        return y;
    }
    public static boolean isNotBlank(String s){
        return !isNullOrEmpty(s);
    }


    /**
     * 将一个字符串转为整型
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int string2Int(String str, int defaultValue) {
        int v = defaultValue;
        try {
            if (!StringUtilies.isNullOrEmpty(str)) {
                v = Integer.parseInt(str.trim());
            }
        } catch (NumberFormatException ex) {
            v = defaultValue;
        }
        return v;
    }

    /**
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static double string2Number(String str, double defaultValue) {
        double v = defaultValue;
        try {
            if (!StringUtilies.isNullOrEmpty(str)) {
                v = Double.parseDouble(str.trim());
            }
        } catch (NumberFormatException ex) {
            v = defaultValue;
        }
        return v;
    }

    /**
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static boolean string2Boolean(String str, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(str.trim());
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 连接数组中的字符串
     *
     * @param array
     * @param split
     * @return
     */
    public static String conArray(String[] array, String split) {
        StringBuilder str = new StringBuilder();
        if (array != null) {
            for (String a : array) {
                str.append(",").append(a);
            }
        }
        return str.deleteCharAt(0).toString();
    }

    /**
     * compress give string use zip format.
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes("utf-8"));
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * uncompress give string use zip format.
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString("utf-8");
    }

    /**
     * format a string by give parameters.
     *
     * @param msg for example, 'come on, ${user}, let's ${act}.'
     * @param params for example, {user='john', act='moving'}
     * @return formatted string.
     */
    public static String format(String msg, Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("\\$\\{\\w+\\}");
        Matcher matcher = p.matcher(msg);
        while (matcher.find()) {
            String ms = matcher.group();
            String _ms = ms.replaceAll("\\{", "").replaceAll("\\}", "");
            matcher.appendReplacement(sb, params.containsKey(_ms) ? params.get(_ms).toString() : ms.substring(1));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * string的格式化
     * format:你好，%s
     * args:jack
     * 结果：你好，jack
     * @author wubin
     * @param format
     * @param args
     * @return
     */
    public static String format(String format, Object... args) {
        try(Formatter fmt = new Formatter();) {
            return fmt.format(format, args).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

  
    /**
     * 将list转换为逗号分隔的字符串
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {  
        StringBuilder sb = new StringBuilder();  
        if (list != null && list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                if (i < list.size() - 1) {  
                    sb.append(list.get(i) + ",");  
                } else {  
                    sb.append(list.get(i));  
                }  
            }  
        }  
        return sb.toString();  
    }  
    
    
    /**
     * 判断输入是否是手机号码
     * @author jiangxia
     * @param number
     * @return
     */
    public static boolean isMobilePhone(String number)
    {
        if(StringUtilies.isNullOrEmpty(number))
        {
            return false;
        }
        String regExp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(number);
        return m.find();
    }
    
    
    /**
     * 判断输入是否是手机号码
     * @author jiangxia
     * @param number
     * @return
     */
    public static boolean isMail(String input)
    {
        if(StringUtilies.isNullOrEmpty(input))
        {
            return false;
        }
        String regExp = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(input);
        return m.find();
    }

    /**
     * String 转Date
     * @author jiangxia
     * @param number
     * @return
     */
    public static Date String2Date(String input)
    {
        if(StringUtilies.isNotBlank(input))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date time = null;
            try {
                time = sdf.parse(input);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return time;
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        System.out.println(isMobilePhone("18702868748"));
        System.out.println(isMobilePhone("1322299571"));
        
        System.out.println(isMail("jiangxia207@163.com"));
        System.out.println(isMail("@jiangxia207@163.com"));
    }
    
    public static boolean isBlank(final CharSequence cs) {
            int strLen;
            if (cs == null || (strLen = cs.length()) == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if (Character.isWhitespace(cs.charAt(i)) == false) {
                    return false;
                }
            }
            return true;
    }

    public static boolean compareTo(String o1, String o2)
    {
        if(o1 == null && o2 ==null)
        {
            return true;
        }
        else if(o1 == null || o2 ==null)
        {
            return false;
        }
        else
        {
            return o1.equals(o2);
        }
    }
}
