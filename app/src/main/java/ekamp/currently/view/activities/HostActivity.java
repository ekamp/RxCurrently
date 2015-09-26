package ekamp.currently.view.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import ekamp.currently.R;
import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformationCache;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.presenters.WeatherPresenter;
import ekamp.currently.services.WeatherService;
import ekamp.currently.view.adapters.WeatherPagerAdapter;

/**
 * Main host {@link android.app.Activity} of the application. This {@link android.app.Activity} is
 * responsible for rendering the main UI and rendering and supporting the {@link android.support.v4.view.ViewPager}
 * that houses the {@link ekamp.currently.view.fragments.WeatherFragment}s.
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

    @Bind(R.id.forecast_day_tabs)
    TabLayout forecastTabLayout;

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
        weatherPresenter.loadForecast("Red Bank");
    }

    @Override
    public void onCurrentWeatherSuccess(WeatherInformation weatherInformation) {
        WeatherInformationCache.getInstance().addCurrentWeatherInformationToForecast(weatherInformation);
        createWeatherInformationViewPager();
    }

    @Override
    public void onCurrentWeatherError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }

    @Override
    public void onForecastSuccess(ForecastInformation forecastInformation) {
        WeatherInformationCache.getInstance().setForecastInformation(forecastInformation);
        weatherPresenter.loadCurrentWeather("Red Bank");
    }

    @Override
    public void onForecastError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }

    private void createWeatherInformationViewPager() {
        weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(),
                WeatherInformationCache.getInstance().getForecastInformation().getWeeklyWeatherList());
        forecastWeatherViewPager.setAdapter(weatherPagerAdapter);
        forecastTabLayout.setupWithViewPager(forecastWeatherViewPager);
    }
}