package AST;

import IR.IR_EXP_BINOP;
import IR.TEMP;


public abstract class AST_VAR extends AST_Node
{
	public abstract AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize);

	/**
	 * 
	 * @return temporary register containing the address of the lvalue 
	 */
	public abstract TEMP calcAddress(SymbolTable table,CodeGenarator genrator,
			String fileName);

	public abstract IR_EXP_BINOP IRGenerator();

	
	
}	
