package com.example.bookshelftop;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class BookButtonActivity extends FragmentActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_button);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookShelfActivity.EXTRA_MESSAGE);

        // Capture the layout's ViewPager and view with swipes
        viewPager = (ViewPager)findViewById(R.id.view_pager);


        PageNavigator pageNavigator = new PageNavigator(new DocGrabber().readFileFromDirectory(message));
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(),pageNavigator);
        viewPager.setAdapter(swipeAdapter);
    }



}
