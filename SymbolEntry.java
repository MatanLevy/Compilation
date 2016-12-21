import AST.AST_TYPE;

public class SymbolEntry {
	String id;
	AST_TYPE type;
	int scope; //This number represent the scope this id and type refer.
	
	public SymbolEntry(String id, AST_TYPE type, int scope) {
		this.id = id;
		this.type = type;
		this.scope = scope;
	}
	

}
