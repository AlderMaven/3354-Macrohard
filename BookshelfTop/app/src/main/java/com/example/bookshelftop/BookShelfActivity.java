package com.example.bookshelftop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

public class BookShelfActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public BookShelf allBooks = new BookShelf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        this.displayAllBooks();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
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

        String text;
        try{
            InputStream inputStream = getAssets().open("readInBooks.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size]; //read one line
            inputStream.read(buffer);
            text = new String(buffer);
            String[] lines = text.split("\\r?\\n");

            for(int i = 0; i<lines.length;i++){
                final String newBookStr = lines[i];
                Book inBook = new Book(newBookStr);

                Button testButton = new Button(this);
                testButton.setText(newBookStr);

                testButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(BookShelfActivity.this,BookButtonActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, newBookStr);
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
