package AST;

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

	
	
}	
