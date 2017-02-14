package AST;

import COUNTERS.TEMP;

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
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
	}


	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		return exp.calcAddress(table, genarator, fileName);
	}

}
