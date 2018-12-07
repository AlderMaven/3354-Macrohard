package com.example.bookshelftop;

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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.String;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import com.example.bookshelftop.TagFile;
import com.example.bookshelftop.NoteFile;

//Be sure to double check the permissions inside of the VM itself

public class DocGrabber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Unchanged
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_grabber);
    }

    //Is called when focus leaves textbox
    /**
     * 1st call that resolves for importing a book
     * which will attempt to make a copy of a file from the downloads directory and place it into the app's local directory
     * If successful it will also add that book's file name to the assets.dat file in the local directory
     *
     *
     * @param view the view of the current text model
     *
     *
     * @author Brandon Barnes
     *
     *
     */
    public void onEnter(View view) {
        Intent intent = new Intent(this, DocGrabber.class); //Probably removable

        //Grab file name from textline
        EditText editText = (EditText) findViewById(R.id.FileNameBox);
        String bookFileName = (editText.getText()).toString();


        boolean notImported = false;
        boolean notInvalid = true;

        //Just for returning messages
        String onSuccess = "File imported successfully";
        String onFail = "Could not import file";
        String assetsFileName = "assets.dat";
        String appendName = bookFileName+ "\n";
        String assetError = "Error in adding file name to assets.dat";
        String onAlreadyImported = "Book was already imported";
        String onInvalid = "Not allowed to import file with that name";

        ContextWrapper c = new ContextWrapper(this);
        File localBook = new File(c.getFilesDir(), assetsFileName);
        try {
            localBook.createNewFile();
        }
        catch (Exception e){

        }


        File bsIn = new File(c.getFilesDir(), "assets.dat");
        try {

            FileInputStream inputStream = new FileInputStream(bsIn); //getAssets().open("readInBooks.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size]; //read one line
            inputStream.read(buffer);
            String text = new String(buffer);
            String[] lines = text.split("\\r?\\n");
            notImported = true;

            for(int i =0; i<lines.length;i++) {
                String bookInList = lines[i];
                if(bookFileName.equals(bookInList)) {
                    notImported = false;
                    editText.setText(onAlreadyImported);
                    break;
                }
                else {

                }

            }

        }
        catch(Exception e) {
            //This will trigger if reading from assets.dat occurs
            notImported = false; //Treating cases where book may or not be imported already as if it is already imported
        }

        //Check to ensure user cannot overwrite assets.dat
        if(bookFileName.equals(assetsFileName)) {
          //  notInvalid = false;
            editText.setText(onInvalid);
        }
        else {
            notInvalid = true; //bookFileName is not assets,dat
        }
        if(notInvalid && notImported) {
            //Call function and if add was successful will return true
            boolean successful = getDocumentFromDownload(bookFileName, editText);


            //Print whether add was successful or not
            if (successful) { //if successful add book's file name to assets.dat
                File assetsFile = new File(c.getFilesDir(), assetsFileName);

                try { //write bookFileName to assets.dat
                    FileOutputStream assetsWriter = new FileOutputStream(assetsFile, true);
                    byte[] tempArray = appendName.getBytes();

                    assetsWriter.write(tempArray);


                    editText.setText(onSuccess);
                } catch (Exception e) {
                    editText.setText(assetError); //This message will trigger if writing to the assets.dat file fails
                }


            } else {
                editText.setText(onFail); //If book was not found in downloads or any other false return for the import occurs, this will trigger
            }
        }
        else{
            //Messages are already placed above, this else is not necessary for functionality
        }
    }

    /**
     * 2nd function call for importing a book
     * Creates a File object based on the provided bookFileName from a file in the downloads folder for use in the next function call
     *
     *
     *
     * @param bookFileName the string representing the file name of the desired book
     * @param editText the EditText from the associated xml based display
     *
     *
     * @author Brandon Barnes
     *
     * @return function returns a boolean which will be true if the book was successfully copied and false otherwise
     */
    boolean getDocumentFromDownload(String bookFileName, EditText editText) {
        //Get filepath to downloads
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try {
            ContextWrapper c = new ContextWrapper(getApplicationContext()); //Needed for writeFileToAppDirectory
            File bookFile = new File(path, bookFileName); //Make file from ,txt/.pdf in downloads
            //Get File from download folder using path above

            //Call the writing function
            return writeFileToAppDirectory(bookFile, bookFileName, c, editText);
        } catch (Exception e) { //Will File object cannot be created

            editText.setText(e.getMessage()); //Not currently visible to user, must dummy out fail message in onEnter to view
            return false;
        }
    }






    /**
     * 3rd function call for importing a book
     * Copies the book associated with bookFile to the local directory
     *
     *
     *
     * @param bookFileName the string representing the file name of the desired book
     * @param editText the EditText from the associated xml based display
     * @param bookFile A File object that is associated with the desired file in the downloads folder
     * @param c The context for the current activity
     *
     *
     * @author Brandon Barnes
     *
     * @return function returns a boolean which will be true if the book was successfully copied and false otherwise
     */
    boolean writeFileToAppDirectory(File bookFile, String bookFileName, Context c, EditText editText) {
        try {
            File localBook = new File(c.getFilesDir(), bookFileName); //For creating bookFile in local directory

            //Reader and Writer to write book into directory

            int buffer = 0;
            int readRequestInt = 0;

            //Runtime permission check as required by API level
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        readRequestInt);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        readRequestInt);
            }
            else {
                //Copy the book to the local directory
                FileInputStream inStream = new FileInputStream(bookFile);
                FileOutputStream outStream = new FileOutputStream(localBook);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();
                //Create the tag and note files
                NoteFile notes = new NoteFile(localBook); //This will create a file for notes in the local directory if it does not exist
                TagFile tags = new TagFile(localBook); //This will create a file for tags in the local directory if it does not exist

            }

        }

        catch (Exception e) {
            //Triggers if File object, FileReader, or FileWriter cannot be created
            editText.setText(e.getMessage());//Not currently visible to user, must dummy out fail message in onEnter to view
            return false;
        }
        return true;
    }

    //This was created for use by fellow developers who need access to the local directory
    /**
     * Returns a file with the same name as the String from the local directory
     *
     *
     *
     *
     * @param name String which holds the filename to pull from local directory
     *
     *
     * @author Brandon Barnes
     *
     * @return A File object for the file in the local directory if successful, otherwise returns null
     */

    File readFileFromDirectory(String name) {
        ContextWrapper c = new ContextWrapper(this);
        try {
            File localFile = new File(c.getFilesDir() + name);
            return localFile;
        } catch (Exception e) {
            return null;
        }
    }
}