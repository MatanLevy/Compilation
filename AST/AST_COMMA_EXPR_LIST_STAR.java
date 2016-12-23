package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_COMMA_EXPR_LIST_STAR extends AST_Node {

	List<AST_EXP> expList;
	
	
	public AST_COMMA_EXPR_LIST_STAR() {
		expList = new ArrayList<>();
	}
	
	public void addExp(AST_EXP e) {
		expList.add(e);
	}
	
	List<AST_TYPE> getListOfTypes() {
		List<AST_TYPE> listTypes = new ArrayList<>();
		for (AST_EXP exp : expList) {
			listTypes.add(exp.type);
		}
		return listTypes;
	}
	
	public void print() {
		System.out.println("comma exp list star :");
		for (AST_EXP exp : expList) {
			exp.print();
		}
	}

}
