package AST;

import IR.IR_STMT;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT body) {
		this.cond = cond;
		this.body = body;
	}
	public void print() {
		System.out.println("while statemnt");
		cond.print();
		body.print();
	}
	@Override
	public String getName() {
		return null;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (!(cond.calcType(table) instanceof AST_TYPE_INT))
			throw new RuntimeException("while condition not boolean");
		return body.checkSemantic(table);
	}
	@Override
	public IR_STMT createIR() {
		// TODO Auto-generated method stub
		return null;
	} 


}