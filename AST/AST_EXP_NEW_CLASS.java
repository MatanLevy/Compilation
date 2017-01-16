package AST;

import IR.IR_EXP_NEW_CLASS;
import IR.TEMP;

public class AST_EXP_NEW_CLASS extends AST_EXP {
	
	String _className;
	public AST_EXP_NEW_CLASS(String className) {
		this._className = className;
		this.type = new AST_TYPE_CLASS(className);
		this.typeUptoDate = true;
	}
	public void print() {
		System.out.println("exp new class : " + _className);
	}
	@Override
	public String getName() {
		return _className;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (!typeUptoDate) {
			type = new AST_TYPE_CLASS(_className);
			typeUptoDate = true;
		}
		return type;
	}
	@Override
	public IR_EXP_NEW_CLASS createIR() {
		return new IR_EXP_NEW_CLASS(_className);
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
