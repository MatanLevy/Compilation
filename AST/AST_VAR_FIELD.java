package AST;
 
public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	/*public AST_VAR_FIELD(AST_VAR var,String fieldName)
	{
		this.var = var;
		this.fieldName = fieldName;
	}*/

	public AST_VAR_FIELD(AST_EXP e, String fieldName2) {
		var = e;
		fieldName = fieldName2;
	}
	
	public void print() {
		System.out.println("var field : ");
		var.print();
		System.out.println("field name = " + fieldName);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return fieldName;
	}
}