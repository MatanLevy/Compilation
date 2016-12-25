package AST;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_EXP exp;
	public AST_VAR var;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}
	
	public void print() {
		System.out.println("assign stmt : ");
		exp.print();
		var.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		AST_TYPE leftSide = var.calcType(table);
		AST_TYPE rightSide = exp.calcType(table);
		if (!SemanticChecker.isBaseClassOf(leftSide.getName(), 
				rightSide.getName()))
			throw new RuntimeException("Incompitable types for assign");
		return true;
	}
}