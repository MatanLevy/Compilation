package AST;

import COUNTERS.TEMP;

public class AST_EXP_NEW_TYPE extends AST_EXP {
	AST_TYPE typeleft;
	AST_EXP exp;
	public AST_EXP_NEW_TYPE(AST_TYPE t, AST_EXP e) {
		this.typeleft = t;
		this.exp = e;
	}
	
	public void print() {
		System.out.println("exp new type : ");
		type.print();
		exp.print();
	}
	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (!typeUptoDate) {
			AST_TYPE expType = (exp.typeUptoDate) ? exp.type : exp.calcType(table);
			if (!(expType instanceof AST_TYPE_INT))
				throw new RuntimeException("Cant initalized array without integer"
						+ " type");
			type = new AST_TYPE_BRACK(typeleft);
			typeUptoDate = true;
		}
		return type;
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
//		int sizeToAllocateForThisStmt = 4;
//		CodeGenarator.allocateMemory(sizeToAllocateForThisStmt);
		//CodeGenarator.changeOffset(sizeToAllocateForThisStmt);
		TEMP arraySize = exp.calcAddress(table, genarator, fileName);
		TEMP result =  genarator.ArrayAlloc(arraySize);
		
		CodeGenarator.printADDICommand(MIPS_COMMANDS.B1, arraySize.name, 0);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.B2, result.name, 0);
		CodeGenarator.printJALCommand(CodeGenarator.initArray.labelString);

		
		//init array with zeros.
		/*if (exp instanceof AST_EXP_LITERAL) {
			AST_EXP_LITERAL dummy_exp = (AST_EXP_LITERAL) exp;
			AST_LITERAL dummy_literal = dummy_exp.literal;
			if (dummy_literal instanceof AST_LITERAL_INT) {
				AST_LITERAL_INT sizeLiteral = (AST_LITERAL_INT) dummy_literal;
				int size = sizeLiteral._i * 4;
				TEMP Iterator = new TEMP();
		        CodeGenarator.printADDICommand(Iterator.name,result.name,0);
		        TEMP zero = new TEMP();
				 for (int i = 4; i < size; i += 4) {
			            CodeGenarator.printADDICommand(Iterator.name,result.name,i);
			            CodeGenarator.printSWCommand(zero.name, Iterator.name, 0);
			        }
			}
			
		}*/
		
		return result;
	}

}
