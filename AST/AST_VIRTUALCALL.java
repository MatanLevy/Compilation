package AST;

import java.util.List;

public class AST_VIRTUALCALL {	
	AST_EXP exp;
	String _id;
	AST_COMMA_EXPR_LIST exp_list;
	
	public AST_VIRTUALCALL(AST_EXP e, String id, AST_COMMA_EXPR_LIST l) {
		exp = e;
		_id = id;
		exp_list = l;
	}

	public AST_VIRTUALCALL(String id, AST_COMMA_EXPR_LIST l) {
		exp = null;
		_id = id;
		exp_list = l; 
	}
	
	public AST_TYPE calcType() {
		AST_METHOD method = assertMethod();
		if (method == null)  { //no such a method
			return null;
		}
		if (!matchMethod(method)) //method not matching
			return null;
		return method.type;
	}
	
	public AST_METHOD assertMethod() { //if method exist return her else null
		if (exp == null) {
			return null; //TO DO FIND THE CURRENT SCOPE
		}
		else {
			if (! (exp.type instanceof AST_TYPE_CLASS)) {
				return null;
			}
			AST_TYPE_CLASS type = (AST_TYPE_CLASS) exp.type;
			AST_CLASSDECL classDecl = SemanticChecker.getClass(type.description);
			if (classDecl == null) {
				return null;
			}
			return classDecl.fm_list.getMethod(_id);
		}
	}
	public boolean matchMethod(AST_METHOD method) {
		if (! _id.equals(method._id)) //checking same name
			return false;
		return matchArgumets(exp_list,method.formals);
	}
	
	public boolean matchArgumets(AST_COMMA_EXPR_LIST exp_list2, AST_FORMALS formals) {
		List<AST_TYPE> formalsList = formals.getListOfArgumentsType();
		List<AST_TYPE> dynamicList = exp_list2.getListOfTypes();
		if (formalsList.size() != dynamicList.size())
			return false;
		for (int i = 0 ; i < formalsList.size(); i++) {
			if (! SemanticChecker.canPassToFunction(formalsList.get(i), dynamicList.get(i)))
				return false;
		}
		return true;
	}

	public void print() {
		System.out.println("virtual call : ");
		System.out.println("id = " + _id);
		 if (exp != null ) exp.print(); else System.out.println("no exp");
		exp_list.print();
		
	}

}
