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
		
		return null;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		for (AST_CLASSDECL classdecl : class_decl_list) {
			table.insertASTNode(classdecl);
			//table.pushScope(true, classdecl.getName());
			if (!classdecl.checkSemantic(table))
				return false;
			table.popScope();
			table.initCounterOffsetWhenPopScopeOfClass();
		}
		return true;
	}
	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenartor genartor) {
		for (AST_CLASSDECL classdecl : class_decl_list) {
			table.insertASTNode(classdecl);
			classdecl.mipsTranslate(table, assemblyFileName, genartor);
			table.popScope();
			table.initCounterOffsetWhenPopScopeOfClass();
		}
		
	}
}
