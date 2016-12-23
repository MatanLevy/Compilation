package AST;

public class AST_VAR_SIMPLE extends AST_VAR
{
	public String name;
	
	public AST_VAR_SIMPLE(String name)
	{
		this.name = name;
	}
	
	public void calcType() {
		//TO DO
	}
	
	public void print() {
		System.out.println("var simple : " + name);
		
	}
}