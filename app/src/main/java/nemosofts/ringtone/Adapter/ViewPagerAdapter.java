package nemosofts.ringtone.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nemosofts.ringtone.Fragment.CategoriesFragment;
import nemosofts.ringtone.Fragment.HomeFragment;
import nemosofts.ringtone.Fragment.MostViewFragment;

/**
 * Created by thivakaran
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                MostViewFragment tab2 = new MostViewFragment();
                return tab2;
            case 2:
                CategoriesFragment tab3 = new CategoriesFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}