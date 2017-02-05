package AST;

import java.util.HashMap;
import java.util.Map;

import IR.LABEL;

public class VFTable {
	String name;
	public Map <String, Integer> labelMethodToOffsetMap;

	
	public VFTable (String className) {
		name = "VFTable_" + className;
		labelMethodToOffsetMap = new HashMap<String, Integer>();
	}
	
	public void putLabelInMap (String label ){
		int size = labelMethodToOffsetMap.size();
		labelMethodToOffsetMap.put(label, size);
	}
	
	//return -1 if there is no such label in map
	public int getOffsetOfMethodLabel(String label) {
		int res = labelMethodToOffsetMap.get(label);
		return (labelMethodToOffsetMap.get(label)==null)? -1:res;
	}
	
	public void addAllPairsFromGivenVFTable (VFTable vftable) {
		Map <String, Integer> mapOfGivenVFTable = vftable.labelMethodToOffsetMap;
		labelMethodToOffsetMap.putAll(mapOfGivenVFTable);
	}


}
