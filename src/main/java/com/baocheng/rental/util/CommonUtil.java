package com.baocheng.rental.util;

import java.util.regex.Pattern;

/**
 * 工具类
 */
public class CommonUtil {
    /**
     * 检查日期字符串是否合法
     * @param dateStr
     * @return
     */

    public static boolean checkDateStr(String dateStr){
        String pattern="^[1-2][0-9][0-9][0-9][0-1][0-9][0-3][0-9]$";
        boolean isMatch = Pattern.matches(pattern, dateStr);
        if(!isMatch){
            System.out.println("日期不合法,请输入1或者2开头的8位数字");
            return false;
        }

        int year=Integer.parseInt(dateStr.substring(0,4));
        String monthStr=dateStr.substring(4,6);
        String dayStr=dateStr.substring(6,8);
        int month=0;

        if(monthStr.startsWith("0")){
            month=Integer.parseInt(monthStr.substring(1,2));
        }else{
            month=Integer.parseInt(monthStr);
        }

        if(month==0||month>12){
            System.out.println("月份不合法");
            return false;
        }

        int day=0;
        if(dayStr.startsWith("0")){
            day=Integer.parseInt(dayStr.substring(1,2));
        }else{
            day=Integer.parseInt(dayStr);
        }
        if(day==0){
            System.out.println("日份不合法");
            return false;
        }
        //有31天的月份
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            if(day>31){
                System.out.println("日份不合法");
                return false;
            }
        }
        //有30天的月份
        if(month==4||month==6||month==9||month==11){
            if(day>30){
                System.out.println("日份不合法");
                return false;
            }
        }
        if(month==2){
            boolean isLeapYear = false;//闰年标志
            // 判断是否为闰年
            if (year % 4 == 0 && year % 100 != 0 ||year % 400 == 0) {
                isLeapYear = true;
            }
            if(isLeapYear){
                if(day>29){
                    System.out.println("日份不合法");
                    return false;
                }
            }else{
                if(day>28){
                    System.out.println("日份不合法");
                    return false;
                }
            }
        }
        return true;
    }

}
