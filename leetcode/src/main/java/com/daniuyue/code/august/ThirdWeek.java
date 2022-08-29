package com.daniuyue.code.august;

import com.sun.tools.javac.util.StringUtils;

/**
 * @author 15081
 * 2022-08-23
 */
public class ThirdWeek {
    /**
     * 字符串有三种编辑操作:插入一个英文字符、删除一个英文字符或者替换一个英文字符。
     * 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
     */
    public static boolean oneEditAway(String first, String second) {
        int result = first.length() - second.length();
        if (result > 1 || result < -1){
            return false;
        }
        if (result == 0){
            char[] chars = first.toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {

            }
        }
        return false;
    }

}
