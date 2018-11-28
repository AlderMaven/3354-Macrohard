package com.example.bookshelftop;
import java.io.File;
import java.io.RandomAccessFile;

public class PageNavigator {
    private Page currentPage;
    private boolean isOnCurrentPage = true;

    PageNavigator(File book){
        this.currentPage = new Page(book);

    }
    public boolean isLastPage() {
        RandomAccessFile fileReader = currentPage.getFileReader();
        try {
            return fileReader.getFilePointer() == fileReader.length();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void nextPage() {

        if (!isLastPage()) {
            this.currentPage = new Page(currentPage.getFileReader(), currentPage.getEndPosition(),
                    currentPage.getPageNumber(), currentPage.getPage());

        }

    }

    public String displayCurrentPage(){
        return currentPage.getPage().toString();
    }

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
