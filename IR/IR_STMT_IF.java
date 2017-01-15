package IR;

public class IR_STMT_IF extends IR_STMT {
	public IR_EXP cond;
	public IR_STMT body;
	public IR_LABEL label;
	
	public IR_STMT_IF(IR_EXP cond, IR_STMT body,IR_LABEL label) {
		this.cond = cond;
		this.body = body;
		this.label = label;
	}
	

}
