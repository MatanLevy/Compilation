package AST;

import COUNTERS.TEMP;

public class AST_COMMA_EXPR_LIST extends AST_Node {
	AST_EXP exp;
	AST_COMMA_EXPR_LIST_STAR list;
	public AST_COMMA_EXPR_LIST(AST_EXP e, AST_COMMA_EXPR_LIST_STAR l) {
		list = l;
		exp = e;
	}
	public AST_COMMA_EXPR_LIST() {
		exp = null;
		list = null;
	}
	
	public void print() {
		System.out.println("comma exp list : ");
		if ( exp != null) exp.print(); else System.out.println("no exp");
		if (list != null) list.print(); else System.out.println("no list");
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genarator) {
		if (exp != null) {
//			TEMP tempExp = exp.calcAddress(table, genarator, assemblyFileName);
//			//CodeGenarator.changeOffset(4);
//			int offset = CodeGenarator.getOffset();
//			CodeGenarator.changeOffset(4);
//			CodeGenarator.printAndPrepareArgumentBeforeCall(offset, tempExp);
//			//CodeGenarator.printADDICommand(MIPS_COMMANDS.A0, tempExp.name, 0);
			CodeGenarator.allocateMemory(4, true);
			TEMP temp = exp.calcAddress(table,genarator,assemblyFileName);
			CodeGenarator.printSWInFpPlusOffset(temp);
		}
		if (list != null) {
			list.mipsTranslate(table, assemblyFileName, genarator);
		}
	}
	
	public int getSize () {
		return ((exp==null) ? 0 : 1) + (list==null ? 0 : list.getSize());

	}

}
