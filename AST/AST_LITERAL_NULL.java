package AST;

public class AST_LITERAL_NULL extends AST_LITERAL {
	
	public AST_LITERAL_NULL() {
		this.type = new AST_TYPE_CLASS("NULL");
	}
	public void print() {
		System.out.println("literal :");
		System.out.println("null literal");
	}
}
