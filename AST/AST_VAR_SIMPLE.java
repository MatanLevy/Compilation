package AST;

import IR.IR_EXP_BINOP;
import IR.TEMP;

public class AST_VAR_SIMPLE extends AST_VAR
{
	public String name;
	
	public AST_VAR_SIMPLE(String name)
	{
		this.name = name;
	}
	
	public void print() {
		System.out.println("var simple : " + name);
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize) {
		if (table.find_symbol(name) == null) 
			throw new RuntimeException(name + " is not defined in this scope");
		if (!table.isSymbolInitalize(name) && needCheckInitialize)
			throw new RuntimeException(name + " is not initalized");
		if (!needCheckInitialize) { //it's false only if we get here from AST_STMT_ASSING
			table.find_symbol(name).setInitalize(true);
		}
		
		return table.getTable().get(name).getFirst().getType();
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
		int offSetVar = 0; // TODO change according to symbol table
		TEMP offSetAdress = new TEMP();
		System.out.format("%s %s %s %d ",CodeGenartor.ADDI,
				offSetAdress.name,CodeGenartor.FRAME_PTR,offSetVar);
		return offSetAdress;
	}
}