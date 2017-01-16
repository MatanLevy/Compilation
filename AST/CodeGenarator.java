package AST;
import IR.*;
public class CodeGenarator {

	
	
	
	/**
	 * address(heap address) of this pointer at this moment
	 * (not sure yet if should be integer or something else
	 */
	public TEMP thisAddress;
	
	
	/**
	 * 
	 * @param labelName
	 * @return new asm-code for creating label
	 */
	public String LabelGenerate(String labelName) {
		String label  = new LABEL(labelName).labelString;
		return label + " : ";
	}
	public String TempVariableGenerate() {
		return new TEMP().name;
	}
	
	
	
	/*
	 * Methods that print the commands
	 */
	
	
	//Adds a register and a sign-extended immediate value and stores the result in a register
	public static void printADDICommand(String rs, String rt, int immed){
		System.out.format("%s %s %s %d ", MIPS_COMMANDS.ADDI, rt, rs, immed);
	
	}
	//Adds two registers and stores the result in a register
	public static void printADDCommand(String rs, String rt, String rd){
		System.out.format("%s %s %s %d ", MIPS_COMMANDS.ADD, rs, rt, rd);
	
	}
	
	//The li pseudo instruction loads an immediate value into a register.
	public static void printLICommand(String rt, int immed) {
		System.out.format("%s %s %d ", MIPS_COMMANDS.LI, rt, immed);

	}
	//Load Address (la)
	public static void printLACommand(String rt, String address) {
		System.out.format("%s %s %s ", MIPS_COMMANDS.LA, rt, address);
	}
	
	
}
