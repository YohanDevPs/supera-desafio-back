package br.com.banco.utils;

import java.sql.Timestamp;

public class TimeUtils {

    public static Timestamp getDefaultStartDate() {
        return new Timestamp(0);
    }

    public static Timestamp getDefaultEndDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}
