package AST;

import java.util.*;
import java.util.stream.Collectors;

public class VirtualTableManager {

    public static final String labelPrefix = "Label_0_";
    
    public static String mainLabel;

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
                String label = labelPrefix + actualClassName + "_" + functionName;
                if (functionName.equals("main"))
                	mainLabel = label;
                return label;
        
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
		//if class equal print, ignore it.
		if (/*!(className.equals("PRINT")) &&*/ getAllLabelsForClass(className).size() > 0) {
			System.out.format("\n\tVFTable_%s : .word ", className);
			List<String> allLabelsFromClass = getAllLabelsForClass(className);
			String lastLabel = getAllLabelsForClass(className).get(allLabelsFromClass.size()-1);
			allLabelsFromClass.remove(allLabelsFromClass.size()-1);
			allLabelsFromClass.stream().map(x -> x + ",").forEach(System.out::print);
			System.out.printf("%s", lastLabel);
			
		}
	}

    public static void printAllClasses() {
    	System.out.println(".data");
        SemanticChecker.getProgram().class_dec_list.class_decl_list.stream().map(x -> x.classId).
                forEach(VirtualTableManager::printAllFunctions);
        System.out.println("\n");
        
        //print strings
		STRING_LABEL.printStrings();

    }
    
	public static int getOffsetForFunction(String className, String functionName) {
		Set<String> listOfFunctionsInClassSet = getListOfActualFunctionsByName(className);
		List<String> listOfFunctionsInClassList = new ArrayList<String>(listOfFunctionsInClassSet);
		return listOfFunctionsInClassList.indexOf(functionName);

	}

}
