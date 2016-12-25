package AST;

import javax.management.RuntimeErrorException;

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
		if (!(cond.type instanceof AST_TYPE_INT))
			throw new RuntimeException("if stmt not boolean");
		return body.checkSemantic(table);
	} 


}