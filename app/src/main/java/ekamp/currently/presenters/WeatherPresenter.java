package ekamp.currently.presenters;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.model.WeatherInformationCache;
import ekamp.currently.services.WeatherService;
import ekamp.currently.view.activities.BaseCallbackActivity;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Reactive communication link between current weather fragment and the weather service.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public class WeatherPresenter {

    public WeatherService weatherService;
    private BaseCallbackActivity baseCallbackActivity;

    public WeatherPresenter(WeatherService weatherService, BaseCallbackActivity baseCallbackActivity) {
        this.weatherService = weatherService;
        this.baseCallbackActivity = baseCallbackActivity;
    }

    /**
     * Starts and registers the current daily weather request so that the request is backgrounded
     * and the main thread receives data updates via an {@link Observer}.
     *
     * @param cityName name of the city in which to grab weather updates from.
     */
    public void loadCurrentWeather(String cityName) {
        weatherService.getCurrentWeatherServiceAPI()
                .getWeatherInformation(cityName, WeatherService.TEMP_TYPE_IMPERIAL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInformation>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Notify about an error
                        baseCallbackActivity.onWeatherInformationCollectionError(new Error(e));
                    }

                    @Override
                    public void onNext(WeatherInformation weatherInformation) {
                        //Notify about completion or next event
                        WeatherInformationCache.getInstance().addCurrentWeatherInformationToForecast(weatherInformation);
                        baseCallbackActivity.onWeatherInformationCollected();
                    }
                });
    }

    /**
     * Starts and registers the forecast weather request so that the request is backgrounded
     * and the main thread receives data updates via an {@link Observer}.
     *
     * @param cityName name of the city in which to grab weather updates from.
     */
    public void loadForecast(final String cityName) {
        if (WeatherInformationCache.getInstance().hasValidForecastInformation()) {
            loadCurrentWeather(cityName);
            return;
        }
        weatherService.getForcastedWeatherServiceAPI()
                .getWeatherInformation(cityName, WeatherService.TEMP_TYPE_IMPERIAL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastInformation>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Notify about an error
                        baseCallbackActivity.onForecastError(new Error(e));
                    }

                    @Override
                    public void onNext(ForecastInformation forecastInformation) {
                        //Notify about completion or next event
                        WeatherInformationCache.getInstance().storeForecastInformation(forecastInformation);
                        loadCurrentWeather(cityName);
                    }
                });
    }
}
