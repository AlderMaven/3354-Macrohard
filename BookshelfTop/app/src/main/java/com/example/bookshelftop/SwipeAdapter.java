package com.example.bookshelftop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Connects with the BookButtonActivity and handles the actions following a swipe
 *
 *@author Carl Schneider
 *
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {
    PageNavigator pageNavigator;
    private int count = 0;

    public SwipeAdapter(FragmentManager fm, PageNavigator pageNavigator){
        super(fm);
        this.pageNavigator = pageNavigator;
    }

    /**
     *
     * @param i     numerical value of the current fragment
     * @return fragment that is going to be displayed
     */
    @Override
    public Fragment getItem(int i){
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", pageNavigator.displayCurrentPage()); // Gets the text of the current page
        pageNavigator.nextPage();
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
