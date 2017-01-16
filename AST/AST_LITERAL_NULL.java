package AST;

public class AST_LITERAL_NULL extends AST_LITERAL {
	
	public AST_LITERAL_NULL() {
		this.type = new AST_TYPE_CLASS("NULL");
	}
	public void print() {
		System.out.println("literal :");
		System.out.println("null literal");
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
