package com.example.bookshelftop;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
    Page(RandomAccessFile book, long startingPosition, int pageNumber, StringBuilder prevPage){

        this.fileReader = book;

        this.startingPosition = startingPosition;
        try {
            fileReader.getChannel().position(startingPosition+1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.pageNumber = pageNumber;
        this.previousPage = prevPage;
        this.page = new StringBuilder();
        createPageFormat();

    }

    private void createPageFormat() {
        try {
            char currentChar = 0;
            for(int i = 0; i < LINES; i++) {
                for(int j = 0; j < CHARS; j++) {
                    long currentPosition = fileReader.getFilePointer();
                    currentChar = (char)fileReader.read();
                    page.append(currentChar);
                    if(Character.compare(currentChar, '\n') == 0) {
                        break;
                    }
                    if(Character.compare(currentChar, '\t') == 0) {
                        j += 7;
                    }
                    if(j == CHARS-1){
                        char lastChar = currentChar;
                        if(!Character.isWhitespace(lastChar)) {
                            char extraChar = (char)fileReader.readChar();
                            if(!Character.isWhitespace(extraChar)) {
                                page.append('-');

                                fileReader.getChannel().position(currentPosition+1);

                            }
                        }

                    }

                }
                if(!(i == LINES-1 && Character.compare(currentChar, '\n') == 0)) {
                    page.append('\n');
                }
            }
            endPosition = fileReader.getFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public long getStartingPosition() {
        return startingPosition;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public RandomAccessFile getFileReader() {
        return fileReader;
    }

}

