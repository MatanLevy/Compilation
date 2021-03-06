package AST;

public class AST_FIELD extends AST_Node {
	
	public AST_TYPE _type;
	public String _id;
	public AST_COMMA_ID_LIST _comma_list;

	public AST_FIELD(AST_TYPE t, String id, AST_COMMA_ID_LIST l) {
		_type = t;
		_id = id;
		_comma_list = l;
	}
	
	public void print() {
		System.out.println("field : ");
		System.out.println("field name = " +  _id);
		_type.print();
		_comma_list.print();
	}

	@Override
	public String getName() {
		return _id;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return SemanticChecker.isTypeDefinedAlready(table, _type);
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		//Should we add are something???
		
	}

}
