package ekamp.currently.model;

import java.util.ArrayList;

/**
 * Contains information relating to the weekly forecast.
 *
 * @author Erik Kamp
 * @since 9/20/15.
 */
public class ForecastInformation {

    private ArrayList<WeatherInformation> weeklyWeatherList;

    public ForecastInformation(ArrayList<WeatherInformation> weeklyWeatherList) {
        this.weeklyWeatherList = weeklyWeatherList;
    }

    public ForecastInformation() {
    }

    public void setWeeklyWeatherList(ArrayList<WeatherInformation> weeklyWeatherList) {
        this.weeklyWeatherList = weeklyWeatherList;
    }

    public ArrayList<WeatherInformation> getWeeklyWeatherList() {
        return weeklyWeatherList;
    }

    @Override
    public String toString() {
        return "ForecastInformation{" +
                "weeklyWeatherList=" + weeklyWeatherList +
                '}';
    }
}
