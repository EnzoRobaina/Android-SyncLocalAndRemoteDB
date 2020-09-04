package com.enzorobaina.synclocalandremotedb.converters;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static final String TEMPLATE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(TEMPLATE);

    @TypeConverter
    public static Date fromString(String date){
        Date result = null;
        try {
            result = dateFormat.parse(date); // 2020-09-04T19:27:19.211071
        }
        catch (ParseException e) { e.printStackTrace(); }
        return result;
    }

    @TypeConverter
    public static String fromDate(Date date){
        return dateFormat.format(date);
    }
}
