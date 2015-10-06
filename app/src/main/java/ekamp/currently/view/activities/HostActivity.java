package ekamp.currently.view.activities;

import android.location.Location;
import android.location.LocationListener;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import ekamp.currently.R;
import ekamp.currently.model.WeatherInformationCache;
import ekamp.currently.presenters.WeatherPresenter;
import ekamp.currently.model.WeatherLocationController;
import ekamp.currently.services.WeatherService;
import ekamp.currently.view.adapters.WeatherPagerAdapter;
import ekamp.currently.view.dialogs.ErrorDialog;

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

    @Bind(R.id.request_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        ButterKnife.bind(this);

        showProgress();
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

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void registerLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //TODO prevent from being tripped first time the location is collected
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
    public void onWeatherInformationCollected() {
        hideProgress();
        createWeatherInformationViewPager();
    }

    @Override
    public void onWeatherInformationCollectionError(Error error) {
        ErrorDialog.newInstance(error.getMessage(),
                getString(R.string.error_default_title),
                getString(R.string.error_dialog_confirmation_button_text)).
                showDialog(getSupportFragmentManager());
    }

    @Override
    public void onForecastError(Error error) {
        ErrorDialog.newInstance(error.getMessage(),
                getString(R.string.error_default_title),
                getString(R.string.error_dialog_confirmation_button_text)).
                showDialog(getSupportFragmentManager());
    }

    @Override
    public void onLocationResolved(String address) {
        requestWeatherInformation(address);
    }

    @Override
    public void onLocationResolutionFailure() {
        hideProgress();
        ErrorDialog.newInstance(
                new ErrorDialog.ErrorDialogClickListener() {
                    @Override
                    public void onDismiss() {
                        finish();
                    }
                },
                getString(R.string.error_location_message),
                getString(R.string.error_default_title),
                getString(R.string.error_dialog_confirmation_button_text)).
                showDialog(getSupportFragmentManager());
    }
}