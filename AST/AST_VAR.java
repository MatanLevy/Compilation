package AST;

public abstract class AST_VAR extends AST_Node
{
	public abstract AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize);
	
	
}