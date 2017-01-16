package AST;

import IR.IR_EXP;
import IR.IR_EXP_BINOP;
import IR.IR_Node;
import IR.IR_STMT;
import IR.IR_STMT_MOVE;
import IR.IR_TEMP;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_EXP exp;
	public AST_VAR var;

	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}
	
	public void print() {
		System.out.println("assign stmt : ");
		exp.print();
		var.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {

		AST_TYPE leftSide = var.calcType(table, false);
		AST_TYPE rightSide = exp.calcType(table);
		if (!SemanticChecker.isBaseClassOf(leftSide.getName(), 
				rightSide.getName()))
			throw new RuntimeException("Incompitable types for assign");
		return true;
	}
	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IR_STMT createIR() {
		IR_EXP_BINOP dst = var.createIR();
		IR_EXP src = exp.createIR();
		return new IR_STMT_MOVE(dst, src);
	}


}