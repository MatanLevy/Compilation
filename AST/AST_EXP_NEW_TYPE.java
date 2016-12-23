package AST;

public class AST_EXP_NEW_TYPE extends AST_EXP {
	AST_EXP exp;
	public AST_EXP_NEW_TYPE(AST_TYPE t, AST_EXP e) {
		this.type = t;
		this.exp = e;
	}
	
	public void print() {
		System.out.println("exp new type : ");
		type.print();
		exp.print();
	}
	public boolean assertEXpTypeInteger() {
		return (exp.type instanceof AST_TYPE_INT ) ;
	}

}
