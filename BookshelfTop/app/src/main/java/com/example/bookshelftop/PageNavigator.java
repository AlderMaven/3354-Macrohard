package com.example.bookshelftop;
import java.io.File;

import java.io.RandomAccessFile;

/**
 * Oragnizes page changes for easier control with less risk
 *
 *@author Carl Schneider
 *
 */

public class PageNavigator {
    private Page currentPage;
    private boolean isOnCurrentPage = true;

    PageNavigator(File book){
        this.currentPage = new Page(book);

    }

    /**
     * Checks whether the current page is the last page or not
     * @return boolean the boolean is whether the current page is the last page or not
     *          true = current page is last page
     *          false = current page is not last page
     */
    public boolean isLastPage() {
        RandomAccessFile fileReader = currentPage.getFileReader();

        try {
            long position = fileReader.getFilePointer();
            boolean end =  fileReader.read() == -1;
            if(end){
                return true;
            }
            else{
                fileReader.getChannel().position(position);
                return false;
            }
        }
        catch(java.io.IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the next page in the book and sets it as the current page
     *
     * @return boolean the boolean is whether the next page was reached
     */
    public boolean nextPage() {

        if (!isLastPage()) {
            this.currentPage = new Page(currentPage.getFileReader(), currentPage.getEndPosition(),
                    currentPage.getPageNumber(), currentPage.getPage());
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @return String    the string is the formatted string of the current page
     */
    public String displayCurrentPage(){
        return currentPage.getPage().toString();
    }

    /**
     *
     * @return String   the string is the formatted string of the previous page
     */
    public String displayPreviousPage(){
        if(isOnCurrentPage) {
            isOnCurrentPage = false;
            return currentPage.getPreviousPage().toString();
        }
        else{
            return null;
        }
    }

}
