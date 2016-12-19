package AST;

public class AST_EXP_PAREN extends AST_EXP {

	AST_EXP exp;

	public AST_EXP_PAREN(AST_EXP e) {
		this.exp = e;
		this.type = e.type;
	}
	
	public void print() {
		System.out.println("exp paren");
		exp.print();
	}

}
