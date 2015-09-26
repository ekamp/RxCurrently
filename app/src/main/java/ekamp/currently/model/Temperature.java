package ekamp.currently.model;

/**
 * Contains all weather information relating to the temperature.
 *
 * @author Erik Kamp
 * @since 09/20/2015
 */
public class Temperature {

    public static final int DEFAULT_TEMP_VALUE = 0;
    public static final char DEGREE_SYMBOL = '\u00B0';
    private double minTemp, maxTemp, humidity, currentTemp;

    public Temperature(double minTemp, double maxTemp, double humidity, double currentTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.currentTemp = currentTemp;
    }

    public static Temperature getDefaultValue() {
        return new Temperature(DEFAULT_TEMP_VALUE, DEFAULT_TEMP_VALUE, DEFAULT_TEMP_VALUE, DEFAULT_TEMP_VALUE);
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getMinTempForDisplay() {
        return Double.toString(minTemp) + DEGREE_SYMBOL;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getMaxTempForDisplay() {
        return Double.toString(maxTemp) + DEGREE_SYMBOL;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getHumidityForDisplay() {
        return Double.toString(humidity);
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentTempForDisplay() {
        return Double.toString(currentTemp) + DEGREE_SYMBOL;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", humidity=" + humidity +
                ", currentTemp=" + currentTemp +
                '}';
    }
}
