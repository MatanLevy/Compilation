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

}
