package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_FORMALS_LIST {

	List<String> formal_list;
	List<AST_TYPE> type_list;
	
	public AST_FORMALS_LIST() {
		formal_list = new ArrayList<>();
		type_list = new ArrayList<>();
	}
	
	public void addFormal(AST_TYPE t, String id) {
		formal_list.add(id);
		type_list.add(t);
	}
	
	public void print() {
		System.out.println("formal list :");
		for (int i = 0; i < formal_list.size(); i++) {
			System.out.println("type = " + type_list.get(i));
			System.out.println("formal = " + formal_list.get(i));
		}
	}

}
