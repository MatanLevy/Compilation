package IR;

public class IR_CALL extends IR_Node{
	
	public IR_LABEL calledMethodLable;
	public IR_EXP callerAddress;
	public IR_EXP_LIST args;
	
	public IR_CALL(IR_LABEL calledMethod, IR_EXP callerAdd, IR_EXP_LIST args) {
		this.calledMethodLable = calledMethod;
		this.callerAddress = callerAdd;
		this.args = args;
	}
}
