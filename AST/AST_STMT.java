package AST;

import IR.IR_STMT;

public abstract class AST_STMT extends AST_Node
{

	public abstract IR_STMT createIR();

}