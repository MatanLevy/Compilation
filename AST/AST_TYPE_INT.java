package AST;

public class AST_TYPE_INT extends AST_RAW_TYPE {


	public AST_TYPE_INT() {
		description = "int";
	}

	@Override
	public String getName() {
		return "int";
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}
	

}
