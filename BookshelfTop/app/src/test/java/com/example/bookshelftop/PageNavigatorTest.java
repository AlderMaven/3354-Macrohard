package com.example.bookshelftop;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class PageNavigatorTest {
    PageNavigator tester;

    @Before
    public void setup(){
        // Edit the path to get the correct path will definitely be different
       File file = new File("C:\\Users\\ces161430\\Documents\\GitHub\\3354-Macrohard\\BookshelfTop\\app\\src\\test\\java\\com\\example\\bookshelftop\\TestFile.txt");
       tester = new PageNavigator(file);
    }

    @Test
    public void nextPage(){
        assertTrue("Check if it got the next page", tester.nextPage());
    }

    @Test
    public void checkLastPage(){
        assertFalse("Check whether it is the last page on first page", tester.isLastPage());
    }

    @Test
    public void checkLastPageOnLastPage(){
        while(tester.nextPage()){

        }
        assertTrue("Check whether it is the last page on last page", tester.isLastPage());
    }

    @Test
    public void checkPageString(){
        assertNotNull("Check if tester gets a string", tester.displayCurrentPage());
        System.out.print(tester.displayCurrentPage());
        tester.nextPage();
        System.out.print(tester.displayCurrentPage() + "\n");
        System.out.print(tester.displayPreviousPage());
    }
}
