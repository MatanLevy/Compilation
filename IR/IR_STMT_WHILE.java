package IR;

public class IR_STMT_WHILE extends IR_STMT {
	public IR_EXP cond;
	public IR_LABEL label;
	public IR_STMT body;
	
	public IR_STMT_WHILE(IR_LABEL label,IR_EXP cond, IR_STMT body) {
		this.cond = cond;
		this.body = body;
		this.label = label;
	}
}
