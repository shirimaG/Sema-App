package com.example.goodluck.sema;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by GOODLUCK on 4/4/2018.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    public CategoryAdapter(Context context,FragmentManager fm) {
        super(fm);
        //Actually this context used to refer the tabs
        mContext=context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new NumbersFragment();
        } else if (position==1){
            return new FamilyFragment();
        } else if (position==2){
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }


    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        // we returned four pages as needed
        return 4;
    }

    /***
            * now we create this override method so can help us to create tabs
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return mContext.getString(R.string.numbers);
        } else  if (position==1){
            return  mContext.getString(R.string.family);
        } else if (position==2){
            return mContext.getString(R.string.colors);
        } else {
            return mContext.getString(R.string.phrases);
        }
    }
}
