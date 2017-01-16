package AST;

public class AST_TYPE_STRING extends AST_RAW_TYPE {

	public AST_TYPE_STRING() {
		description = "string";
	}

	@Override
	public String getName() {
		return "string";
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
