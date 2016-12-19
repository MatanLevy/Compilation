package AST;

public class AST_FORMALS extends AST_Node {

	
	private AST_TYPE type;
	private String _id;
	private AST_FORMALS_LIST f_list; 
	
	public AST_FORMALS(AST_TYPE t, String id, AST_FORMALS_LIST fl) {
		type = t;
		_id = id;  
		f_list = fl;
	}
	public AST_FORMALS() {
		type = null;
		_id = null;
		f_list = null;
	}
	
	public void print() {
		System.out.println("formals : ");
		if (type!= null ) type.print(); else System.out.println("no type");
		if (_id != null) System.out.println("id = "+_id); else System.out.println("no _id");
		if (f_list != null) f_list.print(); else System.out.println("no f_list");
	}

}
