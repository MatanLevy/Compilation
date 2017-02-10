package AST;

import IR.TEMP;

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
		if (exp != null)
			exp.print();
		else
			System.out.println("no exp");
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
			throw new RuntimeException("class " + type.getName() + " hasn't " + " been defined");
		if (table.check_scope(id))
			throw new RuntimeException("symbol " + id + "exist already in this" + " scope");
		if (exp != null) {
			if (!SemanticChecker.isBaseClassOf(type.getName(), exp.calcType(table).getName()))
				throw new RuntimeException("in compitable type assign");
		}
		table.insertASTNode(this);
		return true;

	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		table.insertASTNode(this);

		if (exp != null) {


			int sizeToAllocateForThisStmt = 4;
			CodeGenarator.allocateMemory(sizeToAllocateForThisStmt, true);
			table.updateOffsetOfSymbolEntry(id,CodeGenarator.getOffset());
			
			TEMP rvalue = exp.calcAddress(table, genartor, assemblyFileName);



//			int varOffset = CodeGenarator.getOffset();
//			TEMP lvalue = new TEMP();
//			CodeGenarator.printADDICommand(lvalue.name, MIPS_COMMANDS.FRAME_PTR, varOffset);
//			CodeGenarator.printSWCommand(rvalue.name, lvalue.name, 0);
			
			CodeGenarator.printSWInFpPlusOffset(rvalue);
		}
	} 

}
