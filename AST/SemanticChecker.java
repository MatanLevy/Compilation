package AST;

import java.util.ArrayList;
import java.util.List;

public class SemanticChecker {

	private static AST_PROGRAM program;

	public static AST_PROGRAM getProgram() {
		return program;
	}

	public static void setProgram(AST_PROGRAM program) {
		SemanticChecker.program = program;
	}

	public static AST_CLASSDECL getClass(String className) {
		List<AST_CLASSDECL> classList = program.getClassDeclList().getListOfClassDecl();
		for (AST_CLASSDECL classDecl : classList) {
			if (classDecl.classId.equals(className)) {
				return classDecl;
			}
		}
		return null;
	}

	public static boolean isFieldInClass(String fieldName,String className) {
		AST_CLASSDECL decl = SemanticChecker.getClass(className);
		for (AST_FIELD field : decl.fm_list.field_list)
			if (field._id.equals(fieldName))
				return true;
		return false;
	}



	public static List<AST_EXP> generateExpList(AST_COMMA_EXPR_LIST explist) {
		List<AST_EXP> list = new ArrayList<>();
		if (explist.exp == null)
			return list;
		list.add(explist.exp);
		list.addAll(explist.list.expList);
		return list;
	}

	public static boolean isBaseClassOf(String base, String derived) {
		//System.out.println(base + "   " + derived);
		if (base.equals(derived))
			return true;
		if (derived.equals("NULL")) 
			return true;
		AST_CLASSDECL deriveClass = SemanticChecker.getClass(derived);
		AST_CLASSDECL baseClass = SemanticChecker.getClass(base);
		if (deriveClass == null || baseClass == null || deriveClass.baseId == null)
			return false;
		AST_CLASSDECL parentClass = deriveClass;
		while (true) {
			parentClass = SemanticChecker.getClass(parentClass.baseId);
			if (parentClass == null)
				break;
			if (parentClass.classId.equals(base))
				return true;
		}
		return false;
	}

	public static boolean isTypeDefinedAlready(SymbolTable table, AST_TYPE type) {
		String fieldClassName=null;
		if (type instanceof AST_TYPE_CLASS) {
			fieldClassName = type.getName();
		}
		else if (type instanceof AST_TYPE_BRACK) {
			AST_RAW_TYPE raw = ((AST_TYPE_BRACK)type).getRawType();
			if (raw instanceof AST_TYPE_CLASS)
				fieldClassName = raw.getName();
		}
		
		if (table.get_currentClass().equals(fieldClassName))
			return true;
		
		
		if ( (fieldClassName != null) && !table.check_classScopes(fieldClassName))
			throw new RuntimeException("class " + fieldClassName + 
					" has not been defined yet");
		return true;
		

	}

}
