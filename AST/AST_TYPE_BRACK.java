package AST;

public class AST_TYPE_BRACK extends AST_TYPE {

	private AST_TYPE type;

	public AST_TYPE_BRACK(AST_TYPE t) {
		this.type = t;
	}
	
	public void print() {
		System.out.println("type brack : ");
		type.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
