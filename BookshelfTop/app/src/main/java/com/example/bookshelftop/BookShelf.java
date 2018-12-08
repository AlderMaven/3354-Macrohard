package com.example.bookshelftop;

import java.util.ArrayList;

/**
 * BookShelf - A manager for a binary tree of books, covers all methods that access books
 * @author John Walsh
 *
 * @Book root - the root node of the binary tree, empty on default
 */

public class BookShelf {
    private Book root;

    public BookShelf(){root = new Book();}
    public BookShelf(Book r){root = r;}

    /**
     * Insert - put a book into the bookshelf binary tree. Insertion is based on string value (Alphabetical)
     *
     * @param in - the book being inserted, this will always be set as a child of a book already in the bookshelf
     *
     * @return boolean canInsert, true if insertion was successful, false if insertion failed
     */
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
                int comp = n.getTitle().compareToIgnoreCase(inTitle);

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
                    return false;
                }
            }
        }

        return canInsert;
    }

    /**
     * returnBooks - runs through all books in the tree and returns an ArrayList of the books in order
     * returnBooks calls the recursive method getAllBooks, which merges sub ArrayLists into one ArrayList containing all books
     *
     * @return an arraylist of all books in bookshelf in alphabetical order
     */
    public ArrayList<Book> returnBooks(){
        ArrayList<Book> out = getAllBooks(root); //call recursive method
        return out;
    }

    /**
     * getAllBooks - recursive method to get all books in order, creates an arraylist of a book and its children then passes it up to the parent
     * children to the left are in one list added at the begining of the array, then the parent is added, then the children to the right are added
     *
     * @param n - the book passed by a parent to be put in the arraylist, then it passes its children and recives their arraylists
     *
     * @return r - a sub ArrayList that gets added onto the ArrayList made above it, contains a sub array of books
     */
    private ArrayList<Book> getAllBooks(Book n){
        ArrayList<Book> r = new ArrayList<Book>();
        if(n == null){return r;}															//if null return null
        else if(n.getDeleted()){return r;}													//if deleted add an asterisk
        r.addAll(this.getAllBooks(n.getLeft()));
        r.add(n);
        r.addAll(this.getAllBooks(n.getRight()));
        return r;
    }
}