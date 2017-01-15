package AST;

import IR.IR_CALL;

public class AST_CALL extends AST_Node {
	
	AST_VIRTUALCALL virtualCall;

	public AST_CALL(AST_VIRTUALCALL vc) {
		this.virtualCall = vc;  
	}
	
	public void print() {
		System.out.println("call : ");
		virtualCall.print();  
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return virtualCall.checkSemantic(table);
	}

	public IR_CALL IRGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

}
