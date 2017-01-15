package AST;

import IR.IR_STMT_RETURN;

public class AST_STMT_RETURN extends AST_STMT {



	public AST_STMT_RETURN() {
	}

	public void print() {
		System.out.println("return statament");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		AST_TYPE returnType= table.returnTypeCurrentMethod();
		if (returnType != null)
			throw new RuntimeException("method must return " + returnType.getName());
		return true;

	}

	@Override
	public IR_STMT_RETURN IRGenerator() {
		return new IR_STMT_RETURN();
	}
}
