package br.com.banco.utils.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public class DateUtilsTest {

    @Autowired
    DateUtils dateTimeUtils;

//    @BeforeEach
//    void setup() {
//        dateTimeUtils = new DateUtils();
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testGetDefaultStartDate() {
        Timestamp result = dateTimeUtils.getDefaultStartDate();

        Assertions.assertEquals(new Timestamp(0), result);
    }

    @Test
    void testGetDefaultEndDate() {
        Timestamp result = dateTimeUtils.getDefaultEndDate();

        Assertions.assertTrue(result.getTime() <= System.currentTimeMillis());
    }

    @Test
    void testDefineTimeStamps_withStartAndEndDates() {
        Timestamp startDate = new Timestamp(1640995200000L);
        Timestamp endDate = new Timestamp(1672531199000L);

        Timestamp[] timestamps = dateTimeUtils.defineTimesStamps(startDate, endDate);

        Assertions.assertArrayEquals(new Timestamp[]{startDate, endDate}, timestamps);
    }

    @Test
    void testDefineTimeStamps_withNullDates() {
        Timestamp[] timestamps = dateTimeUtils.defineTimesStamps(null, null);

        Assertions.assertEquals(dateTimeUtils.getDefaultStartDate(), timestamps[0]);
        Assertions.assertTrue(timestamps[1].getTime() <= System.currentTimeMillis());
    }

    @Test
    void testFormatTimestampToString() {
        Timestamp timestamp = new Timestamp(1640995200000L);

        String result = dateTimeUtils.formatTimestampToString(timestamp);

        Assertions.assertEquals("2021-12-31%2021:00:00", result);
    }
}
