package br.com.banco.utils.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    public static String formatTimestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
        String formattedTimestamp = dateFormat.format(timestamp);
        return formattedTimestamp;
    }
}
