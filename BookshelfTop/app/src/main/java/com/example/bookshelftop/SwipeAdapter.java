package com.example.bookshelftop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    PageNavigator pageNavigator;
    private int count = 0;

    public SwipeAdapter(FragmentManager fm, PageNavigator pageNavigator){
        super(fm);
        this.pageNavigator = pageNavigator;
    }
    @Override
    public Fragment getItem(int i){
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        pageNavigator.nextPage();
        bundle.putString("text", pageNavigator.displayCurrentPage());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount(){
        if(!pageNavigator.isLastPage()) {
            return count + 1;
        }else{
            return count;
        }
    }
}
