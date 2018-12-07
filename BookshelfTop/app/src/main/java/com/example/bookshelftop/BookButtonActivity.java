package com.example.bookshelftop;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class BookButtonActivity extends FragmentActivity {
	int charsperpage = 1500;
    ViewPager viewPager;
    TextView text_holder;
    Button back_button;
    RandomAccessFile bReader;
    int currentLoc = -1; //position in chars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_button);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookShelfActivity.EXTRA_MESSAGE);

        //ss stuff-----------------------------------------
        text_holder = (TextView)findViewById(R.id.textHolder);  //write text to this
        text_holder.setOnClickListener(goToNextPage);
        back_button.setOnClickListener(goToPrevPage);

        ContextWrapper c = new ContextWrapper(this);
        File bFile = new File(c.getFilesDir(), message);       //file with text (hopefully)

        try {
            bReader = new RandomAccessFile(bFile, "rw");
        }
        catch(Exception e)
        {
            text_holder.setText("File not found");
        }

        currentLoc = make_and_viewPage(bReader, currentLoc, text_holder);


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

    //for textview
    private View.OnClickListener goToNextPage = new View.OnClickListener() {
        public void onClick(View v) {
            currentLoc = make_and_viewPage(bReader, currentLoc, text_holder);
        }
    };

    //for button
    private View.OnClickListener goToPrevPage = new View.OnClickListener() {
        public void onClick(View v) {
            int prevPosition = currentLoc - (2*charsperpage);
            currentLoc = make_and_viewPage(bReader, prevPosition, text_holder);
        }
    };

    int make_and_viewPage(RandomAccessFile bReader, int startLoc, TextView text_holder)  //makes the pages
    {
        String pageText = "";
        int temp = -1;
        int position = -1;

        try {
            bReader.seek((long)(2*startLoc));
            for (int pos = startLoc; pos < (startLoc + charsperpage); pos++)  //reads 1500 characters
            {
                if ((temp = bReader.readChar()) != -1) {  //temp is int, need to cast to a char to use

                    pageText = pageText + (char)temp;
                }
                else      //preventing infinite loop
                {
                    break;
                }
                position = pos;    //stores latest position to be returned
            }
        }
        catch(Exception e)
        {
            text_holder.setText("IO exception");
        }

        text_holder.setText(pageText);
        return position;
    }




}
