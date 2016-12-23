package AST;
 
public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP var;
	public String fieldName;
	
	public AST_VAR_FIELD(AST_EXP e, String fieldName2) {
		var = e;
		fieldName = fieldName2;
	}
	public AST_FIELD assertFieldExist() { //if field exist, return him else null
		if (! (var.type instanceof AST_TYPE_CLASS)) {
			return null;
		}
		AST_TYPE_CLASS type = (AST_TYPE_CLASS) var.type;
		AST_CLASSDECL classDecl = SemanticChecker.getClass(type.description);
		if (classDecl == null) {
			return null;
		}
		return classDecl.fm_list.getField(fieldName);
	}
	public void calcType() {
		AST_FIELD field = assertFieldExist();
		if (field != null) {
			type = field._type;
		}
		type = null;
	}
	
	public void print() {
		System.out.println("var field : ");
		var.print();
		System.out.println("field name = " + fieldName);
	}
}