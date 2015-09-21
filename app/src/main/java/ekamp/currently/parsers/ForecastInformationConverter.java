package ekamp.currently.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ekamp.currently.model.ForecastInformation;

/**
 * Converts from REST response to {@link ForecastInformation}
 *
 * @author Erik Kamp
 * @since 9/20/15.
 */
public class ForecastInformationConverter implements JsonDeserializer<ForecastInformation> {
    @Override
    public ForecastInformation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
