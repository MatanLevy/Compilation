package AST;

import IR.IR_EXP;
import IR.TEMP;

public abstract class AST_EXP extends AST_Node
{
	public AST_TYPE type;
	public boolean typeUptoDate = false;
	public abstract AST_TYPE calcType(SymbolTable table);

	/**
	 * 
	 * @return
	 */
	public abstract TEMP calcAdrress();

	public abstract IR_EXP IRGenerator();
}