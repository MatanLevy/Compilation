package AST;

public class AST_TYPE_ARR extends AST_TYPE {

	private AST_RAW_TYPE type;

	public AST_TYPE_ARR(AST_RAW_TYPE r) {
		this.type = r;
	}
	
	public void print() {
		System.out.println("type arr : ");
		type.print();
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
