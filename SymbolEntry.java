import AST.AST_TYPE;
import AST.AST_TYPE_CLASS;

public class SymbolEntry {
	String id;
	AST_TYPE type;
	boolean is_class;
	
	public SymbolEntry(String id, AST_TYPE type) {
		this.id = id;
		this.type = type;
		if (type instanceof AST_TYPE_CLASS) {
			is_class = true;
		}
		else {
			is_class = false;
		}
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
