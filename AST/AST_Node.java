package AST;

import IR.IR_Node;

public abstract class AST_Node
{
	
	public abstract boolean checkSemantic(SymbolTable table);
	public abstract void print();
	public abstract String getName();

}