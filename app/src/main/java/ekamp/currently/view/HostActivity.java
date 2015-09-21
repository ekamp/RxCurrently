package ekamp.currently.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ekamp.currently.R;
import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.presenters.WeatherPresenter;
import ekamp.currently.services.CurrentWeatherService;


/**
 * Main host activity of the application. This activity is responsible for rendering the main UI
 * and rendering and supporting the {@link android.support.v4.view.ViewPager} that houses the
 * weather fragments.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public class HostActivity extends AppCompatActivity implements HostCallBack {

    private WeatherPresenter weatherPresenter;
    private CurrentWeatherService currentWeatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        initWeatherService();
        requestWeatherInformation();
    }

    private void initWeatherService() {
        currentWeatherService = new CurrentWeatherService();
        weatherPresenter = new WeatherPresenter(currentWeatherService, this);
    }

    private void requestWeatherInformation() {
        weatherPresenter.loadCurrentWeather("Red Bank");
        weatherPresenter.loadForecast("Red Bank");
    }

    @Override
    public void onCurrentWeatherSuccess(WeatherInformation weatherInformation) {
        Log.e(getClass().getName(), weatherInformation.toString());
    }

    @Override
    public void onCurrentWeatherError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }

    @Override
    public void onForecastSuccess(ForecastInformation forecastInformation) {
        Log.e(getClass().getName(), forecastInformation.toString());
    }

    @Override
    public void onForecastError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }
}
