import android.view.View;
import android.widget.Button;

import com.example.jack.practice.R;

public class Book {
    private String title;
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

    public void setLeft(Book l){leftChild = l;}
    public void setRight(Book r){rightChild = r;}
    public Book getLeft(){return leftChild;}
    public Book getRight(){return rightChild;}

    public String getTitle(){return title;}
    public void setTitle(String k){title = k;}
    public void setDeleted(boolean d){deleted = d;}
    public boolean getDeleted(){return deleted;}
}
