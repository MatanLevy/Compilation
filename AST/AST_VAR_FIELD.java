package AST;

import IR.BIN_OP;
import IR.IR_CONST;
import IR.IR_EXP_BINOP;
import IR.IR_EXP_LITERAL;
import IR.IR_EXP_MEM;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP var;
	public String fieldName;
	public SymbolTable table;
	
	public AST_VAR_FIELD(AST_EXP e, String fieldName2) {
		var = e;
		fieldName = fieldName2;
	}
	
	public void print() {
		System.out.println("var field : ");
		var.print();
		System.out.println("field name = " + fieldName);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return fieldName;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize) {
		this.table = table;
		AST_TYPE classType = (var.typeUptoDate)? var.type : var.calcType(table);
		if (!(classType instanceof AST_TYPE_CLASS)) 
			throw new RuntimeException("cannt access field of type not class");
		String className = classType.getName();
		//ScopeNode node = table.getClassScope(className);
		//Hashtable<String, SymbolEntry> hash = node.getSymbols();
		SymbolEntry entry = (table.get_currentClass().equals(className)) ? 
				table.find_symbol(fieldName) : table.getClassScope(className).
				getSymbols().get(fieldName);
		if (entry == null)
			throw new RuntimeException("no such field : " + fieldName +
					" in class " + className);
		if (entry.isIs_method())
			throw new RuntimeException(fieldName + " is method,not field");
		return entry.getType();
	}

	@Override
	public IR_EXP_BINOP IRGenerator() {
		SymbolEntry symbolEntryField = table.find_symbol(fieldName);
		//TODO if symbolEntryField is not a field, throw an error.
		int offset = symbolEntryField.offset * (-4);
		return new IR_EXP_BINOP(BIN_OP.PLUS, var.IRGenerator(), new IR_EXP_LITERAL(new IR_CONST(offset)));
	}
}