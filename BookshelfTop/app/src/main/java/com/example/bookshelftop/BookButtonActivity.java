
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

    RandomAccessFile bReader;
    int currentLoc = 0; //position of cursor in chars

    /**
     *Creates the textview object, randomaccessfile, and gets the desired file from the internal directory
     *
     * Also calls the make and view page method
     *
     * @author Brandon Barnes and Saurabh Shah
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_button);

        // Get the Intent that started this activity and extract the string for the filename
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookShelfActivity.EXTRA_MESSAGE);

        //ss stuff-----------------------------------------
        text_holder = (TextView) findViewById(R.id.textHolder);  //write text to this

        text_holder.setOnClickListener(goToNextPage);


        ContextWrapper c = new ContextWrapper(this);
        File bFile = new File(c.getFilesDir(), message);       //file with text

        try {
            bReader = new RandomAccessFile(bFile, "rw"); //This is used to read from the file, RandomAccessFile is used since it is seekable
        } catch (Exception e) {
            text_holder.setText("File not found");
        }

        currentLoc = make_and_viewPage(bReader, currentLoc, text_holder); //Make first page


        /* //dummied out
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


    private View.OnClickListener goToNextPage = new View.OnClickListener() {
        /**
         * for advancing in book
         * @param v View object
         */
        public void onClick(View v) {
           try {
                if (currentLoc < bReader.length())
                    currentLoc = make_and_viewPage(bReader, currentLoc, text_holder);
            }
            catch(Exception e){

            }
        }
    };

    /**
     * for back button
     * @param view View object
     */
    public void goToPrevPage(View view){

            int prevPosition = (currentLoc - (1500)); //Go back 2*length of page
            if (prevPosition > 0) {
                currentLoc = make_and_viewPage(bReader, prevPosition, text_holder);
            }
        }


    /**
     * Creates pages and sends them to the textview object for displaying on the screen
     * Prints a set number of characters at a time
     *
     * @param bReader randomaccessfile used to read text from file
     * @param startLoc Tells the file reader where to start reading from the file
     * @param text_holder textView object that displays text to the user
     * @return returns the current position of the cursor
     */
    int make_and_viewPage(RandomAccessFile bReader, int startLoc, TextView text_holder)  //makes the pages
    {
        String pageText = "";
        byte[] temp = new byte[2]; //Each character is 2 bytes so an array is used
        int position; //Our return value
        int pos = 0;
        String tempString;

        try {
            bReader.seek((long) (startLoc));
            for (pos = startLoc*2; pos < startLoc*2 + 1500 && (pos/2) < bReader.length(); pos += 2)  //reads 750 characters or until eof
            {
                //Read the bytes from the file
                temp[0] = bReader.readByte();
                temp[1] = bReader.readByte();

                tempString = new String(temp); //create string out of bytes

                pageText = pageText + tempString; //add string composed of 1 char to the string to be printed to the screen



            }
        } catch (Exception e) {
            text_holder.setText("IO exception");
        }
        position = pos/2;
        //Display page
        text_holder.setText(pageText);
        return position;
    }

}
