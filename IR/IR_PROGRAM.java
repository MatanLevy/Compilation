package IR;

import java.util.ArrayList;

public class IR_PROGRAM extends IR_Node{
	public ArrayList<IR_CLASSDECL> _classDeclList;

	public IR_PROGRAM(ArrayList<IR_CLASSDECL> classDeclLit) {
		this._classDeclList = classDeclLit;
	}
}
