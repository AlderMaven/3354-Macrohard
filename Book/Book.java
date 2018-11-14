
/*
 * Book class: has basic get and set functions so parts of the book can be used
 * 
 * @author Saurabh Shah
 * 
 * @version 1
 * 
 */

import java.io.File;


public class Book {
	
	private String filePath;
	private File bookFile;
	private NoteFile notes;
	private TagFile tags;
	private String bookTitle;
	private Book leftChild;
	private Book rightChild;
	
	
	/**
	 * @param title Title of book
	 * @param book_file Filepath that book txt file is located in
	 */
	public Book(String title, File book_file)
	{
		filePath = book_file.getPath();
		bookFile = book_file;
		bookTitle = title;
		notes = new NoteFile(bookTitle);
		tags = new TagFile(bookTitle);
		leftChild = null;
		rightChild = null;
	}

	
	/**
	 * @return Title of book
	 */
	public String getName()
	{
		return bookTitle;
	}
	
	/**
	 * @param newName new name of book
	 */
	public void setName(String newName)
	{
		bookTitle = newName;
	}
	
	/**
	 * @return TagFile object for this book
	 */
	public TagFile getTags()
	{
		return tags;
	}
	
	/**
	 * @return NoteFile object for this book
	 */
	public NoteFile getNotes()
	{
		return notes;
	}
	
	/**
	 * @return file object for the book txt
	 */
	public File getFile()
	{
		return bookFile;
	}
	
	/**
	 * @return left child of book (for use in binary tree)
	 */
	public Book getLeftChild()
	{
		return leftChild;
	}
	
	/**
	 * @return right child of book (for use in binary tree)
	 */
	public Book getRightChild()
	{
		return rightChild;
	}
	
	/**
	 * @param newLeft replaces the old left child of book (for use in binary tree)
	 */
	public void setLeftChild(Book newLeft)
	{
		leftChild = newLeft;
	}
	
	/**
	 * @param newRight replaces the old right child of book (for use in binary tree)
	 */
	public void setRightChild(Book newRight)
	{
		rightChild = newRight;
	}
	
	
	
	
	
}
