package AST;

import IR.IR_EXP_BINOP;
import IR.TEMP;

public abstract class AST_VAR extends AST_Node
{
	public abstract AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize);
	public abstract IR_EXP_BINOP createIR();
	/**
	 * 
	 * @return temporary register containing the address of the lvalue 
	 */
	public abstract TEMP calcAdress(SymbolTable table,CodeGenartor genrator,
			String fileName);
	
	
}