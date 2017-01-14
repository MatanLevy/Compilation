package IR;

import java.util.ArrayList;

public class IR_EXP_LIST extends IR_EXP {
	public ArrayList<IR_EXP> _expList;

	public IR_EXP_LIST() {
		_expList = new ArrayList<IR_EXP>();
	}
	
	public void insertToExpList(IR_EXP e) {
		_expList.add(e);
	}
	
	

}
