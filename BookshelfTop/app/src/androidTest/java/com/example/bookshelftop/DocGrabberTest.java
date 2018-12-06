package com.example.bookshelftop;

import android.support.test.rule.ActivityTestRule;
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
    //Unfortunately testing the above cases with JUnit is unfeasible with my current knowledge set as I do not know how to access the necessary items such as EditText and ContextWrapper to properly call the functions within the bounds of this test class
    @Test
    public void readFileFromDirectory() {
        assertEquals("Function should return false when passed null", null, tester.readFileFromDirectory(null));
    }
}