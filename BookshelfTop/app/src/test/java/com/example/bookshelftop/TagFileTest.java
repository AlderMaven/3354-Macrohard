package com.example.bookshelftop;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TagFileTest {
    private TagFile tester;

    @Before
    public void setup(){
        tester=new TagFile(new File("notefile.txt"));
    }

    @Test
    public void addTag() {
        assertEquals("Check to see that adding a tag works", true, tester.addTag("Horror"));
        assertEquals("Check to see that pulling a tag from the file works", true, tester.addTag("Action"));
    }

    @Test
    public void getTag() {
        assertEquals("Check to see that pulling a tag from the file works", "Horror", tester.getTag("Horror"));
    }

    @Test
    public void deleteTag() {
        assertEquals("Check to see that deleting a tag from the file works", true, tester.deleteTag("Horror"));
    }

    @Test
    public void changeTag() {
        assertEquals("Check to see that changing a tag from the file works", true, tester.changeTag("Horror", "Action"));
    }

    @Test
    public void listTags() {

    }
}