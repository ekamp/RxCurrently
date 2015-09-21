package ekamp.currently.view;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;

/**
 * Interface to be used to call back to the activity after a service has completed.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public interface HostCallBack {

    /**
     * Called when the weather information has downloaded successfully.
     *
     * @param weatherInformation successfully collected information relating to the current weather.
     */
    void onCurrentWeatherSuccess(WeatherInformation weatherInformation);

    /**
     * Called when the weather information request encountered an error.
     *
     * @param error error encountered during request.
     */
    void onCurrentWeatherError(Error error);

    /**
     * Called when the forecast information has downloaded successfully.
     *
     * @param forecastInformation successfully collected information relating to weekly forecast.
     */
    void onForecastSuccess(ForecastInformation forecastInformation);

    /**
     * Called when the forecast information request encountered an error.
     *
     * @param error error encountered during request.
     */
    void onForecastError(Error error);
}
