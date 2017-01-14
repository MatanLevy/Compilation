package AST;

import IR.IR_STMT;

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
		if (type == null || (exp != null && exp.calcType(table) == null))
			throw new RuntimeException("can't assign from/to void type");
		if (!SemanticChecker.isTypeDefinedAlready(table, type))
			throw new RuntimeException("class " + type.getName() + " hasn't "
					+" been defined");
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

	@Override
	public IR_STMT createIR() {
		// TODO Auto-generated method stub
		return null;
	}



}
