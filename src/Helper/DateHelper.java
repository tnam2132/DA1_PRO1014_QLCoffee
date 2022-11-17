package Helper;

import java.text.*;
import java.util.*;

public class DateHelper {

    static SimpleDateFormat formater = new SimpleDateFormat();

    
    public static String getDate(){
        Date now = new Date();
        SimpleDateFormat formater1 = new SimpleDateFormat();
        formater1.applyPattern("HH:mm:ss dd-M-yyyy");
        String currentTime = formater1.format(now);
        return currentTime;
    }
    /*
    *Chuyển chuỗi thành ngày
    *String s = "01-01-2022";
    *Date date = DateHelper.toDate(s,"dd-MM-yyyy");
    */
    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    *Chuyển ngày thành chuỗi
    *Date now = new Date();
    *String s = DateHelper.toString(now,"dd-MM-yyyy");
    */
    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    /*
    *Đưa vào ngày trước đó hoặc sau đó (số days) ngày 
    *   tùy thuộc vào giá trị (days) âm hay dương
    *Date now = new Date();
    *Date after = DateHelper.addDay(now,10)
    */
    public static Date addDay(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
