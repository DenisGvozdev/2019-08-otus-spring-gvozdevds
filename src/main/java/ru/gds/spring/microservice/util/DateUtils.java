package ru.gds.spring.microservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date getDateFromString(String dateString, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
