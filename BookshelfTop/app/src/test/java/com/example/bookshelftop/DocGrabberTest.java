package com.example.bookshelftop;


import android.widget.EditText;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

public class DocGrabberTest {
    private DocGrabber tester;

    @Before
    public void setup(){
        tester=new DocGrabber();
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onEnter() {
    }

    @Test
    public void getDocumentFromDownload() {


    }

    @Test
    public void writeFileToAppDirectory() {

    }

    @Test
    public void readFileFromDirectory() {
        assertEquals("Function should return false when passed null", null, tester.readFileFromDirectory(null));
    }
}