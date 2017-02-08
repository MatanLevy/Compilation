package AST;

import java.util.*;
import java.util.stream.Collectors;

public class VirtualTableManager {

    public static final String labelPrefix = "Label_0_";

    public static Set<String> getListOfActualFunctionsByName(String className) {
        List<String> actualFunctions = new ArrayList<>();
        AST_CLASSDECL classDecl = SemanticChecker.getClass(className);
        if (classDecl.baseId != null) {
            actualFunctions.addAll(getListOfActualFunctionsByName(classDecl.baseId));
        }
        actualFunctions.addAll(classDecl.fm_list.method_list.stream().map(x->x._id).collect(Collectors.toList()));
        return new LinkedHashSet<>(actualFunctions);
    }

    public static String getLabelNameForFunction(String className,String functionName) {
        String actualClassName = className;
        while (actualClassName != null) {
            if (SemanticChecker.getClass(actualClassName).fm_list.method_list.stream().map(x -> x._id).
                    filter(x -> x.equals(functionName)).count() > 0) {
                return labelPrefix + actualClassName + "_" + functionName;
            }
            else {
                actualClassName = SemanticChecker.getClass(actualClassName).baseId;
            }
        }
        return null;
    }
    public static List<String> getAllLabelsForClass(String className) {
        return getListOfActualFunctionsByName(className).stream().map(x -> getLabelNameForFunction(className,x)).
                collect(Collectors.toList());
    }
    /*public static List<String> getListOfLabelNamePerFunction(String functionName) {
        return SemanticChecker.getProgram().class_dec_list.class_decl_list.stream().map(x -> x.classId)
                .map(x -> getLabelNameForFunction(x,functionName)).collect(Collectors.toList());
    }*/
    public static void printAllFunctions(String className) {
        System.out.format("%n%s functions : ",className);
        getAllLabelsForClass(className).stream().map(x -> x + " ").forEach(System.out :: print);
    }

    public static void printAllClasses() {
        SemanticChecker.getProgram().class_dec_list.class_decl_list.stream().map(x -> x.classId).
                forEach(VirtualTableManager::printAllFunctions);
    }
    
	public static int getOffsetForFunction(String className, String functionName) {
		Set<String> listOfFunctionsInClassSet = getListOfActualFunctionsByName(className);
		List<String> listOfFunctionsInClassList = new ArrayList<String>(listOfFunctionsInClassSet);
		return listOfFunctionsInClassList.indexOf(functionName);

	}

}
