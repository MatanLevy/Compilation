package AST;

import IR.IR_EXP_BINOP;

public abstract class AST_VAR extends AST_Node
{
	public abstract AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize);
	public abstract IR_EXP_BINOP createIR();
	
	
}