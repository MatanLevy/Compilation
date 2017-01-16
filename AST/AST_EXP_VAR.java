package AST;

import IR.IR_EXP;
import IR.TEMP;

public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR var;
	
	public AST_EXP_VAR(AST_VAR var)
	{
		this.var = var;
	}
	
	public void print() {
		System.out.println("exp var : ");
		var.print();
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
			type = var.calcType(table, true);
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	
}