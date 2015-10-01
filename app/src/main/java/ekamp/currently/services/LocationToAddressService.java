package ekamp.currently.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ekamp.currently.model.Constants;

/**
 * Intent service used to parse the location into a readable address.
 *
 * @author Erik Kamp
 * @since 9/26/15.
 */
public class LocationToAddressService extends IntentService {

    private List<Address> addresses = null;

    public LocationToAddressService() {
        super("LocationToAddressService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException io) {

        } catch (IllegalArgumentException illegalArg) {

        }
        broadcastResults(Constants.RESULT_SUCCESS, parseAddress());
    }

    private String parseAddress() {
        Address address = addresses.get(0);
        ArrayList<String> addressFragments = new ArrayList<>();

        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            addressFragments.add(address.getAddressLine(i));
        }
        return TextUtils.join(System.getProperty("line.separator"),
                addressFragments);
    }

    private void broadcastResults(int resultCode, String message) {
        Intent broadcastAddressIntent = new Intent(Constants.LOCATION_TO_ADDRESS_FILTER_ACTION)
                .putExtra(Constants.LOCATION_DATA_EXTRA, message)
                .putExtra(Constants.RESULT_DATA_KEY, resultCode);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastAddressIntent);
    }
}