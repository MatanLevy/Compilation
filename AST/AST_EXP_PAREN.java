package AST;

import IR.IR_EXP_PAREN;
import IR.TEMP;

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
	public IR_EXP_PAREN IRGenerator() {		
		return new IR_EXP_PAREN(exp.IRGenerator());
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAdrress() {
		// TODO Auto-generated method stub
		return null;
	}

}
