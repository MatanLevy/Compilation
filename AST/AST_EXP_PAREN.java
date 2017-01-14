package AST;

import IR.IR_EXP;

public class AST_EXP_PAREN extends AST_EXP {

	AST_EXP exp;

	public AST_EXP_PAREN(AST_EXP e) {
		this.exp = e;
	}
	
	public void print() {
		System.out.println("exp paren");
		exp.print();
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

	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (!typeUptoDate) {
			type = exp.calcType(table);
			typeUptoDate = true;
		}
		return type;
	}

	@Override
	public IR_EXP createIR() {
		// TODO Auto-generated method stub
		return null;
	}

}
