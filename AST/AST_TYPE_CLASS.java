package AST;

public class AST_TYPE_CLASS extends AST_RAW_TYPE {
	String classId;

	public AST_TYPE_CLASS(String id) {
		description = id;
		classId = id;
	}

	@Override
	public String getName() {
		return classId;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

}
   