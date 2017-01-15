package AST;

import IR.IR_EXP;

public abstract class AST_EXP extends AST_Node
{
	public AST_TYPE type;
	public boolean typeUptoDate = false;
	public abstract AST_TYPE calcType(SymbolTable table);
	public abstract IR_EXP IRGenerator();
}