package IR;

public class IR_BINOP extends IR_Node {
	public String operation;
	public IR_Node left;
	public IR_Node right;
	public IR_BINOP(String operation, IR_Node left, IR_Node right) {
		super();
		this.operation = operation;
		this.left = left;
		this.right = right;
	}

}
