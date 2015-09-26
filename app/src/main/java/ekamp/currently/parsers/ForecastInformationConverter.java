package ekamp.currently.parsers;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ekamp.currently.model.ForecastInformation;
import ekamp.currently.model.WeatherInformation;

/**
 * Converts from REST response to {@link ForecastInformation}
 *
 * @author Erik Kamp
 * @since 9/20/15
 */
public class ForecastInformationConverter implements JsonDeserializer<ForecastInformation> {
    private static String TAG_FORCAST_LIST = "list", TAG_WEATHER_FORECAST_TIME = "time", TAG_WEATHER_FORECAST_START = "dt_txt";

    @Override
    public ForecastInformation deserialize(JsonElement rootJsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<WeatherInformation> weatherInformationList;

        JsonObject jsonObject = rootJsonElement.getAsJsonObject();
        if (!jsonObject.has(TAG_FORCAST_LIST)) {
            throw new JsonParseException("Cannot find forecasted list");
        }
        JsonArray forcastJsonArray = jsonObject.getAsJsonArray(TAG_FORCAST_LIST);
        WeatherInformation forcastedWeatherInformation;
        JsonObject weatherJsonObject;
        DateTime forecastedTimeStart;
        weatherInformationList = new ArrayList<>(forcastJsonArray.size());

        for (JsonElement weatherJsonElement : forcastJsonArray) {
            try {
                weatherJsonObject = weatherJsonElement.getAsJsonObject();
                forecastedTimeStart = parseWeatherStartTime(weatherJsonObject);
                boolean weatherObjectIsOutDated = forecastedTimeStart.isBeforeNow();

                if (!weatherObjectIsOutDated) {
                    forcastedWeatherInformation = CurrentWeatherInformationConverter.parseWeatherInformation(weatherJsonObject);
                    forcastedWeatherInformation.setTimeWindow(forecastedTimeStart);
                    weatherInformationList.add(forcastedWeatherInformation);
                }
            } catch (JsonParseException jsonException) {
                continue;
            }
        }
        return new ForecastInformation(weatherInformationList);
    }

    private DateTime parseWeatherStartTime(JsonObject weatherJsonRoot) {
        String startWeatherTimeString = weatherJsonRoot.get(TAG_WEATHER_FORECAST_START).getAsString();
        DateTime parsedDateTime = null;
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            parsedDateTime = formatter.parseDateTime(startWeatherTimeString);
        } catch (Exception e) {
            Log.e(getClass().getName(), "");
        }
        return parsedDateTime;
    }
}
