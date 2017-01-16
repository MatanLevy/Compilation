package AST;

import IR.IR_LABEL;
import IR.IR_STMT_IF;
import IR.LABEL;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

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
		if (!(cond.calcType(table) instanceof AST_TYPE_INT))
			throw new RuntimeException("if condition not boolean");
		return body.checkSemantic(table);
	}

	@Override
	public IR_STMT_IF IRGenerator() {
		return new IR_STMT_IF(cond.IRGenerator(),body.IRGenerator(),
				new IR_LABEL(new LABEL("if")));
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}
}