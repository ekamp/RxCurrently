package ekamp.currently.utils;

import org.joda.time.DateTime;

/**
 * Utility class used to get information about a certain piece of data.
 *
 * @author Erik Kamp
 * @since 10/6/15.
 */
public class DataUtils {

    private DataUtils() {
    }

    /**
     * Determines if the given {@link DateTime} is within three hours of the current time.
     *
     * @param dateTime given time.
     * @return true if the given time is within three hours of the current time.
     */
    public static boolean isLessThanThreeHoursOld(DateTime dateTime) {
        DateTime threeHoursBefore = DateTime.now().minusHours(3);
        return dateTime.isAfter(threeHoursBefore);
    }
}
