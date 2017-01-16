package AST;

import IR.IR_EXP_BINOP;
import IR.TEMP;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP var;
	public String fieldName;
	
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
		AST_TYPE classType = (var.typeUptoDate)? var.type : var.calcType(table);
		if (!(classType instanceof AST_TYPE_CLASS)) 
			throw new RuntimeException("cannt access field of type not class");
		String className = classType.getName();
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
	public IR_EXP_BINOP createIR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAdress(SymbolTable table,CodeGenartor genrator,String fileName) {
		TEMP objectAdress = (var == null) ? genrator.thisAdress : var.calcAdrress();
		int offSetOfFieldInHeap = 0; //TODO change according to symbol table
		return null;
	}
}