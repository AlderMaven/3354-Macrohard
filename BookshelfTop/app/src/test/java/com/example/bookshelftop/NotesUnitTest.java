package com.example.bookshelftop;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotesUnitTest extends TestCase {

    boolean deleteRun = false;
    boolean changeRun = false;
    NoteFile notes;

    @Before
    public void initialize() {
        File file = new File("testFile.txt");
        notes= new NoteFile(file);
    }




    /**testing addNote method
     *
     */
    @Test
    public void testAddNote() {
        this.initialize();
        assertEquals("Addnote returns true for normal conditions", true, notes.addNote(123, "New Note"));
        assertEquals("Duplicate note", true, notes.addNote(123, "New Note"));
        assertEquals("Addnote returns true for normal conditions", true, notes.addNote(456, "Second Note"));
        assertEquals("Duplicate note", true, notes.addNote(456, "Second Note"));
        assertEquals("Addnote returns false for negative location", false, notes.addNote(-123, "Negative Note"));
    }


    /**
     * testing deleteNote method
     */
    @Test
    public void testDeleteNote() {
        this.initialize();
        assertEquals("deleteNote returns true if note is deleted", true, notes.deleteNote(123));
        assertEquals("deleteNote returns false if note doesn't exist", false, notes.deleteNote(123456));
        assertEquals("deleteNote returns false if negative note doesn't exist", false, notes.deleteNote(-123));
        deleteRun = true;
    }


    /**
     * testing getNote method
     */
    @Test
    public void testGetNote() {
        this.initialize();
        if(changeRun == true) {
            assertEquals("getNote should return note contents", "Changed Note", notes.getNote(456));
        }
        else {
            //assertEquals("getNote should return the note", "Second Note", notes.getNote(456));
        }

        //result of this test depends on whether the testDeleteNote method executes before or after this method
        if(deleteRun == true) {
            assertEquals("getNote should return error message", "No note found", notes.getNote(123));
        }
        else
        {
            assertEquals("getNote should return note contents", "New Note", notes.getNote(123));
        }
    }

    /**
     * testing changeNote method
     */
    @Test
    public void testZChangeNote() {
        this.initialize();

        //test cases
        assertEquals("changeNote should return false, negative note location", false, notes.changeNote(-3, "Changed Note"));
        assertEquals("changeNote should return false, nonexistent note", false, notes.changeNote(45, "Changed Note"));
        assertEquals("changeNote should return true", true, notes.changeNote(456, "Changed Note"));
        changeRun = true;
    }

}
