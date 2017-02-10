package AST;

import IR.TEMP;

public class AST_EXP_CALL extends AST_EXP {
	AST_CALL call;

	public AST_EXP_CALL(AST_CALL c) {
		this.call = c;
		
	}
	
	public void print() {
		System.out.println("exp call : ");
		call.print();
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
			type = call.virtualCall.calcType(table);
			typeUptoDate = true;
		}
		return type;
	}



	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		call.mipsTranslate(table, assemblyFileName, genartor);
	}


	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		call.virtualCall.mipsTranslate(table, fileName, genarator);
		return call.virtualCall.calcAddress(table,genarator,fileName);
	}
	

}
