package ekamp.currently.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.parsers.CurrentWeatherInformationConverter;
import ekamp.currently.parsers.ForecastInformationConverter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Service to retrieve weather information to display to the user.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public class CurrentWeatherService {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    public static final String TEMP_TYPE_IMPERIAL = "Imperial", TEMP_TYPE_METRIC = "Metric";
    private CurrentWeatherServiceAPI currentWeatherServiceAPI;
    private ForcastedWeatherServiceAPI forcastedWeatherServiceAPI;

    public CurrentWeatherService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        createWeatherServiceAPIAdapter(requestInterceptor);
        createForcastServiceAPIAdapter(requestInterceptor);
    }

    private void createWeatherServiceAPIAdapter(RequestInterceptor requestInterceptor) {
        Gson currentWeatherConverter = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(WeatherInformation.class, new CurrentWeatherInformationConverter())
                .create();

        RestAdapter currentWeatherRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(currentWeatherConverter))
                .build();

        currentWeatherServiceAPI = currentWeatherRestAdapter
                .create(CurrentWeatherServiceAPI.class);
    }

    private void createForcastServiceAPIAdapter(RequestInterceptor requestInterceptor) {
        Gson forecastedInformationConverter = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ForecastInformation.class, new ForecastInformationConverter())
                .create();

        RestAdapter forcastedRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(forecastedInformationConverter))
                .build();

        forcastedWeatherServiceAPI = forcastedRestAdapter
                .create(ForcastedWeatherServiceAPI.class);
    }

    public CurrentWeatherServiceAPI getCurrentWeatherServiceAPI() {
        return currentWeatherServiceAPI;
    }

    public ForcastedWeatherServiceAPI getForcastedWeatherServiceAPI() {
        return forcastedWeatherServiceAPI;
    }

    /**
     * Retrofit service API, used to collect current {@link WeatherInformation}
     */
    public interface CurrentWeatherServiceAPI {
        @GET("/weather")
        Observable<WeatherInformation>
        getWeatherInformation(@Query("q") String cityName, @Query("units") String tempUnitType);
    }

    /**
     * Retrofit service API, used to collect the weekly forecast
     */
    public interface ForcastedWeatherServiceAPI {
        @GET("/forecast")
        Observable<ForecastInformation>
        getWeatherInformation(@Query("q") String cityName, @Query("units") String tempUnitType);
    }
}
