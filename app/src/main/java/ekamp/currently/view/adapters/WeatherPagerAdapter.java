package ekamp.currently.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ekamp.currently.model.WeatherInformation;
import ekamp.currently.view.fragments.WeatherFragment;

/**
 * Adapter used to populate the forecasted weather ViewPager
 *
 * @author Erik Kamp
 * @since 9/22/15.
 */
public class WeatherPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<WeatherInformation> weatherInformationList;

    public WeatherPagerAdapter(FragmentManager fm, ArrayList<WeatherInformation> weatherInformationList) {
        super(fm);
        this.weatherInformationList = weatherInformationList;
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherFragment.newInstance(weatherInformationList.get(position));
    }

    @Override
    public int getCount() {
        return weatherInformationList.size();
    }
}
