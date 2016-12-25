package AST;

public class AST_STMT_RETURN extends AST_STMT {



	public AST_STMT_RETURN() {
	}

	public void print() {
		System.out.println("return statament");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		throw new RuntimeException("WE MUST IMPLEMENT RETURN!!!!");

	}
}
