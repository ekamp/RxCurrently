package ekamp.currently.model;

/**
 * Contains information relating to the weather such as temperature and humidity.
 *
 * @author Erik Kamp
 * @since 09/20/2015
 */
public class WeatherInformation {

    private Temperature temperatureInformation;
    private Wind windInformation;
    private WeatherDescription weatherDescription;

    public WeatherInformation() {
    }

    public Temperature getTemperatureInformation() {
        return temperatureInformation;
    }

    public Wind getWindInformation() {
        return windInformation;
    }

    public WeatherDescription getWeatherDescription() {
        return weatherDescription;
    }

    public void setTemperatureInformation(Temperature temperatureInformation) {
        this.temperatureInformation = temperatureInformation;
    }

    public void setWeatherDescription(WeatherDescription weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setWindInformation(Wind windInformation) {
        this.windInformation = windInformation;
    }

    @Override
    public String toString() {
        return "WeatherInformation{" +
                "temperatureInformation=" + temperatureInformation +
                ", windInformation=" + windInformation +
                ", weatherDescription=" + weatherDescription +
                '}';
    }
}
