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
	public boolean isEmpty(){
		return (0 == size());
	}
	
	public int size () {
		return _id_list.size();
	}
	
	public void print() {
		System.out.println("comma id list : ");
		for (String id : _id_list) {
			System.out.println(id);
		}
	}
	public String get(int i) {
		return _id_list.get(i);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

}
