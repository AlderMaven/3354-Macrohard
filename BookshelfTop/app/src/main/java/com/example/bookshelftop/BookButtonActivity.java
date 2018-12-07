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

    ViewPager viewPager;
    TextView text_holder;
    Button back_button;
    RandomAccessFile bReader;
    int currentLoc = 500; //position in chars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_button);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookShelfActivity.EXTRA_MESSAGE);

        //ss stuff-----------------------------------------
        text_holder = (TextView) findViewById(R.id.textHolder);  //write text to this
        //back_button.setOnClickListener(goToPrevPage); //Causes Crash if uncommented right now
        text_holder.setOnClickListener(goToNextPage);


        ContextWrapper c = new ContextWrapper(this);
        File bFile = new File(c.getFilesDir(), message);       //file with text (hopefully)

        try {
            bReader = new RandomAccessFile(bFile, "rw");
        } catch (Exception e) {
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
           try {
                if (currentLoc < bReader.length())
                    currentLoc = make_and_viewPage(bReader, currentLoc, text_holder);
            }
            catch(Exception e){

            }
        }
    };

    //for button

    public void goToPrevPage(View view){

            int prevPosition = (currentLoc - (1500));
            if (prevPosition > 0) {
                currentLoc = make_and_viewPage(bReader, prevPosition, text_holder);
            }
        }



    int make_and_viewPage(RandomAccessFile bReader, int startLoc, TextView text_holder)  //makes the pages
    {
        String pageText = "";
        byte[] temp = new byte[2];
        int position;
        int pos = 0;
        String tempString;

        try {
            bReader.seek((long) (startLoc));
            for (pos = startLoc*2; pos < startLoc*2 + 1500 && (pos/2) < bReader.length(); pos += 2)  //reads X characters
            {
                temp[0] = bReader.readByte();
                temp[1] = bReader.readByte();

                tempString = new String(temp);

                pageText = pageText + tempString;


                //stores latest position to be returned
            }
        } catch (Exception e) {
            text_holder.setText("IO exception");
        }
        position = pos/2;
        text_holder.setText(pageText);
        return position;
    }

}
