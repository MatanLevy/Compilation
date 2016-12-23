package AST;

public class AST_STMT_RETURN_EXP extends AST_STMT {

	private AST_EXP exp;

	public AST_STMT_RETURN_EXP(AST_EXP e) {
		this.exp = e;
	}
	
	public void print() {
		System.out.println("return_exp statement");
		exp.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
  