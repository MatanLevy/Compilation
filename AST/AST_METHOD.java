package AST;

public class AST_METHOD extends AST_Node {

	
	public AST_TYPE type;
	public AST_FORMALS formals;
	public AST_STMT_LIST stmt_list;
	public String _id;
	
	public AST_METHOD(String id, AST_FORMALS f, AST_STMT_LIST l) {
		type = null;
		formals = f;
		stmt_list = l;
		_id = id;
	}

	public AST_METHOD(AST_TYPE t, String id, AST_FORMALS f, AST_STMT_LIST l) {
		type = t;
		formals = f;
		stmt_list = l;
		_id = id;
	}
	public void print() {
		System.out.println("method : ");
		if (type != null) {
			type.print();
		}
		else {
			System.out.println("type is void");
		}
		System.out.println("id = "+_id);
		formals.print();
		stmt_list.print();
	}

	@Override
	public String getName() {
		return _id;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (!SemanticChecker.isTypeDefinedAlready(table, type))
			return false;
		if (!formals.checkSemantic(table))
			return false;
		if (!stmt_list.checkSemantic(table))
			return false;
		return true;

	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		System.out.println(genartor.LabelGenerate(_id));
		formals.mipsTranslate(table, assemblyFileName, genartor);
		stmt_list.mipsTranslate(table, assemblyFileName, genartor);
		
		//TODO here : if void method ends with no return what should we do?
		
	}

}
