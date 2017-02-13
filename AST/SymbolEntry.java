package AST;

import java.util.List;

public class SymbolEntry {
	public boolean is_field;
	String id;
	AST_TYPE type;
	boolean is_class;
	boolean is_method;
	List<AST_TYPE> listTypeForMethod;
	int offset;
	boolean initalize;
	String inWhichClassDefined; //this should be name of method or class.
	
	//give offset to fields inside current class which help for the IR
	static int counterOffsetOfFieldsCurrentClass = 0;
	//give offset to local variables inside method
	static int counerOffsetOfLocalVarCurrentMethod = 0;
	//give offset to methods inside current class
	static int counterOffsetMethodsCurrentClass = 0;
	
	public SymbolEntry(String id, AST_TYPE type ,boolean initalize,
			boolean ismethod,List<AST_TYPE> listmethod) {
		this.id = id;
		this.type = type;
		this.initalize = initalize;
		this.is_field = false;
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
	
	public void setOffsetField () {
		offset = counterOffsetOfFieldsCurrentClass;
		counterOffsetOfFieldsCurrentClass++;
	}
	
	static public void initcounterOffsetOfFieldsCurrentClass() {
		counterOffsetOfFieldsCurrentClass = 0;
	}
	static public void updateOffsetField (int num) {
		counterOffsetOfFieldsCurrentClass = num;
	}
	
	public void setOffsetLocalVar () {
		offset = counerOffsetOfLocalVarCurrentMethod;
		counerOffsetOfLocalVarCurrentMethod++;
	}
	static public void initCounterOffsetOfLocalVarCurrentMethod() {
		counerOffsetOfLocalVarCurrentMethod = 0;
	}
	
	static public void decreaseCounerOffsetOfLocalVarCurrentMethodByNum(int toDecreaseBy) {
		counerOffsetOfLocalVarCurrentMethod -= (toDecreaseBy - 1);
		if (counerOffsetOfLocalVarCurrentMethod < 0) {
			throw new RuntimeException("Something isn't good with the offset counter of local vars inside method scope");
		}
	}
	
	public void setOffsetMethod () {
		offset = counterOffsetMethodsCurrentClass;
		counterOffsetMethodsCurrentClass++;
	}
	static public void initCounterOffsetMethodsCurrentClass() {
		counterOffsetMethodsCurrentClass = 0;
	}
	
	static public void updateOffsetMethod (int num) {
		counterOffsetMethodsCurrentClass = num;
	}
	
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
	
	public void setOffset (int offset) {
		this.offset = offset;
	}
	
	public void setIsField(boolean isField) {
		this.is_field = true;
	}


	

}
