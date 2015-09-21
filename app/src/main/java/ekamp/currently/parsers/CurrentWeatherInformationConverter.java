package ekamp.currently.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ekamp.currently.model.Temperature;
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
            TAG_WIND_DIRECTION = "deg";

    public static final String ERROR_TEMP_PARSE = "Temperature parsing error related to missing field",
            ERROR_WIND_PARSE = "Wind parsing error related to missing field";

    @Override
    public WeatherInformation deserialize(JsonElement rootJsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = rootJsonElement.getAsJsonObject();
        return parseWeatherInformation(jsonObject);
    }

    /**
     * Parses a weather {@link JsonObject} into its respective POJO {@link WeatherInformation}
     *
     * @param rootJsonWeatherObject object to be parsed
     * @throws JsonParseException this is thrown when the Json weather information representation is missing a field.
     * @return parsed {@link WeatherInformation}
     */
    public static WeatherInformation parseWeatherInformation(JsonObject rootJsonWeatherObject) throws JsonParseException {
        WeatherInformation weatherInformation = new WeatherInformation();
        Temperature temperature;
        Wind wind;
        if (rootJsonWeatherObject.has(TAG_MAIN_WEATHER)) {
            temperature = parseTemperatureInformation(rootJsonWeatherObject.get(TAG_MAIN_WEATHER).getAsJsonObject());
        } else {
            temperature = Temperature.getDefaultValue();
        }

        if (rootJsonWeatherObject.has(TAG_WIND)) {
            wind = parseWindInformation(rootJsonWeatherObject.get(TAG_WIND).getAsJsonObject());
        } else {
            wind = Wind.getDefaultValue();
        }

        weatherInformation.setTemperatureInformation(temperature);
        weatherInformation.setWindInformation(wind);
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
}
