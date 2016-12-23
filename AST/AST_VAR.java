package AST;

public abstract class AST_VAR extends AST_Node
{
	public AST_TYPE type;
	public abstract void calcType();
}