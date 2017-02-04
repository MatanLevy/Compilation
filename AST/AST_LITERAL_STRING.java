package AST;

public class AST_LITERAL_STRING extends AST_LITERAL {
	String str;
	public AST_LITERAL_STRING(String str1) {
		str = str1;
		this.type = new AST_TYPE_STRING();
	}
	
	public void print() {
		System.out.println("literatl :");
		System.out.println(str);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return str;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		STRING_LABEL label = new STRING_LABEL(str);
		CodeGenarator.stringToStringLabelMap.put(str, label);
		
	} 

}
