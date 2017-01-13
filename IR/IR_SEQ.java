package IR;

public class IR_SEQ extends IR_EXP {
	public IR_EXP left;
	public IR_EXP right;
	public IR_SEQ(IR_EXP left, IR_EXP right) {
		super();
		this.left = left;
		this.right = right;
	}

}
