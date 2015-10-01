package ekamp.currently.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ekamp.currently.R;
import ekamp.currently.model.Temperature;
import ekamp.currently.model.WeatherDescription;
import ekamp.currently.model.WeatherInformation;
import ekamp.currently.model.Wind;
import ekamp.currently.model.WeatherLocationController;
import ekamp.currently.utils.FormatUtils;

/**
 * {@link android.app.Fragment} used to show a forecasted {@link WeatherInformation}.
 *
 * @author Erik Kamp
 * @since 9/22/15.
 */
public class WeatherFragment extends Fragment {

    @Bind(R.id.current_location)
    TextView currnetLocationView;

    @Bind(R.id.current_temperature)
    TextView currentTemperatureView;

    @Bind(R.id.weather_description)
    TextView weatherDescriptionView;

    @Bind(R.id.max_temperature)
    TextView maxTemperatureView;

    @Bind(R.id.min_temperature)
    TextView minTemperatureView;

    @Bind(R.id.humidity)
    TextView humidityView;

    @Bind(R.id.wind_speed)
    TextView windSpeedView;

    @Bind(R.id.weather_icon)
    ImageView weatherIconView;

    private WeatherInformation weatherInformation;

    public static WeatherFragment newInstance(WeatherInformation weatherInformation) {
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setWeatherInformation(weatherInformation);
        return weatherFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFragmentViews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_forecasted_weather_information, container, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    private void setupFragmentViews() {
        Temperature temperatureInformation = weatherInformation.getTemperatureInformation();
        Wind windInformation = weatherInformation.getWindInformation();
        WeatherDescription weatherDescription = weatherInformation.getWeatherDescription();
        String fullAddress = WeatherLocationController.getInstance().getLastCollectedAddress(),
                currentTown = FormatUtils.extractTownFromAddress(fullAddress);

        currentTemperatureView.setText(temperatureInformation.getCurrentTempForDisplay());
        currnetLocationView.setText(currentTown);
        weatherDescriptionView.setText(weatherDescription.getGeneralDescription());
        maxTemperatureView.setText(temperatureInformation.getMaxTempForDisplay());
        minTemperatureView.setText(temperatureInformation.getMinTempForDisplay());
        humidityView.setText(temperatureInformation.getHumidityForDisplay());
        windSpeedView.setText(windInformation.getSpeedForDisplay());

        Picasso.with(getContext()).
                load(weatherDescription.getIconUrlPath()).
                placeholder(getResources().getDrawable(R.drawable.placeholder_weather)).
                into(weatherIconView);
    }

    private void setWeatherInformation(WeatherInformation weatherInformation) {
        this.weatherInformation = weatherInformation;
    }
}