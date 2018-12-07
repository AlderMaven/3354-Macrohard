package com.example.bookshelftop;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Creates the string and for each page that is going to be displayed and formats it
 *
 *
 * @author Carl Schneider
 *
 *
 */

public class Page {
    private final int LINES = 21;
    private final int CHARS = 32;

    private long startingPosition;
    private long endPosition;
    private StringBuilder page;
    public StringBuilder getPage() {
        return page;
    }

    private StringBuilder previousPage;
    public StringBuilder getPreviousPage() {
        return previousPage;
    }

    public long getEndPosition() {
        return endPosition;
    }

    private int pageNumber;
    private RandomAccessFile fileReader;

    /**
     * Constructor, builds the a Page object
     *
     * @param book  a file that holds the text in the page
     */
    Page(File book){
        try {
            this.fileReader = new RandomAccessFile(book, "r");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();

        }
        this.startingPosition = 0;
        this.pageNumber = 0;
        this.previousPage = null;
        this.page = new StringBuilder();
        createPageFormat();

    }

    /**
     * Constructor
     *
     * @param book                  a RandomAccessFile that reads the text of the book
     * @param startingPosition      the position that the access file starts reading from, usually the last position of the previous page
     * @param pageNumber            the page number of the page being built
     * @param prevPage              the stringbuilder of that held the string of the previous page
     */
    Page(RandomAccessFile book, long startingPosition, int pageNumber, StringBuilder prevPage){

        this.fileReader = book;

        this.startingPosition = startingPosition;

        this.pageNumber = pageNumber;
        this.previousPage = prevPage;
        this.page = new StringBuilder();
        createPageFormat();

    }

    /**
     * Creates the page how it should look on the screen, with 32 characters for
     *      each of the 21 lines
     */
    private void createPageFormat() {
        try {
            char currentChar = 0;

            for(int i = 0; i < LINES; i++) {        // Loops for 21 lines
                for(int j = 0; j < CHARS; j++) {    // loops for 32 characters
                    long position = fileReader.getFilePointer();

                    currentChar = (char)fileReader.read();
                    position++;
                    page.append(currentChar);       // Adds the character to string
                    if(Character.compare(currentChar, '\n') == 0) { // Ends the line at a newline
                        break;
                    }
                    if(Character.compare(currentChar, '\t') == 0) { //Accounts for extra space given by tab
                        j += 7;
                    }
                    if(j == CHARS-1){   // Checks the last word of the line and correctly formats it so words are not cut off
                        char lastChar = currentChar;
                        if(!Character.isWhitespace(lastChar)) {
                            char extraChar = (char)fileReader.read();
                            if(!Character.isWhitespace(extraChar)) {
                                page.append('-');

                                fileReader.getChannel().position(position+1);

                            }
                        }

                    }

                }
                if(!(i == LINES-1 && Character.compare(currentChar, '\n') == 0)) {
                    page.append('\n');
                }
            }
            endPosition= fileReader.getFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public int getPageNumber() {
        return pageNumber;
    }

    public RandomAccessFile getFileReader() {
        return fileReader;
    }

}

