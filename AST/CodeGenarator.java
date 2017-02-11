package AST;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import IR.*;
import com.sun.org.apache.bcel.internal.classfile.Code;

public class CodeGenarator {


    public static LABEL mainLabel = new LABEL("main");
    /**
     * map method name to it's label
     */

    public static Map<String, String> methodNameToLabelMap = new HashMap<String, String>();


    /**
     * map string to it's STRING_LABEL
     */
    public static Map<String, STRING_LABEL> stringToStringLabelMap = new HashMap<String, STRING_LABEL>();

    /**
     * Linked list of frames, when we are inside function it's new frame.
     * When we finish method body, we remove the frame.
     */
    public static LinkedList<Frame> framedLinkedList = new LinkedList<Frame>();


    /**
     * current class
     */
    public static String currentClass;

    /**
     * address(heap address) of this pointer at this moment
     * (not sure yet if should be integer or something else
     */
    public TEMP thisAddress;

    /**
     * this is the number of memory sp should go back in return.
     * This memory used to prepare arguments, in back from method we don't need this memory.
     */

    public static int numberOfMemoryStackPtrShouldOverrideInReturn = 0;

    /**
     * exit label for runtime error such as divison by zero or array outofbounds
     */
    public LABEL exitLabel = new LABEL("EXIT");

    /**
     * Add methodName and label as a pair in the map
     *
     * @param methodName
     * @param label
     */
    public void insertMethodNameAndLabelToMap(String methodName, String label) {
        methodNameToLabelMap.put(methodName, label);
    }

    /**
     * @param methodName
     * @return label of method or null if the methodName isn't in the map
     */

    public String getLabelOfMethod(String methodName) {
        return methodNameToLabelMap.get(methodName);
    }


    public static void addPairToArgumentToOffsetMap(String arg, int offset) {
        if (framedLinkedList.getFirst() != null) framedLinkedList.getFirst().addPairToArgumentToOffsetMap(arg, offset);
    }

    /**
     * return -1 if there is no argument with such name.
     *
     * @param arg
     * @return
     */
    public static int getOffsetOfArgument(String arg) {

        return (framedLinkedList.getFirst() == null) ? -1 : framedLinkedList.getFirst().getOffsetOfArgument(arg);

    }


    /**
     * @param labelName
     * @return new asm-code for creating label
     */
    public String LabelGenerate(String labelName, String className) {
        String label = new LABEL(labelName, 0, className).labelString;
        return label + " : ";
    }

    static public void printLabel(String label) {
        System.out.format("%s%n", label);

    }

    public String TempVariableGenerate() {
        return new TEMP().name;
    }

    public static int getOffset() {
        return (framedLinkedList.getFirst() == null) ? -1 : framedLinkedList.getFirst().getOffset();
    }

    public static void changeOffset(int size) {
        if (framedLinkedList.getFirst() != null) framedLinkedList.getFirst().changeOffset(size);
    }

    public static void createFrame() {
        Frame frame = new Frame();
        framedLinkedList.add(frame);
    }

    public static void removeFrame() {
        framedLinkedList.removeFirst();
    }

    /**
     * for array we allocate the $(size+1) on heap.so the first value in the heap will be the size
     * of the array
     *
     * @param addressOfSize
     * @return
     */
    public TEMP ArrayAlloc(TEMP addressOfSize) {
        //cant allocating less than zero,if so we exit
        TEMP zero = new TEMP();
        CodeGenarator.printLICommand(zero.name, 0);
        CodeGenarator.printSETCommand(MIPS_COMMANDS.BLT, addressOfSize.name,
                zero.name, exitLabel.labelString);

        TEMP result = new TEMP();
        CodeGenarator.printADDICommand(MIPS_COMMANDS.A0, addressOfSize.name, 0);
        CodeGenarator.printADDICommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, 1); //one more for size
        CodeGenarator.printADDCommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, MIPS_COMMANDS.A0);
        CodeGenarator.printADDCommand(MIPS_COMMANDS.A0, MIPS_COMMANDS.A0, MIPS_COMMANDS.A0);//MULT BY 4

        CodeGenarator.printLICommand(MIPS_COMMANDS.V0, MIPS_COMMANDS.alloc); //finally alloc 4*(size+1)
        CodeGenarator.printSyscallCommand();
        CodeGenarator.printADDICommand(result.name, MIPS_COMMANDS.V0, 0);
        CodeGenarator.printSWCommand(addressOfSize.name, result.name, 0); //putting in the first place size of array

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

        //init all fields to zero
        TEMP zero = new TEMP();
        CodeGenarator.printLICommand(zero.name, 0);
        for (int i = 4; i < size; i += 4) {
            CodeGenarator.printSWCommand(zero.name, result.name, i);
        }
        return result;
    }

    /**
     * in case of run time errors(such as NullPointer,DivideByZero,ArrayOutOfBounds)
     * we created exit label that exit the program immediately
     */
    public void printExit() {
        printLBLCommand(exitLabel.labelString);
        TEMP r1 = new TEMP();
        printLICommand(r1.name, 666);
        printInteger(r1.name);
        printLICommand(MIPS_COMMANDS.V0, MIPS_COMMANDS.exit);
        printSyscallCommand();
    }

    /**
     * can only use for branch command.at this case,cmd is branch
     * result and r1 -> r1 and r2
     * r2 -> label to jump
     *
     * @param cmd could be sgt,sge,slt,sle,sne,seq
     * @param r1
     * @param r2
     */
    public static void printSETCommand(String cmd, String result, String r1, String r2) {
        System.out.format("\t%s %s, %s, %s%n", cmd, result, r1, r2);
    }

    public static void printLBLCommand(String labelString) {
        System.out.format("%s : %n", labelString);

    }

    public static void printSyscallCommand() {
        System.out.format("\t%s%n", MIPS_COMMANDS.SYSCALL);
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
    public static void printDIVCommand(String r1, String r2) {
        System.out.format("\t%s %s %s%n", MIPS_COMMANDS.DIV, r1, r2);
    }

    /**
     * mul r1,r2
     *
     * @param result
     * @param r1
     * @param r2
     */
    //Multiplies $s by $t and stores the result in $LO.
    public static void printMULCommand(String result, String r1, String r2) {
        System.out.format("\t%s %s, %s, %s%n", MIPS_COMMANDS.MUL, result, r1, r2);
    }

    /**
     * mflo r
     *
     * @param r
     */
    //The contents of register LO are moved to the specified register.
	/*public static void printMFLOCommand(String r) {
		System.out.format("\t%s %s%n",MIPS_COMMANDS.MFLO,r);
	}*/

    //Adds a register and a sign-extended immediate value and stores the result in a register
    public static void printADDICommand(String rs, String rt, int immed) {
        System.out.format("\t%s %s, %s, %d%n", MIPS_COMMANDS.ADDI, rs, rt, immed);

    }

    //Adds two registers and stores the result in a register
    public static void printADDCommand(String rs, String rt, String rd) {
        System.out.format("\t%s %s, %s, %s%n", MIPS_COMMANDS.ADD, rs, rt, rd);

    }

    //The li pseudo instruction loads an immediate value into a register.
    public static void printLICommand(String rt, int immed) {
        System.out.format("\t%s %s,%d%n", MIPS_COMMANDS.LI, rt, immed);

    }

    //Load Address (la)
    public static void printLACommand(String rt, String address) {
        System.out.format("\t%s %s, %s%n", MIPS_COMMANDS.LA, rt, address);
    }

    // A word is loaded into a register from the specified address.
    public static void printLWCommand(String rt, String rs, int offset) {
        System.out.format("\t%s %s, %d(%s)%n", MIPS_COMMANDS.LW, rt, offset, rs);
    }

    // The contents of $t is stored at the specified address.
    public static void printSWCommand(String rt, String rs, int offset) {
        System.out.format("\t%s %s, %d(%s)%n", MIPS_COMMANDS.SW, rt, offset, rs);
    }

    // j Jump to an address
    public static void printJCommand(String offset) {
        System.out.format("\t%s %s%n", MIPS_COMMANDS.J, offset);
    }

    // jr Jump to an address stored in a register
    public static void printJRCommand(String rt) {
        System.out.format("\t%s %s%n", MIPS_COMMANDS.JR, rt);
    }

    //  Branches if the quantities of two registers are equal.
    public static void printBLECommand(/*String rt, String rs, int offset*/) {
        //System.out.format("%s %s, %d(%s)", MIPS_COMMANDS.SW, rt, offset, rs);
    }

    // Branches if the quantities of two registers are NOT equal
    public static void printBGTCommand(/*String rt, String rs, int offset*/) {
        //System.out.format("%s %s, %d(%s)", MIPS_COMMANDS.SW, rt, offset, rs);
    }

    public static void printBEQCommand(String r1, String r2, String label) {
        System.out.format("\t%s %s, %s, %s%n", MIPS_COMMANDS.BEQ, r1, r2, label);
    }

    public static void printJALCommand(String label) {
        System.out.format("\t%s %s%n", MIPS_COMMANDS.JAL, label);
    }

    public static void printJALRCommand(String register) {
        System.out.format("\t%s %s%n", MIPS_COMMANDS.JALR, register);
    }


    public static void printBNQCommand(String r1, String r2, String label) {
        System.out.format("\t%s %s, %s, %s%n", MIPS_COMMANDS.BNE, r1, r2, label);
    }

    public static void printJUMPCommand(String label) {
        System.out.format("\t%s %s%n", MIPS_COMMANDS.J, label);
    }

    //Subtracts two registers and stores the result in a register
    public static void printSUBCommand(String r1, String r2, String r3) {
        System.out.format("\t%s %s, %s, %s%n", MIPS_COMMANDS.SUB, r1, r2, r3);
    }

    /**
     * Example:
     * li Temp_13,4
     * sub Temp_12,$sp,Temp_13
     * addi $sp,Temp_12,0
     *
     * @param sizeOfMemoryToAllocate
     */
    public static void allocateMemory(int sizeOfMemoryToAllocate, boolean changeOffset) {
        printADDICommand(MIPS_COMMANDS.STACK_PTR, MIPS_COMMANDS.STACK_PTR, sizeOfMemoryToAllocate * -1);


        if (changeOffset) {
            //change the offset of the frame ptr
            changeOffset(sizeOfMemoryToAllocate);
        }
    }


    /**
     * if inside a function, it's use argument in given offset,
     * it's should print the following lines and return the temp it's saved in it the argument.
     * Example:
     * li Temp_26,8
     * add Temp_25,$fp,Temp_26
     * lw Temp_24,0(Temp_25)
     *
     * @param offset
     * @return temp with the wanted argument
     */
    public static TEMP printAndGetArgumentInsideMethod(int offset) {
        TEMP offsetTemp = new TEMP();
        printLICommand(offsetTemp.name, offset);
        TEMP addressArgumentTEMP = new TEMP();
        printADDCommand(addressArgumentTEMP.name, MIPS_COMMANDS.FRAME_PTR, offsetTemp.name);
        //TEMP argumentTEMP = new TEMP();
        //printLWCommand(argumentTEMP.name, addressArgumentTEMP.name, 0);

        return addressArgumentTEMP;
    }

    /**
     * This function print all it's need to saving an argument that is int on the stack.
     * Example:
     * li Temp_17,-4
     * add Temp_16,$fp,Temp_17
     * li Temp_18,1
     * sw Temp_18,0(Temp_16)
     *
     * @param offset
     */
    public static void printAndPrepareArgumentBeforeCall(int offset, int valueOfArgument) {
        TEMP offsetTemp = new TEMP();
        printLICommand(offsetTemp.name, offset);
        TEMP argumentAddressTemp = new TEMP();
        printADDCommand(argumentAddressTemp.name, MIPS_COMMANDS.FRAME_PTR, offsetTemp.name);
        TEMP valueOfArgumentTemp = new TEMP();
        printLICommand(valueOfArgumentTemp.name, valueOfArgument);
        printSWCommand(valueOfArgumentTemp.name, argumentAddressTemp.name, 0);


    }

    public static void printAndPrepareArgumentBeforeCall(int offset, TEMP valueOfArgument) {
        TEMP offsetTemp = new TEMP();
        printLICommand(offsetTemp.name, offset);
        TEMP argumentAddressTemp = new TEMP();
        printADDCommand(argumentAddressTemp.name, MIPS_COMMANDS.FRAME_PTR, offsetTemp.name);
        //TEMP valueOfArgumentTemp = new TEMP();
        //printLICommand(valueOfArgumentTemp.name, valueOfArgument);
        printSWCommand(valueOfArgument.name, argumentAddressTemp.name, 0);


    }


    public static void printInteger(String r1) {
        printADDICommand(MIPS_COMMANDS.A0, r1, 0);
        printLICommand(MIPS_COMMANDS.V0, 1);
        printSyscallCommand();
    }

    public static TEMP printAccsessToVFTable(VFTable vftable, int offset) {
        TEMP vftableAddress = new TEMP();
        printLACommand(vftableAddress.name, vftable.name);
        TEMP offsetTemp = new TEMP();
        printLICommand(offsetTemp.name, offset);
        TEMP wantedMethodAddressInVF = new TEMP();
        printADDCommand(wantedMethodAddressInVF.name, vftableAddress.name, offsetTemp.name);
        TEMP wantedMethodAddress = new TEMP();
        printLWCommand(wantedMethodAddress.name, wantedMethodAddressInVF.name, 0);
        return wantedMethodAddress;

    }

    public static TEMP printSWInFpPlusOffset(TEMP rvalue) {
        int varOffset = CodeGenarator.getOffset();
        TEMP lvalue = new TEMP();
        CodeGenarator.printADDICommand(lvalue.name, MIPS_COMMANDS.FRAME_PTR, varOffset);
        CodeGenarator.printSWCommand(rvalue.name, lvalue.name, 0);

        return lvalue;
    }

    public static void printEpilogInReturn() {
        CodeGenarator.printLWCommand(MIPS_COMMANDS.RA,MIPS_COMMANDS.FRAME_PTR,8); //retrive fm
        CodeGenarator.printADDICommand(MIPS_COMMANDS.STACK_PTR, MIPS_COMMANDS.FRAME_PTR, numberOfMemoryStackPtrShouldOverrideInReturn);
        CodeGenarator.printLWCommand(MIPS_COMMANDS.FRAME_PTR, MIPS_COMMANDS.FRAME_PTR, 4); //retrive fm
        CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
    }


} 
