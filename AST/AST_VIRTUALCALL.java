package AST;

public class AST_VIRTUALCALL extends AST_Node{	
	AST_EXP exp;
	String _id;
	AST_COMMA_EXPR_LIST exp_list;
	
	public AST_VIRTUALCALL(AST_EXP e, String id, AST_COMMA_EXPR_LIST l) {
		exp = e;
		_id = id;
		exp_list = l;
	}

	public AST_VIRTUALCALL(String id, AST_COMMA_EXPR_LIST l) {
		exp = null;
		_id = id;
		exp_list = l; 
	}
	
	public void print() {
		System.out.println("virtual call : ");
		System.out.println("id = " + _id);
		 if (exp != null ) exp.print(); else System.out.println("no exp");
		exp_list.print();
		
	}

	@Override
	public String getName() {
		return _id;
	}

}
