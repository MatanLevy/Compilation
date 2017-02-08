package AST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class STRING_LABEL {
	public String str;
	public String str_label;
	static int counter = 0;
	public static ArrayList<String> stringsList = new ArrayList<String>();
	public static HashMap<String, STRING_LABEL> stringToLabelMap = new HashMap <String,STRING_LABEL>();


	public STRING_LABEL(String str, boolean addToList) {
		if ((stringToLabelMap.get(str))==null) {
			this.str_label = "String_" + Integer.toString(counter);
			this.str = str;
			counter++;
			stringToLabelMap.put(str, this);
			if (addToList)
				addStringToStringList();
		}

	}
	
	public static STRING_LABEL getSTRING_LABEL(String str) {
		return stringToLabelMap.get(str);
	}

	public void addStringToStringList () {
		String string = str_label + ": .asciiz " + str + "\n";
		stringsList.add(string);
		//System.out.println(str_label + ": .asciiz " + str + "/n");
	}
	
	public static void printStrings() {
		System.out.println(".data\n");
		stringsList.stream().map(x -> "\t" + x + "\n").forEach(System.out::print);

		
	}
}
