package com.example.jack.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //public LinearLayout backUp = (LinearLayout)findViewById(R.id.buttonLL);
    public BookShelf allBooks = new BookShelf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.displayAllBooks();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //Book inBook = new Book(message);
        //intent.putExtra(EXTRA_MESSAGE, "HOPE THIS WORKS");
        //startActivity(intent);

        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        if(message.equals("")){return;}

        Book inBook = new Book(message);
        allBooks.insert(inBook);

        //this was where displayAllBooks was originally
        this.displayAllBooks();

        editText.setText("");

    }

    public void displayAllBooks(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLL);
        ll.removeAllViews();

        //Button testButton = new Button(this);
        //testButton.setText("First Step");

        //ll.addView(testButton);

        /*//original implementation of button generation
        final ArrayList<Book> contents = allBooks.returnBooks();
        for(int i = 0; i < contents.size(); i++){
            Button testButton = new Button(this);
            testButton.setText(contents.get(i).getTitle());

            testButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,DisplayMessageActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "MAIN ACTIVITY LINE 73");
                    startActivity(intent);
                }
            });

            ll.addView(testButton);
        }*/


        String text;
        try{
            InputStream inputStream = getAssets().open("readInBooks.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size]; //read one line
            inputStream.read(buffer);
            text = new String(buffer);
            String[] lines = text.split("\\r?\\n");

            for(int i = 0; i<lines.length;i++){
                Book inBook = new Book(lines[i]);

                Button testButton = new Button(this);
                testButton.setText(lines[i]);

                testButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,DisplayMessageActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, "DISPLAY BOOK");
                        startActivity(intent);
                    }
                });

                ll.addView(testButton);
            }

            inputStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}