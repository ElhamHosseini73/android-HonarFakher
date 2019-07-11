package ir.rayapars.honarfakher.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.fragments.TabFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    List<Sections> sections;

    Context context;

    public  MyPagerAdapter(FragmentManager fm, List<Sections> sections, Context context) {

        super(fm);
        this.sections = sections;
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {

        TabFragment tabFragment=new TabFragment();
        tabFragment.parentID = sections.get(position).id;
        return tabFragment;

    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return sections.get(position).title;

    }
}
