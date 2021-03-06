package AST;

import COUNTERS.TEMP;

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
//		int sizeToAllocateForThisStmt = 4;
//		CodeGenarator.allocateMemory(sizeToAllocateForThisStmt);
		//CodeGenarator.changeOffset(sizeToAllocateForThisStmt);
		TEMP address = var.calcAddress(table, genrator, fileName);
		//since this exp_var and not var we get the current value
		TEMP infoFromAddress = new TEMP();
		CodeGenarator.printLWCommand(infoFromAddress.name, address.name, 0);
		return infoFromAddress;

	}


	
}