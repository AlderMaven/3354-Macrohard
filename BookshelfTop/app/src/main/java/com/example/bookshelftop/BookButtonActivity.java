package com.example.bookshelftop;

import android.content.ContextWrapper;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.io.File;
import java.io.FileReader;

public class BookButtonActivity extends FragmentActivity {
    ViewPager viewPager;
    TextView text_holder;
    FileReader bReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_button);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookShelfActivity.EXTRA_MESSAGE);

        //ss stuff------------------------------------------

        text_holder = (TextView)findViewById(R.id.textHolder);  //write text to this
        ContextWrapper c = new ContextWrapper(this);
        File bFile = new File(c.getFilesDir(), message);       //file with text (hopefully)

        try {
            bReader = new FileReader(bFile);
        }
        catch(Exception e)
        {
            text_holder.setText("File not found");
        }

        make_and_viewPage(bReader, 0, text_holder);


        /*
        // Capture the layout's ViewPager and view with swipes
        try {
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            ContextWrapper c = new ContextWrapper(this);

            PageNavigator pageNavigator = new PageNavigator(new File(c.getFilesDir(), message));
            SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), pageNavigator);
            viewPager.setAdapter(swipeAdapter);
        }
        catch(Exception e) {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(e.toString());

        }
        */
    }

    void make_and_viewPage(FileReader bReader, long startLoc, TextView text_holder)
    {
        String pageText = "";
        int temp = -1;
        try {
            for (int pos = 0; pos < 100; pos++)  //reads 100 characters
            {
                while ((temp = bReader.read()) != -1) {  //temp is int, need to cast to a char to use
                    pageText = pageText + (char)temp;
                }
            }
        }
        catch(Exception e)
        {
            text_holder.setText("IO exception");
        }

        text_holder.setText(pageText);
    }




}
