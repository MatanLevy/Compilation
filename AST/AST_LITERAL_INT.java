package AST;

public class AST_LITERAL_INT extends AST_LITERAL {
	int _i;
	public AST_LITERAL_INT(Integer i) {
		_i = i;
		this.type = new AST_TYPE_INT();
	}
	
	public void print() {
		System.out.println("literal : ");
		System.out.println(_i);
	}

}
