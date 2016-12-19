package AST;

public class AST_EXP_LITERAL extends AST_EXP {
	AST_LITERAL literal;
	public AST_EXP_LITERAL(AST_LITERAL l) {
		this.literal = l;
		this.type = literal.type;
	}
	
	public void print() {
		System.out.println("exp literal : ");
		literal.print();
	}

}
