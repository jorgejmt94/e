package domel.ecampus.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import domel.ecampus.Fragment.AddSubjectFirstStepFragment;
import domel.ecampus.Fragment.AddSubjectSecondStepFragment;
import domel.ecampus.Fragment.AddSubjectThirdStepFragment;

public class AddStudentFragmentAdapter extends SmartFragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;

    private AddSubjectFirstStepFragment firstStepFragment;
    private AddSubjectSecondStepFragment secondStepFragment;
    private AddSubjectThirdStepFragment thirdStepFragment;

    public AddStudentFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        firstStepFragment = new AddSubjectFirstStepFragment();
        secondStepFragment = new AddSubjectSecondStepFragment();
        thirdStepFragment = new AddSubjectThirdStepFragment();
    }


    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return firstStepFragment;
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return secondStepFragment;
            case 2: // Fragment # 1 - This will show SecondFragment
                return thirdStepFragment;
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Paso " + position;
    }
}
