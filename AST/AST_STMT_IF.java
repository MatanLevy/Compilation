package AST;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/


	public AST_STMT_IF(AST_EXP cond, AST_STMT body) {
		this.cond = cond;
		this.body = body;
	}
	
	public void print() {
		System.out.println("if stmt :");
		System.out.println(" condition  = ");
		cond.print();
		System.out.println(" body =  ");
		body.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}
}