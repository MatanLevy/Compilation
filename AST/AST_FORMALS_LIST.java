package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_FORMALS_LIST {

	public List<String> formal_list;
	public List<AST_TYPE> type_list;
	
	public AST_FORMALS_LIST() {
		formal_list = new ArrayList<>();
		type_list = new ArrayList<>();
	}
	
	public void addFormal(AST_TYPE t, String id) {
		formal_list.add(id);
		type_list.add(t);
	}
	
	public int getSize() {
		return formal_list.size();
	}

	public void print() {
		System.out.println("formal list :");
		for (int i = 0; i < formal_list.size(); i++) {
			System.out.println("type = " + type_list.get(i));
			System.out.println("formal = " + formal_list.get(i));
		}
	}

	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return true;
	}
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		int diffToOffset = 1;
		int size = getSize() + 1;
		for (int j = 0; j < formal_list.size(); j++) {
			CodeGenarator.addPairToArgumentToOffsetMap(formal_list.get(j), size - diffToOffset);
			diffToOffset++;
//			if (diffToOffset < 0)
//				break;
		}
	}

}
