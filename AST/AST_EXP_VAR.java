package AST;

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
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {	
	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genrator, String fileName) {
		return var.calcAddress(table, genrator, fileName);
	}


	
}