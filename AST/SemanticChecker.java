package AST;
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
			if (className.equals(classDecl.classId)) {
				return classDecl;
			}
		}
		return null;
	}
	
	public static boolean isBaseClassOf(String base,String derived) {
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

}
