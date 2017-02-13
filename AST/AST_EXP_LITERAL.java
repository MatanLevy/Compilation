package AST;

import IR.TEMP;

public class AST_EXP_LITERAL extends AST_EXP {
	AST_LITERAL literal;

	public AST_EXP_LITERAL(AST_LITERAL l) {
		this.literal = l;
		this.type = literal.type;
		typeUptoDate = true;
	}

	public void print() {
		System.out.println("exp literal : ");
		literal.print();
	}

	@Override
	public String getName() {
		return literal.getName();
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (!typeUptoDate) {
			this.type = literal.type;
			typeUptoDate = true;
		}
		boolean dummy_bool = literal.checkSemantic(table);
		return type;
	}


	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub

	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
//		int sizeToAllocateForThisStmt = 4;
//		CodeGenarator.allocateMemory(sizeToAllocateForThisStmt);
		//CodeGenarator.changeOffset(sizeToAllocateForThisStmt);

		TEMP result = new TEMP();
		if (literal instanceof AST_LITERAL_INT) {
			int value = ((AST_LITERAL_INT)literal)._i;
			CodeGenarator.printLICommand(result.name, value);
		}
		if (literal instanceof AST_LITERAL_NULL) {
			CodeGenarator.printLICommand(result.name, 0);  //null value
		}
		if (literal instanceof AST_LITERAL_STRING){
			literal.mipsTranslate(table, fileName, genarator);
			String str = ((AST_LITERAL_STRING)literal).str;
			STRING_LABEL label = CodeGenarator.stringToStringLabelMap.get(str);
			if (label != null)
				CodeGenarator.printLACommand(result.name, label.str_label);
		}
		return result;
	}
}
