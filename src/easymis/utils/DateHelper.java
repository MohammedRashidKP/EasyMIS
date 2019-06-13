package easymis.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author RashidKP
 */
public class DateHelper {

    private static IDateUtils dateUtils = new DateUtils();

    private DateHelper() {

    }

    public static void load(IDateUtils adateUtils) {
        dateUtils = adateUtils;
    }

    public static Date getToday() {
        return dateUtils.getToday();
    }

    public static Date getNextDay(Date currentDate) {
        LocalDate localDate = currentDate.toLocalDate();
        return java.sql.Date.valueOf(localDate.plusDays(1));
    }

    public static Date getPreviousDay(Date currentDate) {
        LocalDate localDate = currentDate.toLocalDate();
        return java.sql.Date.valueOf(localDate.minusDays(1));
    }

    public static Date getFirstDayOfTheYear() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate myDate = LocalDate.of(year, 1, 1);
        Date date = Date.valueOf(myDate);
        return date;
    }

    public static String format(Date date) {
        LocalDate localDate = date.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu");
        return localDate.format(formatter);
    }

}
