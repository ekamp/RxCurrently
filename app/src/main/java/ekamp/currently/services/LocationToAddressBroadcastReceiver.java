package ekamp.currently.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ekamp.currently.model.Constants;
import ekamp.currently.view.activities.BaseCallbackActivity;

/**
 * Receiver used to notify the {@link BaseCallbackActivity} of the {@link LocationToAddressService}
 * completion.
 *
 * @author Erik Kamp
 * @since 9/29/15.
 */
public class LocationToAddressBroadcastReceiver extends BroadcastReceiver {
    private BaseCallbackActivity baseCallbackActivity;

    public LocationToAddressBroadcastReceiver(BaseCallbackActivity baseCallbackActivity) {
        this.baseCallbackActivity = baseCallbackActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int resultCode = intent.getIntExtra(Constants.RESULT_DATA_KEY, 1);
        if (resultCode == 0) {
            String result = intent.getStringExtra(Constants.LOCATION_DATA_EXTRA);
            baseCallbackActivity.onLocationResolved(result);
        } else {
            baseCallbackActivity.onLocationResolutionFailure();
        }
    }
}
