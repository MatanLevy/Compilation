package AST;

public class AST_EXP_NEW_CLASS extends AST_EXP {
	
	String _className;
	public AST_EXP_NEW_CLASS(String className) {
		this._className = className;
		this.type = new AST_TYPE_CLASS(className);
	}
	public void print() {
		System.out.println("exp new class : " + _className);
	}

}
