package IR;

public class IR_MOVE extends IR_EXP {
	public IR_EXP dst;
	public IR_EXP src;
	public IR_MOVE(IR_EXP dst, IR_EXP src) {
		super();
		this.dst = dst;
		this.src = src;
	}
	

}
