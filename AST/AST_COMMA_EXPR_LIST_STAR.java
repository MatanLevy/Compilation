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
	
	public void print() {
		System.out.println("comma exp list star :");
		for (AST_EXP exp : expList) {
			exp.print();
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
