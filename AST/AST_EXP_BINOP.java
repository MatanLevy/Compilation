package AST;

public class AST_EXP_BINOP extends AST_EXP
{
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP OP)
	{
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	public AST_TYPE calcResultType(AST_TYPE left,AST_BINOP op,AST_TYPE right) {
		if (!left.equals(right)) {
			return null; // types are not the same
		}
		if (OP.getOp() == "EQUAL" || OP.getOp() == "NON EQUAL") {
			return new AST_TYPE_INT();
		}
		if (left instanceof AST_TYPE_INT && right instanceof AST_TYPE_INT) { // raw types
			return new AST_TYPE_INT();
		}
		if (left instanceof AST_TYPE_STRING && right instanceof AST_TYPE_STRING) {
			if (OP.getOp() == "PLUS") {
				return new AST_TYPE_STRING();
			}
		}
		return null;
	}
	
	public void print() {
		System.out.println("exp binop : ");
		left.print();
		OP.print();
		right.print();
	}
	


}