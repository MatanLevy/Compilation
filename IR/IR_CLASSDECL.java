package IR;

import java.util.ArrayList;

public class IR_CLASSDECL extends IR_Node {
	public ArrayList<IR_METHOD> methods;
	public ArrayList<IR_FIELD> fields;
	public IR_CLASSDECL(ArrayList<IR_METHOD> methods, ArrayList<IR_FIELD> fields) {
		this.methods = methods;
		this.fields = fields;
	}
	
}
