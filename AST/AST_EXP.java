package AST;

import COUNTERS.TEMP;

public abstract class AST_EXP extends AST_Node
{
	public AST_TYPE type;
	public boolean typeUptoDate = false;
	public abstract AST_TYPE calcType(SymbolTable table);

	/**
	 * 
	 * @param fileName 
	 * @param genarator 
	 * @param table 
	 * @return
	 */
	public abstract TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName);
}