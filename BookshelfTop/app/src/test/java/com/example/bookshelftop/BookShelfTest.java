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
    Book a, c, m, q, y, z;
    BookShelf testBookshelf;
    ArrayList<Book> tbsContents;

    @Before
    public void initialize() {
        testBook = new Book("ThisBook");
        inBook = new Book("ThatBook");
        a = new Book("alpha");
        c = new Book("car");
        m = new Book("music");
        q = new Book("Queen");
        y = new Book("Young");
        z = new Book("zebra");
        testBookshelf = new BookShelf();

        tbsContents = new ArrayList<Book>();
        //create the arraylist with elements in alphabetical order
        tbsContents.add(a);
        tbsContents.add(c);
        tbsContents.add(m);
        tbsContents.add(q);
        tbsContents.add(y);
        tbsContents.add(z);
    }

    /**
     *testing instert method
     */
    @Test
    public void testInstert() {
        this.initialize();
        assertEquals("Returns true on succsessful insertion to root", true, testBookshelf.insert(testBook));
        assertEquals("Returns true on succsessful insertion to leaf", true, testBookshelf.insert(inBook));
        assertEquals("Returns false when importing the same book twice", false, testBookshelf.insert(inBook));
    }

    /**
     *testing that returnBooks returns ordered books
     */
    @Test
    public void testReturnBooks() {
        this.initialize();

        //create the bookshelf with all elements inserted in alphabetical order
        testBookshelf.insert(a);
        testBookshelf.insert(c);
        testBookshelf.insert(m);
        testBookshelf.insert(q);
        testBookshelf.insert(y);
        testBookshelf.insert(z);
        assertEquals("Returns books in alphabetical order : entered in alphabetical order", tbsContents, testBookshelf.returnBooks());

        //create the bookshelf with all elements inserted in reverse order
        this.initialize();
        testBookshelf.insert(z);
        testBookshelf.insert(y);
        testBookshelf.insert(q);
        testBookshelf.insert(m);
        testBookshelf.insert(c);
        testBookshelf.insert(a);
        assertEquals("Returns books in alphabetical order : entered in alphabetical order", tbsContents, testBookshelf.returnBooks());

        //create the bookshelf with all elements inserted in mixed order
        this.initialize();
        testBookshelf.insert(m);
        testBookshelf.insert(c);
        testBookshelf.insert(y);
        testBookshelf.insert(z);
        testBookshelf.insert(a);
        testBookshelf.insert(q);
        assertEquals("Returns books in alphabetical order : entered in alphabetical order", tbsContents, testBookshelf.returnBooks());
    }

}
