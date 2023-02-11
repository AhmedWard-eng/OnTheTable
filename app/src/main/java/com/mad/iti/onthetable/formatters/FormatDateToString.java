package com.mad.iti.onthetable.formatters;

import android.icu.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormatDateToString {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static String getString(Date date){

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return  format.format(date);
    }

    public static String getString(int year, int month, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return  format.format(calendar.getTime());
    }
}
