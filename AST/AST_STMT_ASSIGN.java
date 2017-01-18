package AST;

import IR.TEMP;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_EXP exp;
	public AST_VAR var;

	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}
	
	public void print() {
		System.out.println("assign stmt : ");
		exp.print();
		var.print();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {

		AST_TYPE leftSide = var.calcType(table, false);
		AST_TYPE rightSide = exp.calcType(table);
		if (!SemanticChecker.isBaseClassOf(leftSide.getName(), 
				rightSide.getName()))
			throw new RuntimeException("Incompitable types for assign");
		return true;
	}
	@Override
	public void mipsTranslate(SymbolTable table, String fileName, CodeGenarator genarator) {
		TEMP varTemp = var.calcAddress(table, genarator, fileName);
		TEMP expTemp = exp.calcAddress(table, genarator, fileName);
		//TEMP temp = new TEMP();
		//CodeGenarator.printLWCommand(temp.name, expTemp.name, 0);
		//CodeGenarator.printADDICommand(temp.name, expTemp.name, 0);
		CodeGenarator.printSWCommand(expTemp.name, varTemp.name, 0);
		
	}
	//count = base
//	la $t0, base     // load the address of "base"
//	la $t1, count    // load the address of "count"
//	lw $t2, 0($t0)   // load the data at location "base"
//	sw $t2, 0($t1)   // store that data at location "count"
	


}