package AST;

import IR.IR_STMT;
import IR.IR_STMT_RETURN_EXP;

public class AST_STMT_RETURN_EXP extends AST_STMT {

	private AST_EXP exp;

	public AST_STMT_RETURN_EXP(AST_EXP e) {
		this.exp = e;
	}
	
	public void print() {
		System.out.println("return_exp statement");
		exp.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		AST_TYPE evlType = exp.calcType(table);
		AST_TYPE returnType= table.returnTypeCurrentMethod();
		if (returnType == null) //void
			throw new RuntimeException("void method mustn't return value");
		if (!(SemanticChecker.isBaseClassOf(returnType.getName(), 
				evlType.getName())))
			throw new RuntimeException("method should return " + returnType.getName()
			+ " and not " + evlType.getName());
		return true;
			
	}

	@Override
	public IR_STMT_RETURN_EXP createIR() {
		return new IR_STMT_RETURN_EXP(exp.createIR());
	}

}
  