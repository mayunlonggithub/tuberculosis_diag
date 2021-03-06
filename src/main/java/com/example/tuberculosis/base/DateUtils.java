package com.example.tuberculosis.base;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by shihuajie on 2019-08-08
 */
public abstract class DateUtils {

    private static String DefaultDateTimePattern = "yyyy-MM-dd hh:mm:ss";

    private static String DefaultDatePattern = "yyyy-MM-dd";


    public static Date parse(String dateStr) {
        try {
            if(StringUtils.isBlank(dateStr))
                return null;
            else {
                Date date = null;
                dateStr = StringUtils.trim(dateStr);
                if(dateStr.length() <= 10){
                    date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, DefaultDatePattern);
                }
                else {
                    date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, DefaultDateTimePattern);
                }
                return date;
            }
        }
        catch (ParseException e){
            throw new IllegalArgumentException("传入的日期值格式不正确"+ dateStr,e);
        }

    }
}
