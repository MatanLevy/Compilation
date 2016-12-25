package AST;

import java.util.List;

public class SymbolEntry {
	String id;
	AST_TYPE type;
	boolean is_class;
	boolean is_method;
	List<AST_TYPE> listTypeForMethod;
	public boolean isIs_method() {
		return is_method;
	}

	public void setIs_method(boolean is_method) {
		this.is_method = is_method;
	}

	public List<AST_TYPE> getListTypeForMethod() {
		return listTypeForMethod;
	}

	public void setListTypeForMethod(List<AST_TYPE> listTypeForMethod) {
		this.listTypeForMethod = listTypeForMethod;
	}

	boolean initalize;
	String inWhichClassDefined; //this should be name of method or class.
	
	public SymbolEntry(String id, AST_TYPE type ,boolean initalize,
			boolean ismethod,List<AST_TYPE> listmethod) {
		this.id = id;
		this.type = type;
		this.initalize = initalize;
		if (type instanceof AST_TYPE_CLASS) {
			is_class = true;
			inWhichClassDefined = "";
		}
		else {
			is_class = false;
			inWhichClassDefined = null;
		}
		if (ismethod) {
			is_method = true;
			listTypeForMethod = listmethod;
		}
		else {
			is_method = false;
			listTypeForMethod = null;
		}
	}

	public String getInWhichClassDefined() {
		return inWhichClassDefined;
	}

	public void setInWhichClassDefined(String inWhichClassDefined) {
		this.inWhichClassDefined = inWhichClassDefined;
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
