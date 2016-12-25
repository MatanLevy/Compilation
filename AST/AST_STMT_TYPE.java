package AST;

public class AST_STMT_TYPE extends AST_STMT {

	public AST_TYPE type;
	public String id;
	public AST_EXP exp;

	public AST_STMT_TYPE(AST_TYPE t, String id) {
		this.type = t;
		this.id = id;
		this.exp = null;
	}

	public AST_STMT_TYPE(AST_TYPE t, String id, AST_EXP e) {
		this.type = t;
		this.id = id;
		this.exp = e;
	}
	public void print() {
		System.out.println("type statement");
		type.print();
		System.out.println("id = " + id);
		if (exp != null) exp.print(); else System.out.println("no exp");
	}

	@Override
	public String getName() {
		return id;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (table.check_scope(id))
			throw new RuntimeException("symbol " + id + "exist already in this"
					+ " scope");
		if (exp != null) {
			if (! SemanticChecker.isBaseClassOf(type.getName(), 
					exp.calcType(table).getName()))
				throw new RuntimeException("in compitable type assign");
		}
		table.insertASTNode(this);
		return true;
		
	}

}
