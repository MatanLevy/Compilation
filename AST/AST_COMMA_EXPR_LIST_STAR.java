package AST;

import java.util.ArrayList;
import java.util.List;

import IR.TEMP;

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
		for (int i=0; i < expList.size(); i++) {
			TEMP tempArg_i = expList.get(i).calcAddress(table, genarator, assemblyFileName);
			String reg = returnRegisterForArgument(i + 1);
			CodeGenarator.printADDICommand(reg, tempArg_i.name, 0);
			
		}
	}
	
	
	public String returnRegisterForArgument(int i) {
		switch (i) {
			case 1:
				return MIPS_COMMANDS.A1;
			case 2:
				return MIPS_COMMANDS.A2;
			case 3:
				return MIPS_COMMANDS.A3;
			default:
				return null;
					
		}
	}

}
