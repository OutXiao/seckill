package com.wenfan.seckill.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/3/9 22:18
 */
public class InputValidator {
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{3,17}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9])|(90[0-9])|(91[0-9])|(92[0-9]))\\d{8}$";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_CHINESE = "^[一-龥],{0,}$";
    public static final Pattern REGEX_CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+");
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    public static final String REGEX_NUMERIC = "[0-9]*";
    public static final String REGEX_DECIMAL = "([0-9]*)|([0-9]*\\.[0-9]*)";
    private static final int MAX_INPUT_LENGTH = 9999;
    private static final int MIN_INPUT_LENGTH = 0;

    public InputValidator() {
    }

    public static boolean isUsername(String username) {
        return Pattern.matches("^[a-zA-Z]\\w{3,17}$", username);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{6,16}$", password);
    }

    public static boolean isNumeric(String str) {
        return str != null && !"".equals(str.trim()) && Pattern.matches("[0-9]*", str);
    }

    public static boolean isDecimal(String str) {
        return str != null && !"".equals(str.trim()) && Pattern.matches("([0-9]*)|([0-9]*\\.[0-9]*)", str);
    }

    public static boolean isMobile(String mobile) {
        return mobile != null && !"".equals(mobile.trim()) && Pattern.matches("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9])|(90[0-9])|(91[0-9])|(92[0-9]))\\d{8}$", mobile);
    }

    public static boolean isEmail(String email) {
        return email != null && !"".equals(email.trim()) && Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
    }

    public static boolean isChinese(String chinese) {
        return chinese != null && !"".equals(chinese.trim()) && Pattern.matches("^[一-龥],{0,}$", chinese);
    }

    public static boolean isIDCard(String idCard) {
        return idCard != null && !"".equals(idCard.trim()) && Pattern.matches("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)", idCard);
    }

    public static boolean isUrl(String url) {
        return url != null && !url.trim().equals("") && Pattern.matches("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
    }

    public static boolean isIPAddr(String ipAddr) {
        return ipAddr != null && !"".equals(ipAddr.trim()) && Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", ipAddr);
    }

    public static int countChinese(String content) {
        if (content != null && !"".equals(content.trim())) {
            int count = 0;
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");

            for (Matcher m = p.matcher(content); m.find(); ++count) {

            }

            return count;
        } else {
            return 0;
        }
    }

    public static boolean isBool(String str) {
        return str != null && !"".equals(str.trim()) && (str.equals("true") || str.equals("false"));
    }

    public static String encodeChinese(String str, String charset) throws UnsupportedEncodingException {
        Matcher m = REGEX_CHINESE_PATTERN.matcher(str);
        StringBuffer b = new StringBuffer();

        while (m.find()) {
            m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
        }

        m.appendTail(b);
        return b.toString();
    }

    public static void checkLength(Object checkObject, int maxLength, int minLength, String tipName) {
        if (maxLength > 9999) {
            maxLength = 9999;
        }

        if (minLength < 0) {
            minLength = 0;
        }

        checkEmpty(checkObject, tipName);
        int length = checkObject.toString().length();
        if (length > maxLength) {
            throw new IllegalArgumentException(tipName + "长度不能超过" + maxLength);
        } else if (length < minLength && length != 0) {
            throw new IllegalArgumentException(tipName + "长度不能小于" + minLength);
        }
    }

    public static void checkEmpty(Object object, String tipName) {
        if (StringUtils.isEmpty(object)) {
            throw new IllegalArgumentException(tipName + "不可为空");
        }
    }

    public static <T> T defaultValue(T defaultValue, T source) {
        return source == null ? defaultValue : source;
    }

    public static void checkDateTime(Date start, Date end) {
        checkEmpty(start, "开始日期");
        checkEmpty(end, "结束日期");
        if (start.getTime() > end.getTime()) {
            throw new IllegalArgumentException("日期范围错误");
        }
    }

    public static void checkDate(Date min, Date max, Date check, String pattern, String tipName) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        checkEmpty(check, tipName);
        if (check.getTime() < min.getTime() || check.getTime() > max.getTime()) {
            throw new IllegalArgumentException(tipName + "有效范围是" + dateFormat.format(min) + "~" + dateFormat.format(max));
        }
    }

    public static void checkNaturalNumber(Integer num, String tipName) {
        if (num < 0) {
            throw new IllegalArgumentException(tipName + "不能小于0");
        }
    }

    public static void checkNaturalNumber(BigDecimal num, String tipName) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(tipName + "不能小于0");
        }
    }

    public static void checkNumber(Integer min, Integer max, Integer check, String tipName) {
        checkEmpty(check, tipName);
        if (check < min || check > max) {
            throw new IllegalArgumentException(tipName + "有效范围是" + min + "~" + max);
        }
    }

    public static void checkNumber(BigDecimal min, BigDecimal max, BigDecimal check, String tipName) {
        checkEmpty(check, tipName);
        if (check.compareTo(min) < 0 || check.compareTo(max) > 0) {
            throw new IllegalArgumentException(tipName + "有效范围是" + min + "~" + max);
        }
    }

}
