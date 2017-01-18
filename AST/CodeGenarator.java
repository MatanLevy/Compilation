package AST;
import java.util.HashMap;
import java.util.Map;

import IR.*;
public class CodeGenarator {
	
	/**
	 * map method name to it's label
	 */

	public static Map <String, String> methodNameToLabelMap = new HashMap<String, String> ();
	
	
	/**
	 * address(heap address) of this pointer at this moment
	 * (not sure yet if should be integer or something else
	 */
	public TEMP thisAddress;
	
	/**
	 * exit label for runtime error such as divison by zero or array outofbounds
	 */
	public LABEL exitLabel = new LABEL("EXIT");
	/**
	 * Add methodName and label as a pair in the map
	 * @param methodName
	 * @param label
	 */
	public void insertMethodNameAndLabelToMap(String methodName, String label) {
		methodNameToLabelMap.put(methodName, label);
	}
	/**
	 * 
	 * @param methodName
	 * @return label of method or null if the methodName isn't in the map
	 */
	
	public String getLabelOfMethod (String methodName) {
		return methodNameToLabelMap.get(methodName);
	}
	
	
	/**
	 * 
	 * @param labelName
	 * @return new asm-code for creating label
	 */
	public String LabelGenerate(String labelName) {
		String label  = new LABEL(labelName).labelString;
		return label + " : ";
	}
	
	static public void printLabel (String label) {
		System.out.format("%s %n",label);

	}
	public String TempVariableGenerate() {
		return new TEMP().name;
	}
	/**
	 * for array we allocate the $(size+1) on heap.so the first value in the heap will be the size
	 * of the array
	 * @param addressOfSize
	 * @return
	 */
	public TEMP ArrayAlloc(TEMP addressOfSize) {
		//cant allocating less than zero,if so we exit
		CodeGenarator.printSETCommand(MIPS_COMMANDS.BLT, addressOfSize.name, 
				MIPS_COMMANDS.ZERO, exitLabel.labelString);
		
		TEMP result = new TEMP();
		CodeGenarator.printADDICommand(MIPS_COMMANDS.A0, addressOfSize.name,0);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, 1); //one more for size
		CodeGenarator.printADDCommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, MIPS_COMMANDS.A0);
		CodeGenarator.printADDCommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, MIPS_COMMANDS.A0);//MULT BY 4
		
		CodeGenarator.printLICommand(MIPS_COMMANDS.V0, MIPS_COMMANDS.alloc); //finally alloc 4*(size+1)
		CodeGenarator.printSyscallCommand();
		CodeGenarator.printADDICommand(result.name, MIPS_COMMANDS.V0, 0);
		CodeGenarator.printADDICommand(result.name, addressOfSize.name, 0); //putting in the first place size of array
		
		//TODO here should come loop in mips that init all the elements to zero(or nullptr)
		
		return result;
	}
	/**
	 * ASSEMBLY CODE :
	 * li $a0 size
	 * li $v0 9 //9 is for allocation syscall
	 * syscall
	 * addi $result $v0 0 //moving the adress from v0 to the result
	 * 
	 * @param size for the Object on the heap
	 * @return temporary register with the address
	 */
	public TEMP AllocOnHeap(int size) {
		TEMP result = new TEMP();
		CodeGenarator.printLICommand(MIPS_COMMANDS.A0, size);
		CodeGenarator.printLICommand(MIPS_COMMANDS.V0, MIPS_COMMANDS.alloc);
		CodeGenarator.printSyscallCommand();
		CodeGenarator.printADDICommand(result.name, MIPS_COMMANDS.V0, 0);
		return result;
	}
	/**
	 * in case of run time errors(such as NullPointer,DivideByZero,ArrayOutOfBounds)
	 * we created exit label that exit the program immediately 
	 */
	public void printExit() {
		printLBLCommand(exitLabel.labelString);
		printLICommand(MIPS_COMMANDS.V0,MIPS_COMMANDS.exit);
		printSyscallCommand();
	}
	/**
	 * can only use for branch command.at this case,cmd is branch
	 * result and r1 -> r1 and r2
	 * r2 -> label to jump
	 * 
	 * 
	 * @param cmd could be sgt,sge,slt,sle,sne,seq
	 * @param r1
	 * @param r2
	 */
	public static void printSETCommand(String cmd,String result,String r1,String r2) {
		System.out.format("\t %s %s %s %s%n",cmd, result, r1, r2);
	}
	
	public static void printLBLCommand(String labelString) {
		System.out.format("%s : %n", labelString);
		
	}
	public static void printSyscallCommand() {
		System.out.format("\t %s%n",MIPS_COMMANDS.SYSCALL);
	}
	/*
	 * Methods that print the commands
	 */
	
	/**
	 * div r1 r2
	 * 
	 * @param r1
	 * @param r2
	 */
	public static void printDIVCommand(String r1, String r2){
		System.out.format("\t %s %s %s%n",MIPS_COMMANDS.DIV,r1,r2);
	}
	/**
	 * mult r1,r2
	 * 
	 * @param r1
	 * @param r2
	 */
	//Multiplies $s by $t and stores the result in $LO.
	public static void printMULTCommand(String r1, String r2){
		System.out.format("\t %s %s %s%n",MIPS_COMMANDS.MULT,r1,r2);
	}
	/**
	 * mflo r
	 * 
	 * @param r
	 */
	//The contents of register LO are moved to the specified register.
	public static void printMFLOCommand(String r) {
		System.out.format("\t %s %s%n",MIPS_COMMANDS.MFLO,r);
	}

	//Adds a register and a sign-extended immediate value and stores the result in a register
	public static void printADDICommand(String rs, String rt, int immed){
		System.out.format("\t %s %s %s %d %n", MIPS_COMMANDS.ADDI, rs, rt, immed);
	
	}
	//Adds two registers and stores the result in a register
	public static void printADDCommand(String rs, String rt, String rd){
		System.out.format("\t %s %s %s %s %n", MIPS_COMMANDS.ADD, rs, rt, rd);
	
	}
	
	//The li pseudo instruction loads an immediate value into a register.
	public static void printLICommand(String rt, int immed) {
		System.out.format("\t %s %s %d %n", MIPS_COMMANDS.LI, rt, immed);

	}
	//Load Address (la)
	public static void printLACommand(String rt, String address) {
		System.out.format("\t %s %s %s %n", MIPS_COMMANDS.LA, rt, address);
	}

	// A word is loaded into a register from the specified address.
	public static void printLWCommand(String rt, String rs, int offset) {
		System.out.format("\t %s %s, %d(%s) %n", MIPS_COMMANDS.LW, rt, offset, rs);
	}
	// The contents of $t is stored at the specified address.
	public static void printSWCommand(String rt, String rs, int offset) {
		System.out.format("\t %s %s, %d(%s) %n", MIPS_COMMANDS.SW, rt, offset, rs);
	}
	
	// j Jump to an address
	public static void printJCommand(String offset) {
		System.out.format("\t %s %s %n", MIPS_COMMANDS.J, offset);
	}
	// jr Jump to an address stored in a register
	public static void printJRCommand(String rt) {
		System.out.format("\t %s %s %n", MIPS_COMMANDS.JR, rt);
	}
	//  Branches if the quantities of two registers are equal.
	public static void printBLECommand(/*String rt, String rs, int offset*/) {
		//System.out.format("%s %s, %d(%s)", MIPS_COMMANDS.SW, rt, offset, rs);
	}
	// Branches if the quantities of two registers are NOT equal
	public static void printBGTCommand(/*String rt, String rs, int offset*/) {
		//System.out.format("%s %s, %d(%s)", MIPS_COMMANDS.SW, rt, offset, rs);
	}
	public static void printBEQCommand(String r1,String r2,String label) {
		System.out.format("\t %s %s %s %s %n",MIPS_COMMANDS.BEQ,r1,r2,label);
	}
<<<<<<< HEAD
	public static void printJALCommand(String label) {
		System.out.format("\t %s %s %n",MIPS_COMMANDS.JAL, label);
	}
=======
	public static void printBNQCommand(String r1,String r2,String label) {
		System.out.format("\t %s %s %s %s %n",MIPS_COMMANDS.BNE,r1,r2,label);
	}
	public static void printJUMPCommand(String label) {
		System.out.format("\t %s %s %n", MIPS_COMMANDS.J, label);
	}
	
>>>>>>> 77f0154e61b2642caf7f8ed76acc5efd7281e6d0

	
}
