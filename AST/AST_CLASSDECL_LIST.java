package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_CLASSDECL_LIST extends AST_Node {
	
	
	public List<AST_CLASSDECL> class_decl_list; 
	 
	public AST_CLASSDECL_LIST() { 
		class_decl_list = new ArrayList<>();
	}
	public void addClassDecl(AST_CLASSDECL cd) {
		class_decl_list.add(cd); 
	}
	public List<AST_CLASSDECL> getListOfClassDecl() {
		return class_decl_list;
	}
	
	public void print() {
		System.out.println("class decl list : ");
		for (AST_CLASSDECL classdecl : class_decl_list) {
			classdecl.print();
		}
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
