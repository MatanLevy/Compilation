package IR;

public class IR_EXP_BINOP extends IR_EXP {
	
	public BIN_OP binOperation;
	public IR_EXP left;
	public IR_EXP right;
	
	public IR_EXP_BINOP(BIN_OP operation, IR_EXP left, IR_EXP right) {
		this.binOperation = operation;
		this.left = left;
		this.right = right;
	}

}
