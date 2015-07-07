package tk.khecnotice.khecnotice;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import tk.khecnotice.khecnotice.database.RoutineDatabase;
import tk.khecnotice.khecnotice.routinefragments.FridayFragment;
import tk.khecnotice.khecnotice.routinefragments.MondayFragment;
import tk.khecnotice.khecnotice.routinefragments.SundayFragment;
import tk.khecnotice.khecnotice.routinefragments.ThursdayFragment;
import tk.khecnotice.khecnotice.routinefragments.TuesdayFragment;
import tk.khecnotice.khecnotice.routinefragments.WednesdayFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new SundayFragment();
            case 1:
                return new MondayFragment();
            case 2:
                return new TuesdayFragment();
            case 3:
                return new WednesdayFragment();
            case 4:
                return new ThursdayFragment();
            case 5:
                return new FridayFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return RoutineDatabase.TABLE_NAMES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.sunday).toUpperCase(l);
            case 1:
                return context.getString(R.string.monday).toUpperCase(l);
            case 2:
                return context.getString(R.string.tuesday).toUpperCase(l);
            case 3:
                return context.getString(R.string.wednesday).toUpperCase(l);
            case 4:
                return context.getString(R.string.thursday).toUpperCase(l);
            case 5:
                return context.getString(R.string.friday).toUpperCase(l);
        }
        return null;
    }
}
