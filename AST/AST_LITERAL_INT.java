package AST;

public class AST_LITERAL_INT extends AST_LITERAL {
	int _i;
	public AST_LITERAL_INT(Integer i) {
		_i = i;
		this.type = new AST_TYPE_INT();
	}
	
	public void print() {
		System.out.println("literal : ");
		System.out.println(_i);
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
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		// TODO Auto-generated method stub
		
	}

}
