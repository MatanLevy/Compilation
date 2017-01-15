package IR;

import java.util.ArrayList;

public class IR_STMT_LIST_BRACE extends IR_STMT {
	public ArrayList<IR_STMT> _stmtList;

	public IR_STMT_LIST_BRACE(ArrayList<IR_STMT> stmtList) {
		this._stmtList = stmtList;
	}
	
	
}
