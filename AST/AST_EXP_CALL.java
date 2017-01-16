package AST;

import IR.IR_EXP;
import IR.TEMP;

public class AST_EXP_CALL extends AST_EXP {
	AST_CALL call;

	public AST_EXP_CALL(AST_CALL c) {
		this.call = c;
		
	}
	
	public void print() {
		System.out.println("exp call : ");
		call.print();
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
			type = call.virtualCall.calcType(table);
			typeUptoDate = true;
		}
		return type;
	}

	@Override
	public IR_EXP IRGenerator() {
		// TODO Auto-generated method stub
		return null;
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
