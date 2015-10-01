package ekamp.currently.view.activities;

import android.support.v7.app.AppCompatActivity;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;

/**
 * Interface to be used to call back to the activity after a service has completed.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public abstract class BaseCallbackActivity extends AppCompatActivity {

    /**
     * Called when the weather information has downloaded successfully.
     *
     * @param weatherInformation successfully collected information relating to the current weather.
     */
    public abstract void onCurrentWeatherSuccess(WeatherInformation weatherInformation);

    /**
     * Called when the weather information request encountered an error.
     *
     * @param error error encountered during request.
     */
    public abstract void onCurrentWeatherError(Error error);

    /**
     * Called when the forecast information has downloaded successfully.
     *
     * @param forecastInformation successfully collected information relating to weekly forecast.
     */
    public abstract void onForecastSuccess(ForecastInformation forecastInformation);

    /**
     * Called when the forecast information request encountered an error.
     *
     * @param error error encountered during request.
     */
    public abstract void onForecastError(Error error);

    /**
     * Called when the location has been successfully parsed from {@link android.location.Location}
     * to a readable {@link String}.
     *
     * @param address human readable {@link String} to be used to display and fetch forecast information.
     */
    public abstract void onLocationResolved(String address);

    /**
     * Called when the {@link android.location.Location} could not properly be parsed by the
     * {@link ekamp.currently.services.LocationToAddressService}
     */
    public abstract void onLocationResolutionFailure();
}
