
import java.io.File;


public class Book {
	
	private String filePath;
	private File bookFile;
	private NoteFile notes;
	private TagFile tags;
	private String bookTitle;
	private Book leftChild;
	private Book rightChild;
	
	
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

	
	public String getName()
	{
		return bookTitle;
	}
	
	public void setName(String newName)
	{
		bookTitle = newName;
	}
	
	public TagFile getTags()
	{
		return tags;
	}
	
	public NoteFile getNotes()
	{
		return notes;
	}
	
	public File getFile()
	{
		return bookFile;
	}
	
	public Book getLeftChild()
	{
		return leftChild;
	}
	
	public Book getRightChild()
	{
		return rightChild;
	}
	
	public void setLeftChild(Book newLeft)
	{
		leftChild = newLeft;
	}
	
	public void setRightChild(Book newRight)
	{
		rightChild = newRight;
	}
	
	
	
	
	
}
