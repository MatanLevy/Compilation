package AST;

public class STRING_LABEL {
	public String str;
	public String str_label;
	static int counter = 0;


	public STRING_LABEL(String str) {
		this.str_label = "string_" + Integer.toString(counter);
		this.str = str;
		counter++;
	}
	
	public void printStringLabel () {
		System.out.println(str_label + ": .asciiz " + str + "/n");
	}
}
