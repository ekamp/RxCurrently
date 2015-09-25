package ekamp.currently.model;

/**
 * Model object that holds a general description of the weather condition
 * and an icon of the weather condition.
 *
 * @author Erik Kamp
 * @since 9/25/15.
 */
public class WeatherDescription {

    private String icon, generalDescription;
    private static String DEFAULT_ICON_VALUE = "01n", DEFAULT_GENERAL_DESCRIPTION_VALUE = "Sky is clear",
            ICON_URL_PREFIX = "https://openweathermap.org/img/w/", ICON_URL_POSTFIX = ".png";

    public WeatherDescription(String icon, String generalDescription) {
        this.icon = icon;
        this.generalDescription = generalDescription;
    }

    public static WeatherDescription getDefaultValue() {
        return new WeatherDescription(DEFAULT_ICON_VALUE, DEFAULT_GENERAL_DESCRIPTION_VALUE);
    }

    public String getIcon() {
        return icon;
    }

    /**
     * Appends the correct Open Weather prefix, in order to provide the correct url for the weather icon.
     *
     * @return formatted icon url.
     */
    public String getIconUrlPath() {
        return new StringBuilder(ICON_URL_PREFIX).append(icon).append(ICON_URL_POSTFIX).toString();
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    @Override
    public String toString() {
        return "WeatherDescription{" +
                "icon='" + icon + '\'' +
                ", generalDescription='" + generalDescription + '\'' +
                '}';
    }
}
