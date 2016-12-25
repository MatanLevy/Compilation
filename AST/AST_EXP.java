package AST;

public abstract class AST_EXP extends AST_Node
{
	public AST_TYPE type;
	public boolean typeUptoDate = false;
	public abstract AST_TYPE calcType(SymbolTable table);
}