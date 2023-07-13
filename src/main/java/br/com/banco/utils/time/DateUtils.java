package br.com.banco.utils.time;

import java.sql.Timestamp;

public class DateUtils {

    public static Timestamp getDefaultStartDate() {
        return new Timestamp(0);
    }

    public static Timestamp getDefaultEndDate() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp[] defineTimesStamps(Timestamp startDate, Timestamp endDate) {
        Timestamp[] timestamps = new Timestamp[2];

        if(startDate == null) {
            timestamps[0] = getDefaultStartDate();
        } else {
            timestamps[0] = startDate;
        }

        if (endDate == null) {
            timestamps[1] = getDefaultEndDate();
        } else {
            timestamps[1] = endDate;
        }

        return timestamps;
    }
}
