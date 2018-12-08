package com.example.bookshelftop;

import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BookShelfActivity extends AppCompatActivity {

    /**
     * BookShelfActivity - the class that interfaces with the bookshelf, holds and manages all the book buttons
     * generates the buttons for each book and displays them in a linear layout
     *
     * @author John Walsh
     *
     */

    public static final String EXTRA_MESSAGE = "used later";
    public BookShelf allBooks = new BookShelf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        this.displayAllBooks();
    }

    /**
     * search - called when the users presses the search button, takes the text in the search bar and turns off visibility for
     * all buttons that do not contain the search string, ignores case
     *
     * @param view - the current view of the app, use this to get the message
     *
     * @return resultCount - the number of results displayed by the search, returned when the search is over
     * returns zero if the search returned no books, returns -1 if the search was empty(""), or returns the number of results
     */
    public int search(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        this.displayAllBooks();

        int resultCount = -1;
        if(message.equals("")){
            return resultCount;
        }

        //this was where displayAllBooks was originally
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLL);
        final int childCount = ll.getChildCount();
        for(int i = 0; i < childCount; i++){
            Button b = (Button) ll.getChildAt(i);
            String buttonText = b.getText().toString();
            buttonText = buttonText.toLowerCase();
            if(!buttonText.contains(message.toLowerCase())){
                b.setVisibility(View.GONE);
            }
            else{resultCount++;}
        }

        editText.setText("");
        return resultCount;

    }

    /**
     * displayAllBooks - displays all the books whenever a user opens the bookshelf or searches for a book
     * this method always empties the linear layout then generates the buttons for each book before putting them in
     * the method also populates the bookshelf allBooks
     */
    public void displayAllBooks(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLL);
        ll.removeAllViews();
        allBooks = new BookShelf();

        String text;
        try{
            ContextWrapper c = new ContextWrapper(this);
            File bsIn = new File(c.getFilesDir(), "assets.dat");

            FileInputStream inputStream = new FileInputStream(bsIn);
            int size = inputStream.available();
            byte[] buffer = new byte[size]; //read one line
            inputStream.read(buffer);
            text = new String(buffer);
            String[] lines = text.split("\\r?\\n"); //parse each line

            for(int i = 0; i<lines.length;i++){
                final String newBookStr = lines[i];

                Button testButton = new Button(this);
                testButton.setText(newBookStr);

                testButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(BookShelfActivity.this,BookButtonActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, newBookStr);
                        startActivity(intent);
                    }
                });

                Book inBook = new Book(newBookStr, testButton);
                allBooks.insert(inBook);
            }

            ArrayList<Book> bookList = allBooks.returnBooks();
            for(int z = 0; z<bookList.size(); z++){
                //Button viewButton = bookList.get(z).getBookButton();
                //viewButton.setVisibility(View.VISIBLE);
                ll.addView(bookList.get(z).getBookButton());
            }

            inputStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
