package ekamp.currently.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
    private static String TAG_FORCAST_LIST = "list";

    @Override
    public ForecastInformation deserialize(JsonElement rootJsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<WeatherInformation> weatherInformationList;

        JsonObject jsonObject = rootJsonElement.getAsJsonObject();
        if (!jsonObject.has(TAG_FORCAST_LIST)) {
            throw new JsonParseException("Cannot find forcasted list");
        }
        JsonArray forcastJsonArray = jsonObject.getAsJsonArray(TAG_FORCAST_LIST);
        weatherInformationList = new ArrayList<>(forcastJsonArray.size());
        WeatherInformation forcastedWeatherInformation;

        for (JsonElement weatherJsonObject : forcastJsonArray) {
            try {
                forcastedWeatherInformation = CurrentWeatherInformationConverter.parseWeatherInformation(weatherJsonObject.getAsJsonObject());
                weatherInformationList.add(forcastedWeatherInformation);
            } catch (JsonParseException jsonException) {
                continue;
            }
        }
        return new ForecastInformation(weatherInformationList);
    }
}
