package com.wenfan.seckill.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/2/12 16:06
 */
public class DateUtils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static Date stringConvertToDate(String dateString) throws NullPointerException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dateString != null) {
                return format.parse(dateString);
            } else {
                throw new NullPointerException();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
