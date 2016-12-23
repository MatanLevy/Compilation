package AST;
public class SymbolEntry {
	String id;
	AST_TYPE type;
	boolean is_class;
	boolean initalize;
	
	public SymbolEntry(String id, AST_TYPE type ,boolean initalize) {
		this.id = id;
		this.type = type;
		this.initalize = initalize;
		if (type instanceof AST_TYPE_CLASS) {
			is_class = true;
		}
		else {
			is_class = false;
		}
	}

	public boolean isInitalize() {
		return initalize;
	}

	public void setInitalize(boolean initalize) {
		this.initalize = initalize;
	}

	public boolean getIs_class() {
		return is_class;
	}

	public void setIs_class(boolean is_class) {
		this.is_class = is_class;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AST_TYPE getType() {
		return type;
	}

	public void setType(AST_TYPE type) {
		this.type = type;
	}


	

}
