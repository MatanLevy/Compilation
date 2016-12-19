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

}
