package AST;

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
		
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return virtualCall.checkSemantic(table);
	}


	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		 virtualCall.mipsTranslate(table, assemblyFileName, genartor);
	}



}
