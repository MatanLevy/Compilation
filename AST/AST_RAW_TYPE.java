package AST;

public abstract class AST_RAW_TYPE extends AST_TYPE {
	
	protected String description;
	
	public void print() {
		System.out.println("raw type : ");
		System.out.println("desc = " + description);
	}

}
