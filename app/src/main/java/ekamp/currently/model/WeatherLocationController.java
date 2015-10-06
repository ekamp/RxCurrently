package ekamp.currently.model;

import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.LocalBroadcastManager;

import ekamp.currently.services.LocationToAddressBroadcastReceiver;
import ekamp.currently.services.LocationToAddressService;
import ekamp.currently.view.activities.BaseCallbackActivity;

/**
 * Controller used to collect the current location of the user, in order to properly display the
 * weather pertaining to the user's location.
 */
public class WeatherLocationController {

    private static WeatherLocationController weatherLocationController;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private BaseCallbackActivity weatherParentActivity;
    private String lastCollectedAddress;
    private static final int LOCATION_REFRESH_TIME = 600000, LOCATION_REFRESH_DISTANCE = 8000;

    public static WeatherLocationController init(LocationListener locationListener, BaseCallbackActivity weatherParentActivity) {
        weatherLocationController = new WeatherLocationController();
        weatherLocationController.locationListener = locationListener;
        weatherLocationController.weatherParentActivity = weatherParentActivity;
        return weatherLocationController;
    }

    public static WeatherLocationController getInstance() {
        return weatherLocationController;
    }

    /**
     * Binds the current instance {@link LocationManager}, and starts the {@link LocationToAddressService}
     * in order to collect the user's location are parse it into a human readable address.
     */
    public void requestCurrentAddress() {
        setupLocationServices();
        startLocationToAddressResolutionService();
    }

    private void setupLocationServices() {
        locationManager = (LocationManager) weatherParentActivity.getSystemService(weatherParentActivity.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);
    }

    /**
     * Starts the {@link LocationToAddressService} in order to turn a standard {@link Location} into
     * a human readable address {@link String}.
     */
    public void startLocationToAddressResolutionService() {
        IntentFilter locationToAddressIntentFilter = new IntentFilter(Constants.LOCATION_TO_ADDRESS_FILTER_ACTION);
        LocationToAddressBroadcastReceiver locationToAddressBroadcastReceiver = new LocationToAddressBroadcastReceiver(weatherParentActivity);
        LocalBroadcastManager.getInstance(weatherParentActivity).
                registerReceiver(locationToAddressBroadcastReceiver, locationToAddressIntentFilter);
        Intent locationIntent = new Intent(weatherParentActivity, LocationToAddressService.class);
        Location location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
        //If we cannot get the location from our GPS, then attempt to get the location via the cell network
        if (location == null) {
            location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
        }
        locationIntent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        weatherParentActivity.startService(locationIntent);
    }

    /**
     * Sets the last parsed / collected address.
     *
     * @param lastAddress last address to be collected and parsed
     */
    public void storeLastCollectedAddress(String lastAddress) {
        lastCollectedAddress = lastAddress;
    }

    /**
     * Retrieves the last parsed / collected address.
     *
     * @return last address to be collected and parsed
     */
    public String getLastCollectedAddress() {
        return lastCollectedAddress;
    }
}
