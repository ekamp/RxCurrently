package ekamp.currently.model;

/**
 * Cached weather information from latest request. This information should be invalidated every
 * three hours.
 *
 * @author Erik Kamp
 * @since 9/25/15.
 */
public class WeatherInformationCache {

    //TODO setup Handler Thread to include 3 hour time to invalidate our cache

    private static WeatherInformationCache weatherInformationCache;
    private static ForecastInformation forecastInformation;

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
    public void setForecastInformation(ForecastInformation forecastInformation) {
        WeatherInformationCache.forecastInformation = forecastInformation;
    }

    /**
     * Retrieves the cached {@link ForecastInformation}
     *
     * @return currently cached {@link ForecastInformation}
     */
    public ForecastInformation getForecastInformation() {
        return forecastInformation;
    }
}
