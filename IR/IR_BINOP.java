package IR;

public class IR_BINOP extends IR_EXP {
	public String operation;
	public IR_EXP left;
	public IR_EXP right;
	public IR_BINOP(String operation, IR_EXP left, IR_EXP right) {
		super();
		this.operation = operation;
		this.left = left;
		this.right = right;
	}

}
