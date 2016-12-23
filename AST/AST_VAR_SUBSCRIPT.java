package AST;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_EXP var;
	public AST_EXP subscript;
	
	public AST_VAR_SUBSCRIPT(AST_EXP e1, AST_EXP e2) {
		var = e1;
		subscript = e2;
	}
	
	public boolean assertType() {
		return (subscript.type instanceof AST_TYPE_INT);
	}
	public void calcType() {
		if (assertType()) {
			type = var.type;
		}
		else {
			type = null;
		}
	}
	
	
	
	public void print() {
		System.out.println("var subscript : ");
		var.print();
		subscript.print();
	}
}