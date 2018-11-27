package com.example.docgrabber;

//Boilerplate
import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.String;
import java.io.File;
import com.example.docgrabber.TagFile;
import com.example.docgrabber.NoteFile;

//Be sure to double check the permissions inside of the VM itself

public class DocGrabber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Unchanged
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_grabber);
    }

    //Is called when focus leaves textbox
    public void onEnter(View view) {
        Intent intent = new Intent(this, DocGrabber.class); //Probably removable

        //Grab file name from textline
        EditText editText = (EditText) findViewById(R.id.FileNameBox);
        String bookFileName = editText.getText().toString();

        //Call function and if add was successful will return true
        boolean successful = getDocumentFromDownload(bookFileName, editText);

        //Just for returning messages
        String onSuccess = "File Successfully Added";
        String onFail = "Could not import file";

        //Print whether add was successful or not
        if(successful){
            editText.setText(onSuccess);
        }
        else{
            editText.setText(onFail);
        }
    }




    boolean getDocumentFromDownload(String bookFileName, EditText editText) {
        //Get filepath to downloads
        File path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try {
            ContextWrapper c = new ContextWrapper(this);
            File bookFile = new File(path, bookFileName); //Make file from ,txt/.pdf in downloads
            //Get File from download folder using path above

            //Call the writing function
            return writeFileToAppDirectory(bookFile, bookFileName, c, editText);
        }
        catch (Exception e){

            editText.setText(e.getMessage());
            return false;
        }
    }

    boolean writeFileToAppDirectory(File bookFile, String bookFileName, Context c, EditText editText){
        try {
            File localBook = new File(c.getFilesDir(), bookFileName); //For creating bookFile in local directory
            NoteFile notes =  new NoteFile(bookFileName+"notes"); //This will create a file for notes in the local directory if it does not exist
            TagFile tags =  new TagFile(bookFileName+"tags"); //This will create a file for tags in the local directory if it does not exist

            //Reader and Writer to write book into directory
            FileReader oldBookReadIn = new FileReader(bookFile);
            FileWriter localBookWrite = new FileWriter(localBook);

            char[] buffer = new char[1024];
            int readRequestInt = 0;

            //Runtime permission check as required by API level
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        readRequestInt);
            }
            else {
                //read from file in download folder and write into file in local directory
                while (oldBookReadIn.read(buffer) != -1) {
                    localBookWrite.write(buffer);
                }
                localBookWrite.write(buffer);
            }

        }
        catch(Exception e){

            editText.setText(e.getMessage());
            return false;
        }
        return true;
    }


    //Returns a file with the same name as the String from the local directory
    File readFileFromDirectory(String name) {
        ContextWrapper c = new ContextWrapper(this);
        try {
            File localFile = new File(c.getFilesDir()+name);
            return localFile;
        }
        catch (Exception e){
            return null;
        }
    }
}