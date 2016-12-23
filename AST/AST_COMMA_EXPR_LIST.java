package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_COMMA_EXPR_LIST extends AST_Node {
	AST_EXP exp;
	AST_COMMA_EXPR_LIST_STAR list;
	public AST_COMMA_EXPR_LIST(AST_EXP e, AST_COMMA_EXPR_LIST_STAR l) {
		list = l;
		exp = e;
	}
	public AST_COMMA_EXPR_LIST() {
		exp = null;
		list = null;
	}
	
	List<AST_TYPE> getListOfTypes() {
		if (exp == null) {
			return new ArrayList<AST_TYPE>();
		}
		List<AST_TYPE> listType = new ArrayList<>();
		listType.add(exp.type);
		listType.addAll(list.getListOfTypes());
		return listType;
	}
	
	public void print() {
		System.out.println("comma exp list : ");
		if ( exp != null) exp.print(); else System.out.println("no exp");
		if (list != null) list.print(); else System.out.println("no list");
	}

}
