package AST;

public class MIPS_COMMANDS {
	//frame pointer
	public static final String FRAME_PTR = "$fp";
	
	//Registers
	public static final String V0 = "$vo";
	public static final String A0 = "$a0";
	public static final String A1 = "$a1";
	public static final String A2 = "$a2";
	public static final String A3 = "$a3";
	public static final String ZERO = "$zero";
	public static final String RA = "$ra";
	
	//Possible values of v0 for syscall
	public static int exit = 10;
	public static int alloc = 9;
	
	//commands
	public static final String SEQ = "seq";
	public static final String SNE = "sne";
	public static final String SGE = "sge";
	public static final String SGT = "sgt";
	public static final String SLT = "slt";
	public static final String SLE = "sle";

	public static final String BEQ = "beq";
	public static final String BNE = "bne";
	public static final String SYSCALL = "syscall";
	public static final String MFLO = "mflo";
	public static final String MULT = "mult";
	public static final String ADDI = "addi";
	public static final String ADD = "add";
	public static final String SUB = "sub";
	public static final String LA = "la";
	public static final String LI = "li";
	public static final String SW = "sw";
	public static final String LW = "lw";
	public static final String J = "j";
	public static final String JR = "jr";
	public static final String JAL = "jal";
	public static final String BLE = "ble";
	public static final String BGT = "bgt";
	public static final String BLT = "blt";
	public static final String BGE = "bge";
	public static final String DIV = "div";
}
