package AST;

import java.util.ArrayList;
import java.util.List;

import COUNTERS.TEMP;

public class AST_COMMA_EXPR_LIST_STAR extends AST_Node {

	List<AST_EXP> expList;
	
	
	public AST_COMMA_EXPR_LIST_STAR() {
		expList = new ArrayList<>();
	}
	
	public void addExp(AST_EXP e) {
		expList.add(e);
	}
	
	public void print() {
		System.out.println("comma exp list star :");
		for (AST_EXP exp : expList) {
			exp.print();
		}
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genarator) {
//		int offset = CodeGenarator.getOffset();
//		CodeGenarator.changeOffset(4);
//		for (int i=0; i < expList.size(); i++) {
//			TEMP tempArg_i = expList.get(i).calcAddress(table, genarator, assemblyFileName);
//			CodeGenarator.printAndPrepareArgumentBeforeCall(offset, tempArg_i);
//			offset -= 4;
			for (AST_EXP exp : expList) {
				CodeGenarator.allocateMemory(4, true);
				TEMP temp = exp.calcAddress(table,genarator,assemblyFileName);
				CodeGenarator.printSWInFpPlusOffset(temp);
		}
	}
	
	public int getSize() {
		return expList.size();
	}
	
	


}
