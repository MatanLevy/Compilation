package AST;

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
	

}
