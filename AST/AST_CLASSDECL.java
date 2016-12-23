package AST;

public class AST_CLASSDECL extends AST_Node{
	
	public String classId;
	public AST_FIELD_METHOD_LIST fm_list;
	public String baseId;
	public AST_TYPE type;
	
	public AST_CLASSDECL(String c1, AST_FIELD_METHOD_LIST fml) {
		baseId = null;
		classId = c1;
		fm_list = fml;
		type = new AST_TYPE_CLASS(classId);
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public AST_FIELD_METHOD_LIST getFm_list() {
		return fm_list;
	}

	public void setFm_list(AST_FIELD_METHOD_LIST fm_list) {
		this.fm_list = fm_list;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public AST_TYPE getType() {
		return type;
	}

	public void setType(AST_TYPE type) {
		this.type = type;
	}

	public AST_CLASSDECL(String c1, String c2, AST_FIELD_METHOD_LIST fml) {
		baseId = c2;
		classId = c1;
		fm_list = fml;
		type = new AST_TYPE_CLASS(classId);

	}  
	
	public void print() {
		System.out.println("classdecl : ");
		System.out.println("base class is " + baseId);
		System.out.println("class name is " + classId);
		fm_list.print();
		
	}

	@Override
	public String getName() {
		return classId;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (classId.equals(baseId))
			throw new RuntimeException("Cant extends from the same class : " + "class name : " +
		classId);
		//Here I want to check if this class extends from other class that hasnt
		//been defined yet and to throw an error.I dont understands how to use your
		//code please write here the syntax.
		return fm_list.checkSemantic(table);
	}

}
