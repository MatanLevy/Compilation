package AST;

import IR.IR_STMT;

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

	@Override
	public boolean checkSemantic(SymbolTable table) {
		table.pushScope(false, null);
		boolean bodyreturned = stmtList.checkSemantic(table);
		table.popScope();
		return bodyreturned;
	}

	@Override
	public IR_STMT createIR() {
		// TODO Auto-generated method stub
		return null;
	}

}
