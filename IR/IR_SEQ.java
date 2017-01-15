package IR;

public class IR_SEQ extends IR_Node {
	public IR_Node left;
	public IR_Node right;
	public IR_SEQ(IR_Node left, IR_Node right) {
		super();
		this.left = left;
		this.right = right;
	}

}
