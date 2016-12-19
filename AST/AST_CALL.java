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

}
