package com.example.bookshelftop;

import android.view.View;

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
public class BookShelfTest extends TestCase {
    Book testBook;
    Book inBook;
    BookShelf testBookshelf;

    @Before
    public void initialize() {
        testBook = new Book("ThisBook");
        inBook = new Book("ThatBook");
        testBookshelf = new BookShelf(testBook);
    }

    /**testing instert method
     *
     */
    //@Test

    public void testInstert() {
        this.initialize();
        assertEquals("Returns true on succsessful insertion", true, testBookshelf.insert(inBook));
        assertEquals("Returns false when importing the same book twice", false, testBookshelf.insert(inBook));
        //assertEquals("Duplicate note", true, notes.addNote(123, "New Note"));
    }

}
