package AST;

import java.util.HashMap;
import java.util.Map;

public class Frame {
	


	private int offset;
	/**
	 * map to argument name and it's offset.
	 * Example: if we in function that get 3 parameters (a, b, c)
	 * So, <a,2>, <b,1>, <c,0>. Because argument c in $fp+0 , b in $fp+4 and c in $fp+8
	 */
	private Map <String, Integer> argumentToOffsetMap;
	
	
	public Frame() {
		offset= 0;
		argumentToOffsetMap = new HashMap<String, Integer>();
	}
	
	
	public void clearArgumentToOffsetMap() {
		argumentToOffsetMap.clear();
	}
	public void addPairToArgumentToOffsetMap (String arg, int offset) {
		argumentToOffsetMap.put(arg, offset);
	}
	
	/**
	 * return -1 if there is no argument with such name.
	 * @param arg
	 * @return
	 */
	public int getOffsetOfArgument(String arg) {
		if (argumentToOffsetMap.get(arg)==null)
			return -1;
		else 
			return argumentToOffsetMap.get(arg);
	}
	
	public int getOffset() {
		return offset;
	}
	public void changeOffset (int size) {
		offset -= size;
	}
	
	public void initOffset() {
		offset=0;
	}

	

	
	


}
