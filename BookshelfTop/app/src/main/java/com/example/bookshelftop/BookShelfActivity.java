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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BookShelfActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public BookShelf allBooks = new BookShelf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        this.displayAllBooks();     //Need to call display all books each time the activity is opened to generate buttons in the linear layout
    }

    /** Called when the user taps the Send button, will be used for searching for books
     *  @editText is the search bar
     *  @message is the text we retreive from the search bar
     *
     * */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);     //Gets the text in the search bar
        String message = editText.getText().toString();                 //This is the string of text from the search bar
        if(message.equals("")){                                         // If the message is empty nothing happens, all books are displayed as usual
            this.displayAllBooks();
            return;
        }

        //this was where displayAllBooks was originally
        this.displayAllBooks();//replace with search function

        editText.setText("");

    }

    public void displayAllBooks(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLL);
        ll.removeAllViews();

        String text;
        try{
            ContextWrapper c = new ContextWrapper(this);
            File bsIn = new File(c.getFilesDir(), "assets.dat"); //assets.dat holds a list of file names that we are using for our books
            /*bsIn.delete();
            bsIn = new File(c.getFilesDir(), "assets.dat");

            FileOutputStream fileOutputStream = new FileOutputStream(bsIn,true);
            fileOutputStream.write(("Well what is it\n").getBytes());
            fileOutputStream.write(("Button 2\n").getBytes());
            fileOutputStream.write(("Button 3\n").getBytes());*/

            FileInputStream inputStream = new FileInputStream(bsIn); //getAssets().open("readInBooks.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size]; //read one line
            inputStream.read(buffer);

            text = new String(buffer);
            String[] lines = text.split("\\r?\\n");

            for(int i = 0; i<lines.length;i++){
                final String newBookStr = lines[i];
                Book inBook = new Book(newBookStr);
                allBooks.insert(inBook);

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
