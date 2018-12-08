package com.example.bookshelftop;

import android.widget.Button;

public class Book {
    private String title;
    private Button bookButton;
    private Book leftChild;
    private Book rightChild;
    private boolean deleted;

    public Book(){
        title = "";
    }
    public Book(String k){
        title = k;
        deleted = false;
    }
    public Book(String k, Button b){
        title = k;
        bookButton = b;
        deleted = false;
    }

    public void setLeft(Book l){leftChild = l;}
    public void setRight(Book r){rightChild = r;}
    public Book getLeft(){return leftChild;}
    public Book getRight(){return rightChild;}

    public String getTitle(){return title;}
    public void setTitle(String k){title = k;}
    public void setDeleted(boolean d){deleted = d;}
    public boolean getDeleted(){return deleted;}

    public void setBookButton(Button l){bookButton = l;}
    public Button getBookButton(){return bookButton;}
}
