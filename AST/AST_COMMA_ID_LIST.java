package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_COMMA_ID_LIST extends AST_Node {

	
	List<String> _id_list;
	
	public AST_COMMA_ID_LIST() {
		_id_list = new ArrayList<>();
	}
	public void addId(String id) {
		_id_list.add(id);
	}
	
	public void print() {
		System.out.println("comma id list : ");
		for (String id : _id_list) {
			System.out.println(id);
		}
	}

}
