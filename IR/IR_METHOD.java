package IR;

public class IR_METHOD extends IR_Node {
	LABEL labelMethod;
	IR_Node body;
	IR_Node prologue;
	IR_Node epilogue;
	
	public IR_METHOD(LABEL labelMethod, IR_Node body, IR_Node prologue, IR_Node epilogue) {
		super();
		this.labelMethod = labelMethod;
		this.body = body;
		this.prologue = prologue;
		this.epilogue = epilogue;
	}

}
