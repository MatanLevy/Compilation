package AST;

public class AST_STMT_LIST_BRACE extends AST_STMT {

	private AST_STMT_LIST stmtList;

	public AST_STMT_LIST_BRACE(AST_STMT_LIST body) {
		this.stmtList = body;
	}
	
	public void print() {
		System.out.println("stmt list brace : ");
		stmtList.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
