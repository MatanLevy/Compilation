package AST;

public class AST_PROGRAM extends AST_Node{
	
	public AST_CLASSDECL_LIST class_dec_list;
	
	public AST_PROGRAM(AST_CLASSDECL_LIST cdl) {
		class_dec_list = cdl;
	}
	
	public AST_CLASSDECL_LIST getClassDeclList() {
		return class_dec_list;
	}
	
	
	
	public void print() {
		System.out.println("program : ");
		class_dec_list.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return class_dec_list.checkSemantic(table);
	}

}
