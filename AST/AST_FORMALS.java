package AST;

public class AST_FORMALS extends AST_Node {

	
	public AST_TYPE type;
	public String _id;
	public AST_FORMALS_LIST f_list; 
	
	public AST_FORMALS(AST_TYPE t, String id, AST_FORMALS_LIST fl) {
		type = t;
		_id = id;  
		f_list = fl;
	}
	public AST_FORMALS() {
		type = null;
		_id = null;
		f_list = null;
	}
	
	public void print() {
		System.out.println("formals : ");
		if (type!= null ) type.print(); else System.out.println("no type");
		if (_id != null) System.out.println("id = "+_id); else System.out.println("no _id");
		if (f_list != null) f_list.print(); else System.out.println("no f_list");
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return _id;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (type == null)
			return true;
		boolean firstcheck = SemanticChecker.isTypeDefinedAlready(table, type);
		for (AST_TYPE type1 : f_list.type_list)
			if (!SemanticChecker.isTypeDefinedAlready(table, type1))
				return false;
		return firstcheck;
	}
	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
//		if (f_list!=null) {
//			int offset = f_list.getSize() + 1;
//			CodeGenarator.addPairToArgumentToOffsetMap(_id, offset);
//			f_list.mipsTranslate(table, assemblyFileName, genartor);
//		}
//		else {
//			CodeGenarator.addPairToArgumentToOffsetMap(_id, 1);
//		}
		int numberOfArguments = numberOfArgs();
		int offSet = 12;
		if (f_list != null) {
			for (int i = f_list.getSize() ; i > 0 ; i--) {
				CodeGenarator.addPairToArgumentToOffsetMap(f_list.formal_list.get(i-1),offSet);
				offSet += 4;
			}
		}
		CodeGenarator.addPairToArgumentToOffsetMap(_id,offSet);
	}

	public int numberOfArgs() {
		int numberOfArguments;
		if (f_list == null) {
			if (type == null) {
				numberOfArguments = 0;
			}
			else {
				numberOfArguments = 1;
			}
		}
		else {
			numberOfArguments = f_list.getSize()+1;
		}
		return numberOfArguments;
	}

}
