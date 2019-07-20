package kennguch.github.instagram.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShareAdapter extends FragmentPagerAdapter {


    private final List<Fragment> mFragment = new ArrayList<>();

    private final List<String> mTitles = new ArrayList<>();


    public ShareAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String title) {
        mFragment.add(fragment);
        mTitles.add(title);
    }


}
