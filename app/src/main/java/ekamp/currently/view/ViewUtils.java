package ekamp.currently.view;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ekamp.currently.model.WeatherInformation;

/**
 * Utility class used to aid in formatting data for display within the application.
 *
 * @author Erik Kamp
 * @since 9/25/15.
 */
public class ViewUtils {

    public static final String DATE_FORMAT = "MM/dd hh:mma";
    public static final String CURRENT_TIME_DEFAULT = "Current Weather";

    public static String formatDateTimeForDisplay(DateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        return dateTimeFormatter.print(dateTime);
    }

    public static String formatWeatherInformationTabTitle(WeatherInformation weatherInformation) {
        if (weatherInformation.getTimeWindow() == null) {
            return CURRENT_TIME_DEFAULT;
        } else {
            return formatDateTimeForDisplay(weatherInformation.getTimeWindow());
        }
    }
}
