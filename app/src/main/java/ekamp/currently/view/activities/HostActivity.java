package ekamp.currently.view.activities;

import android.location.Location;
import android.location.LocationListener;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import ekamp.currently.R;
import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformationCache;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.presenters.WeatherPresenter;
import ekamp.currently.model.WeatherLocationController;
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
public class HostActivity extends BaseCallbackActivity {

    private WeatherPresenter weatherPresenter;
    private WeatherService weatherService;
    private WeatherPagerAdapter weatherPagerAdapter;
    private WeatherLocationController weatherLocationController;
    private LocationListener locationListener;

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
        registerLocationListener();
        weatherLocationController = WeatherLocationController.init(locationListener, this);
        weatherLocationController.requestCurrentAddress();
    }

    private void initWeatherService() {
        weatherService = new WeatherService();
        weatherPresenter = new WeatherPresenter(weatherService, this);
    }

    private void requestWeatherInformation(String address) {
        weatherPresenter.loadForecast(address);
    }

    private void createWeatherInformationViewPager() {
        weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(),
                WeatherInformationCache.getInstance().getForecastInformation().getWeeklyWeatherList());
        forecastWeatherViewPager.setAdapter(weatherPagerAdapter);
        forecastTabLayout.setupWithViewPager(forecastWeatherViewPager);
    }

    private void registerLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //We can skip the initialization as we know the Listener has already been setup
                //TODO prevent from being tripped first time the location is collected
//                weatherLocationController.startLocationToAddressResolutionService();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
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
        weatherPresenter.loadCurrentWeather(weatherLocationController.getLastCollectedAddress());
    }

    @Override
    public void onForecastError(Error error) {
        Log.e(getClass().getName(), "Error " + error.toString());
    }

    @Override
    public void onLocationResolved(String address) {
        weatherLocationController.setLastCollectedAddress(address);
        requestWeatherInformation(address);
    }

    @Override
    public void onLocationResolutionFailure() {
        Log.e(getClass().getName(), "Error could not resolve user's current address ");
    }
}