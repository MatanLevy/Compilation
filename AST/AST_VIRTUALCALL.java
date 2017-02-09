package AST;

import java.util.List;

import IR.TEMP;

public class AST_VIRTUALCALL extends AST_Node {
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

	public AST_TYPE calcType(SymbolTable table) {
		if ((exp != null) && !(exp.calcType(table) instanceof AST_TYPE_CLASS))
			throw new RuntimeException("can't invoke method on not a class elem");
		String className = (exp == null) ? table.get_currentClass() : exp.calcType(table).getName();
		SymbolEntry methodentry = (table.get_currentClass().equals(className)) ? table.find_symbol(_id)
				: table.getClassScope(className).getSymbols().get(_id);
		if (methodentry == null)
			throw new RuntimeException("no such method : " + _id + "in class " + className);
		if (!methodentry.isIs_method())
			throw new RuntimeException(_id + " exist but not a method");
		List<AST_TYPE> argumentList = methodentry.getListTypeForMethod();
		List<AST_EXP> expList = SemanticChecker.generateExpList(exp_list);
		int numberOfArguments = argumentList.size();
		int numberOfParamInCall = expList.size();
		if (numberOfArguments != numberOfParamInCall)
			throw new RuntimeException(
					"method " + _id + " expect " + numberOfArguments + " parametrs.called with " + numberOfParamInCall);
		for (int i = 0; i < numberOfArguments; i++) {
			if (expList.get(i).calcType(table) == null) // VOID
				throw new RuntimeException("argument number " + i + " can't be void");
			if (!SemanticChecker.isBaseClassOf(argumentList.get(i).getName(), expList.get(i).calcType(table).getName()))
				throw new RuntimeException("Error with argument number " + i + 1);
		}
		return methodentry.getType();
	}

	public void print() {
		System.out.println("virtual call : ");
		System.out.println("id = " + _id);
		if (exp != null)
			exp.print();
		else
			System.out.println("no exp");
		exp_list.print();

	}

	@Override
	public String getName() {
		return _id;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		calcType(table);
		return true;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		if (_id.equals("printInt") && exp != null && exp.calcType(table) instanceof AST_TYPE_CLASS) {
			AST_TYPE_CLASS type = (AST_TYPE_CLASS) (exp.calcType(table));
			String className = type.classId;
			if (className.equals("PRINT")) {
				if (exp_list.exp != null && exp_list.exp.calcType(table) instanceof AST_TYPE_INT) {
					TEMP number = exp_list.exp.calcAddress(table, genartor, assemblyFileName);
					CodeGenarator.printInteger(number.name);
					return;
				}
			}
		}
		//allocate memory for exp as argument.
		int sizeToAllocateForExpAsArgument = 4;
		CodeGenarator.allocateMemory(sizeToAllocateForExpAsArgument);
		TEMP temp = exp.calcAddress(table,genartor,assemblyFileName);
		CodeGenarator.printSWInFpPlusOffset(temp);
		//int offSet = 0;
		exp_list.mipsTranslate(table, assemblyFileName, genartor);
		String label = genartor.getLabelOfMethod(_id);
		CodeGenarator.printJALCommand(label);
	}

    public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		//TODO add here body
		return null;
    }
    // jal procedure # call procedure

}
