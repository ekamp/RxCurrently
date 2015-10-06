package ekamp.currently.model;

import org.joda.time.DateTime;

import ekamp.currently.utils.DataUtils;

/**
 * Cached weather information from latest request. This information should be invalidated every
 * three hours.
 *
 * @author Erik Kamp
 * @since 9/25/15.
 */
public class WeatherInformationCache {

    private static WeatherInformationCache weatherInformationCache;
    private static ForecastInformation forecastInformation;
    private DateTime lastUpdatedTime;

    public static WeatherInformationCache getInstance() {
        if (weatherInformationCache == null) {
            weatherInformationCache = new WeatherInformationCache();
        }
        return weatherInformationCache;
    }

    /**
     * Adds the current {@link WeatherInformation} to our cached {@link ForecastInformation}.
     *
     * @param weatherInformation current {@link WeatherInformation} to be added to the cache.
     */
    public void addCurrentWeatherInformationToForecast(WeatherInformation weatherInformation) {
        if (forecastInformation == null)
            forecastInformation = new ForecastInformation();
        forecastInformation.getWeeklyWeatherList().add(0, weatherInformation);
    }

    /**
     * Sets the cached {@link ForecastInformation}.
     *
     * @param forecastInformation {@link ForecastInformation} to be cached.
     */
    public void storeForecastInformation(ForecastInformation forecastInformation) {
        WeatherInformationCache.forecastInformation = forecastInformation;
        lastUpdatedTime = DateTime.now();
    }

    /**
     * Retrieves the cached {@link ForecastInformation}
     *
     * @return currently cached {@link ForecastInformation}
     */
    public ForecastInformation getForecastInformation() {
        return forecastInformation;
    }

    /**
     * Determines if the currently cached {@link ForecastInformation} is valid.
     *
     * @return true if the data is valid, false if no data or if the data is expired.
     */
    public boolean hasValidForecastInformation() {
        return lastUpdatedTime != null && DataUtils.isLessThanThreeHoursOld(lastUpdatedTime);
    }
}
