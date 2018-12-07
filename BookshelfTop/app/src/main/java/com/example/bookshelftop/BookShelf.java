package com.example.bookshelftop;

import java.util.ArrayList;

public class BookShelf {
    private Book root;

    public BookShelf(){root = new Book();}
    public BookShelf(Book r){root = r;}

    //insert------------------------------------------------------------------------------------------------------------------------------------------------------
    public boolean insert(Book in){
        boolean canInsert = false;
        String inTitle = in.getTitle();

        if(root.getTitle().equals("")){	//if the tree is empty then key is root
            root = in;
            canInsert = true;
        }

        else{
            Book n = root;
            while(n.getTitle() != inTitle){
                int comp = n.getTitle().compareTo(inTitle);

                if(comp<0){						//if nKey < key then keyNode goes to the right
                    if(n.getRight() == null){	//if the right is null and keyNode goes right we set n right to keyNode
                        n.setRight(in);
                        canInsert = true;
                    }
                    n = n.getRight();			//we set n to right regardless because we know key is larger
                }

                else if(comp>0){				//if nKey > key then keyNode goes to the left
                    if(n.getLeft() == null){	//if the left is null and keyNode goes left we set n left to keyNode
                        n.setLeft(in);
                        canInsert = true;
                    }
                    n = n.getLeft();			//we set n to left regardless because we know key is smaller
                }

                else{
                    //Once I figure out how to best do the error message I will put it in here
                    return false;
                }
            }
        }

        return canInsert;
    }

    //insert------------------------------------------------------------------------------------------------------------------------------------------------------
    public ArrayList<Book> returnBooks(){
        ArrayList<Book> out = getAllBooks(root); //call recursive method
        return out;
    }

    private ArrayList<Book> getAllBooks(Book n){
        ArrayList<Book> r = new ArrayList<Book>();
        if(n == null){return r;}															//if null return null
        else if(n.getDeleted()){return r;}													//if deleted add an asterisk
        r.add(n);
        r.addAll(0,this.getAllBooks(n.getLeft()));
        r.addAll(this.getAllBooks(n.getRight()));
        return r;
    }
}