package AST;


public class AST_BINOP extends AST_Node {
	int op;
	public AST_BINOP(int i) {
		this.op = i;
	}
	
	public void print() {
		System.out.println("binop : " + op);
	}
	
	public String getOp() {
		switch (op) {
		case 0 : return "PLUS";
		case 1 : return "MINUS";
		case 2 : return "TIMES";
		case 3 : return "DIVIDE";
		case 4 : return "GT";
		case 5 : return "GTOREQUAL";
		case 6 : return "ST";
		case 7 : return "STOREQUAL";
		case 8 : return "EQUAL";
		case 9 : return "NON EQUAL";
		default : return "SHOULD NEVER GET THIS";
		}
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return false;
	}



	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {		
	}

}
//PLUS 0
//MINUS	1
//TIMES 2
//DIVIDE 3
//GT 4	
//GTOREQUAL 5
//ST 6
//STOREQUAL	7
//EQUAL	8
//NOTEQUAL	9