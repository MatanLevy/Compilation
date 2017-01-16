package AST;
import IR.*;
public class CodeGenartor {
	public static final String ADDI = "addi";
	public static final String FRAME_PTR = "$fp";
	
	
	
	/**
	 * address(heap address) of this pointer at this moment
	 * (not sure yet if should be integer or something else
	 */
	public TEMP thisAdress;
	
	
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
	
	
}
