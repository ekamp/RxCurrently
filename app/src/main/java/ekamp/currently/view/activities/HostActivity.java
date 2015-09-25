package ekamp.currently.view.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import ekamp.currently.R;
import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.presenters.WeatherPresenter;
import ekamp.currently.services.WeatherService;
import ekamp.currently.view.adapters.WeatherPagerAdapter;


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
    private WeatherService weatherService;
    private WeatherPagerAdapter weatherPagerAdapter;

    @Bind(R.id.forecast_weather_view_pager)
    ViewPager forecastWeatherViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        ButterKnife.bind(this);
        initWeatherService();
        requestWeatherInformation();
    }

    private void initWeatherService() {
        weatherService = new WeatherService();
        weatherPresenter = new WeatherPresenter(weatherService, this);
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
        createForecastViewPagerWithData(forecastInformation);
    }

    @Override
    public void onForecastError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }

    private void createForecastViewPagerWithData(ForecastInformation forecastInformation) {
        weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), forecastInformation.getWeeklyWeatherList());
        forecastWeatherViewPager.setAdapter(weatherPagerAdapter);
    }
}