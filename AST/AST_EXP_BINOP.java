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
	
	public void print() {
		System.out.println("exp binop : ");
		left.print();
		OP.print();
		right.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table) {
		AST_TYPE leftType = left.calcType(table); 
		AST_TYPE rightType = right.calcType(table);
		if (!leftType.equals(rightType)) {
			throw new RuntimeException("Incomparble types");
		}
		if (OP.getOp() == "EQUAL" || OP.getOp() == "NON EQUAL") {
			return new AST_TYPE_INT();
		}
		if (leftType instanceof AST_TYPE_INT && rightType instanceof AST_TYPE_INT) { // raw types
			return new AST_TYPE_INT();
		}
		if (leftType instanceof AST_TYPE_STRING && rightType instanceof AST_TYPE_STRING) {
			if (OP.getOp() == "PLUS") {
				return new AST_TYPE_STRING();
			}
		}
		throw new RuntimeException("can't invoke binary operation here " );
	}
	


}