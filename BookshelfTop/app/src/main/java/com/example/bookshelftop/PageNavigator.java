package com.example.bookshelftop;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class PageNavigator {
    private Page currentPage;
    private boolean isOnCurrentPage = true;

    PageNavigator(File book){
        this.currentPage = new Page(book);

    }
    public boolean isLastPage() {
        FileReader fileReader = currentPage.getFileReader();

        try {
            fileReader.mark(2);
            boolean end =  fileReader.read() == -1;
            if(end){
                return true;
            }
            else{
                fileReader.reset();
                return false;
            }
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
