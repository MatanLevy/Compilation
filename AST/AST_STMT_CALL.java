package AST;

import IR.IR_STMT_CALL;

public class AST_STMT_CALL extends AST_STMT {

	private AST_CALL call;

	public AST_STMT_CALL(AST_CALL c) {
		this.call = c;
	}

	
	public void print() {
		System.out.println("call statement : ");
		System.out.println("call = ");
		call.print();
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean checkSemantic(SymbolTable table) {
		return call.checkSemantic(table);
	}


	@Override
	public IR_STMT_CALL IRGenerator() {
		// TODO Auto-generated method stub
		return new IR_STMT_CALL(call.IRGenerator());
	}


	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}
}
