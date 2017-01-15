package IR;

public class IR_CJUMP extends IR_Node {
	public String operation;
	public IR_Node left;
	public IR_Node right;
	public IR_LABEL jumpToHereIfTrue;
	public IR_LABEL jumpToHereIfFalse;
	public IR_CJUMP(String operation, IR_Node left, IR_Node right, IR_LABEL jumpToHereIfTrue, IR_LABEL jumpToHereIfFalse) {
		super();
		this.operation = operation;
		this.left = left;
		this.right = right;
		this.jumpToHereIfTrue = jumpToHereIfTrue;
		this.jumpToHereIfFalse = jumpToHereIfFalse;
	}


}
