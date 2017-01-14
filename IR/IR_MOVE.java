package IR;

public class IR_MOVE extends IR_Node {
	public IR_Node dst;
	public IR_Node src;
	public IR_MOVE(IR_Node dst, IR_Node src) {
		super();
		this.dst = dst;
		this.src = src;
	}
	

}
