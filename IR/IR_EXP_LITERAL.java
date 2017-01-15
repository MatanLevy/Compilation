package IR;

import AST.AST_LITERAL;

public class IR_EXP_LITERAL extends IR_EXP {
	
	AST_LITERAL litreal;
	
	public IR_EXP_LITERAL(AST_LITERAL _literal) {
		litreal = _literal;
	}

}
