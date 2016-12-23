package AST;

public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR var;
	
	public AST_EXP_VAR(AST_VAR var)
	{
		this.var = var;
	}
	
	public void print() {
		System.out.println("exp var : ");
		var.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}