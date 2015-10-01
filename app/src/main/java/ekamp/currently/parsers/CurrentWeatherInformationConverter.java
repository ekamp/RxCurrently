package ekamp.currently.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ekamp.currently.model.Temperature;
import ekamp.currently.model.WeatherDescription;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.model.Wind;

/**
 * Converts from the REST response to {@link WeatherInformation}.
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public class CurrentWeatherInformationConverter implements JsonDeserializer<WeatherInformation> {

    private static final String TAG_MAIN_WEATHER = "main", TAG_TEMP_CURRENT = "temp", TAG_TEMP_HUMIDITY = "humidity",
            TAG_TEMP_MIN = "temp_min", TAG_TEMP_MAX = "temp_max", TAG_WIND = "wind", TAG_WIND_SPEED = "speed",
            TAG_WIND_DIRECTION = "deg", TAG_WEATHER_DESCRIPTION_ROOT = "weather", TAG_WEATHER_DESCRIPTION = "description",
            TAG_WEATHER_ICON = "icon";

    public static final String ERROR_TEMP_PARSE = "Temperature parsing error related to missing field",
            ERROR_WIND_PARSE = "Wind parsing error related to missing field",
            ERROR_DESCRIPTION_PARSE = "Weather parsing error related to missing field";

    @Override
    public WeatherInformation deserialize(JsonElement rootJsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = rootJsonElement.getAsJsonObject();
        return parseWeatherInformation(jsonObject);
    }

    /**
     * Parses a weather {@link JsonObject} into its respective POJO {@link WeatherInformation}
     *
     * @param rootJsonWeatherObject object to be parsed
     * @return parsed {@link WeatherInformation}
     * @throws JsonParseException this is thrown when the Json weather information representation is missing a field.
     */
    public static WeatherInformation parseWeatherInformation(JsonObject rootJsonWeatherObject) {
        WeatherInformation weatherInformation = new WeatherInformation();
        Temperature temperature;
        WeatherDescription weatherDescription;
        Wind wind;

        try {
            if (rootJsonWeatherObject.has(TAG_MAIN_WEATHER)) {
                temperature = parseTemperatureInformation(rootJsonWeatherObject.get(TAG_MAIN_WEATHER).getAsJsonObject());
            } else {
                temperature = Temperature.getDefaultValue();
            }
        } catch (JsonParseException parseException) {
            temperature = Temperature.getDefaultValue();
        }

        try {
            if (rootJsonWeatherObject.has(TAG_WIND)) {
                wind = parseWindInformation(rootJsonWeatherObject.get(TAG_WIND).getAsJsonObject());
            } else {
                wind = Wind.getDefaultValue();
            }
        } catch (JsonParseException parseException) {
            wind = Wind.getDefaultValue();
        }

        try {
            if (rootJsonWeatherObject.has(TAG_WEATHER_DESCRIPTION_ROOT)) {
                weatherDescription = parseWeatherDescription(rootJsonWeatherObject.get(TAG_WEATHER_DESCRIPTION_ROOT)
                        .getAsJsonArray().get(0).getAsJsonObject());
            } else {
                weatherDescription = WeatherDescription.getDefaultValue();
            }
        } catch (JsonParseException parseException) {
            weatherDescription = WeatherDescription.getDefaultValue();
        }

        weatherInformation.setTemperatureInformation(temperature);
        weatherInformation.setWindInformation(wind);
        weatherInformation.setWeatherDescription(weatherDescription);
        return weatherInformation;
    }

    private static Temperature parseTemperatureInformation(JsonObject temperatureRoot) throws JsonParseException {
        double minTemp, maxTemp, humidity, currentTemp;
        if (!temperatureRoot.has(TAG_TEMP_MIN) || !temperatureRoot.has(TAG_TEMP_MAX)
                || !temperatureRoot.has(TAG_TEMP_HUMIDITY)) {
            throw new JsonParseException(ERROR_TEMP_PARSE, new Throwable());
        }
        currentTemp = temperatureRoot.getAsJsonPrimitive(TAG_TEMP_CURRENT).getAsDouble();
        minTemp = temperatureRoot.getAsJsonPrimitive(TAG_TEMP_MIN).getAsDouble();
        maxTemp = temperatureRoot.getAsJsonPrimitive(TAG_TEMP_MAX).getAsDouble();
        humidity = temperatureRoot.getAsJsonPrimitive(TAG_TEMP_HUMIDITY).getAsDouble();
        return new Temperature(minTemp, maxTemp, humidity, currentTemp);
    }

    private static Wind parseWindInformation(JsonObject windRoot) throws JsonParseException {
        double windSpeed, windDirection;
        if (!windRoot.has(TAG_WIND_SPEED) || !windRoot.has(TAG_WIND_DIRECTION)) {
            throw new JsonParseException(ERROR_WIND_PARSE, new Throwable());
        }
        windSpeed = windRoot.getAsJsonPrimitive(TAG_WIND_SPEED).getAsDouble();
        windDirection = windRoot.getAsJsonPrimitive(TAG_WIND_DIRECTION).getAsDouble();
        return new Wind(windSpeed, windDirection);
    }

    private static WeatherDescription parseWeatherDescription(JsonObject weatherDescriptionRoot) throws JsonParseException {
        String icon, generalDescription;
        if (!weatherDescriptionRoot.has(TAG_WEATHER_DESCRIPTION) || !weatherDescriptionRoot.has(TAG_WEATHER_ICON)) {
            throw new JsonParseException(ERROR_DESCRIPTION_PARSE, new Throwable());
        }
        icon = weatherDescriptionRoot.get(TAG_WEATHER_ICON).getAsString();
        generalDescription = weatherDescriptionRoot.get(TAG_WEATHER_DESCRIPTION).getAsString();
        return new WeatherDescription(icon, generalDescription);
    }
}