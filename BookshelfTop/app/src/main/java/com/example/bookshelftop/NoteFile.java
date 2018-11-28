
/*
 * NoteFile class: stores book notes in a file so they can be accessed later. If 2 books with the same name are imported they will share a
 * NoteFile (old file will NOT be overwritten). The location of the note (a long) must be known in order to access it.
 *
 * @author Saurabh Shah
 *
 * @version 1
 *
 */


package com.example.docgrabber;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class NoteFile {

    //string variables for holding book title and filepath for text file of book
    private String filePath;
    private String bookTitle = "";

    /**
     *Constructor
     *Creates a file in default directory (needs to be changed) and adds a
     *title to the file along with 2 newlines
     *
     * @param bookFile title of book that the NoteFile is for
     */
    public NoteFile(File bookFile)
    {
        bookTitle = bookFile.getName();
        filePath = ((bookFile.getPath()).substring(0,((bookFile.getPath()).length())-4))+"notes.txt";                //should be the filepath
        boolean fileDoesNotExist;

        try
        {
            File file = new File(filePath);
            fileDoesNotExist = file.createNewFile();
            if(fileDoesNotExist) {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Notes for " + bookTitle + ":");
                bw.newLine();
                bw.newLine();
                bw.write(">> ");
                bw.close();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    /**
     *addNote function
     *adds a note to the note file along with its location in the text
     *returns true is successful, returns false if not.
     *>> signals the beginning of a note, followed by a number that shows the location and then the actual text
     *
     * @param noteLoc location of the book in the book txt
     * @param noteContents text that the note contains at the specific location
     * @return true if note was added properly
     */

    public boolean addNote(long noteLoc, String noteContents)
    {
        try
        {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(noteLoc+" "+noteContents);
            bw.newLine();
            bw.newLine();
            bw.write(">> ");
            bw.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

    }


    /*getNote method
     *
     * returns the text of a note if provided with its location
     * @param noteLoc location of desired note
     * @return contents of desired note
     */
    public String getNote(long noteLoc)
    {
        File file = new File(filePath);
        Scanner reader;
        try {
            reader = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return "No note found";
        }

        while(reader.hasNextLine())
        {
            if(reader.next().equals(">>"))
            {
                if(Long.parseLong(reader.next()) == noteLoc)
                {
                    String out =  reader.nextLine();
                    reader.close();
                    return out;
                }
                else
                {
                    reader.nextLine();
                    continue;
                }
            }
        }

        reader.close();
        return "No note found.";

    }



    /**
     * deleteNote method
     *
     * removes a note from the file given the note location

     * @param noteLoc location of bad note
     * @return true if note was deleted
     */
    public boolean deleteNote(long noteLoc)
    {
        //altpath is just filepath but the file name is "file2.txt instead of "file.txt""
        File original = new File(filePath);
        String altPath = filePath.substring(0,(filePath.length()-4)).concat("2"+filePath.substring((filePath.length()-4),filePath.length()));

        Scanner reader;
        try {
            reader = new Scanner(original);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try
        {
            File newFile = new File(altPath);
            newFile.createNewFile();
            FileWriter fw = new FileWriter(newFile);
            BufferedWriter bw = new BufferedWriter(fw);
            String nxtLn;

            bw.write(reader.nextLine());
            bw.newLine();
            bw.newLine();

            //creates alternate file with the name "file2.txt"
            while(reader.hasNextLine())
            {
                nxtLn = reader.nextLine();

                if(nxtLn.length() < 4)
                {
                    continue;
                }

                if((nxtLn.substring(0,2)).equals(">>"))
                {
                    String test =nxtLn.substring(3);
                    int x = test.indexOf(" ") +3;
                    test = nxtLn.substring(3,x);
                    //if(Long.parseLong(nxtLn.substring(3,((nxtLn.substring(3)).indexOf(" ")))) == noteLoc)
                    if(Long.parseLong(test) != noteLoc)
                    {
                        bw.write(nxtLn);
                        bw.newLine();
                        bw.newLine();
                    }
                }

            }
            bw.write(">> ");
            bw.close();

            reader.close();

            original.delete();

            newFile.renameTo(new File(filePath));

            return true;

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        reader.close();

        return false;

    }



    /**
     * changeNote method
     *
     * calls the delete method and add method to edit a note without changing its location

     * @param noteLoc location of note to be changed
     * @param newNote new note contents
     * @return true if note successfully changed
     */
    public boolean changeNote(long noteLoc, String newNote)
    {
        try
        {
            this.deleteNote(noteLoc);
            this.addNote(noteLoc, newNote);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     *  listNotes
     *
     * returns a list of notes in a certain range
     *

     * @param startLoc initial location to start looking for notes
     * @param endLoc location to stop looking for notes
     * @return returns a list of strings containing the contents of all notes within a range
     */
    public List<String> listNotes(long startLoc, long endLoc)
    {
        List<String> noteList = new ArrayList<String>();

        for(long i = startLoc; i < endLoc; i++)
        {
            noteList.add(this.getNote(i));
        }

        return noteList;
    }

}

