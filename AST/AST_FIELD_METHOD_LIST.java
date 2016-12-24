package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_FIELD_METHOD_LIST extends AST_Node {

	
	public List<AST_FIELD> field_list;
	public List<AST_METHOD> method_list;
	
	
	public AST_FIELD_METHOD_LIST() {
		field_list = new ArrayList<>();
		method_list = new ArrayList<>();
	}
	public void addField(AST_FIELD f) {
		field_list.add(f);
	}

	public void addMethod(AST_METHOD m) {
		method_list.add(m);
	}
	public void print() {
		System.out.println("field method list : ");
		for (AST_FIELD field : field_list) {
			field.print();
		}
		for (AST_METHOD method : method_list) {
			method.print();
		}
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		for (AST_FIELD field : field_list) {
			//table.pushScope(false, null);
			table.insertASTNode(field);
			if (!field.checkSemantic(table))
				return false;
		}
		for (AST_METHOD method : method_list) {
			table.insertASTNode(method);
//			table.pushScope(false, null, method._id);
			if (!method.checkSemantic(table))
				return false;
			table.popScope();

		}
//		for (AST_FIELD field : field_list) {
//			if (!field.checkSemantic(table))
//				return false;
//		}
//		for (AST_METHOD method : method_list) {
//			if (!method.checkSemantic(table))
//				return false;
//		}
//		for (AST_FIELD field : field_list) {
//			table.popScope();
//		}
//		for (AST_METHOD method : method_list) {
//			table.popScope();
//		}
		return true;
	}

}
