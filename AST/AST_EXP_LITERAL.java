package AST;

import IR.IR_EXP_LITERAL;
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
			this.type = literal.type;
			typeUptoDate = true;
		}
		return type;
	}

	@Override
	public IR_EXP_LITERAL IRGenerator() {
		return null;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
