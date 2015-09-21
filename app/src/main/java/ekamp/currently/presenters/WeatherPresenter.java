package ekamp.currently.presenters;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.services.CurrentWeatherService;
import ekamp.currently.view.HostCallBack;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Reactive communication link between current weather fragment and the weather service.
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class WeatherPresenter {

    public CurrentWeatherService currentWeatherService;
    private HostCallBack hostCallBack;

    public WeatherPresenter(CurrentWeatherService currentWeatherService, HostCallBack hostCallBack) {
        this.currentWeatherService = currentWeatherService;
        this.hostCallBack = hostCallBack;
    }

    public void loadCurrentWeather(String cityName) {
        currentWeatherService.getCurrentWeatherServiceAPI()
                .getWeatherInformation(cityName, CurrentWeatherService.TEMP_TYPE_IMPERIAL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInformation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Notify about an error
                        hostCallBack.onCurrentWeatherError(new Error(e));
                    }

                    @Override
                    public void onNext(WeatherInformation weatherInformation) {
                        //Notify about completion or next event
                        hostCallBack.onCurrentWeatherSuccess(weatherInformation);
                    }
                });
    }

    public void loadForecast(String cityName) {
        currentWeatherService.getForcastedWeatherServiceAPI()
                .getWeatherInformation(cityName, CurrentWeatherService.TEMP_TYPE_IMPERIAL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastInformation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Notify about an error
                        hostCallBack.onForecastError(new Error(e));
                    }

                    @Override
                    public void onNext(ForecastInformation forecastInformation) {
                        //Notify about completion or next event
                        hostCallBack.onForecastSuccess(forecastInformation);
                    }
                });
    }
}
