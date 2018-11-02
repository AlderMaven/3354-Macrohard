
public class Main {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			NoteFile notes = new NoteFile("test file");
			
			notes.addNote(546,"yeh");
			
			notes.addNote(132532,"Billy bear likes ice cream");
			
			notes.addNote(-123431,"negative number test run");
			
			notes.addNote(0,"zero and really long string test run bakjfdlbnkajfalkjbvfljkasbhj fhgkljsfdhg kjslfhglkjs fhglkjfhgjlkshfdg kjfhglkj dfhlgskjdfhg lkjsdfghk ljfsdghlk sdjfghl kjdfsgh");
			
			System.out.println(notes.getNote(132532));
			System.out.println(notes.getNote(0));
			System.out.println(notes.getNote(-123431));
			
			notes.deleteNote(132532);
			
			notes.deleteNote(-123431);
			
			notes.deleteNote(9000);
			
			notes.addNote(20,"My age");
			
			notes.addNote(1000232, "This is a good note");
			
			notes.changeNote(546, "yeeah boiiiii");
			
		}
}
