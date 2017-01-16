package AST;

import IR.IR_EXP_BINOP;
import IR.TEMP;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_EXP var;
	public AST_EXP subscript;
	
	public AST_VAR_SUBSCRIPT(AST_EXP e1, AST_EXP e2) {
		var = e1;
		subscript = e2;
	}
	
	public void print() {
		System.out.println("var subscript : ");
		var.print();
		subscript.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize) {
		AST_TYPE subtype = (subscript.typeUptoDate) ? subscript.type : 
			subscript.calcType(table);
		if (!(subtype instanceof AST_TYPE_INT))
			throw new RuntimeException("array index is not integer");
		AST_TYPE arrtype = (var.typeUptoDate) ? var.type : var.calcType(table);
		if (!(arrtype instanceof AST_TYPE_BRACK))
			throw new RuntimeException("cant invoke [ ] on raw type");
		return ((AST_TYPE_BRACK)arrtype).getType();
	}

	@Override
	public IR_EXP_BINOP IRGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAdress(SymbolTable symbol,CodeGenartor genrator,String fileName) {
		return null;
	}
}