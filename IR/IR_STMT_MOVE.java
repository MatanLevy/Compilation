package IR;

public class IR_STMT_MOVE extends IR_STMT {
	
	public IR_EXP dst;
	public IR_EXP src;
	
	public IR_STMT_MOVE(IR_EXP dst, IR_EXP src) {
		this.dst = dst;
		this.src = src;
	}
	

}
