/**
 * 
 */
package AST;

import java.util.List;

public class SymbolEntryMethod extends SymbolEntry{
	String id;
	List<AST_TYPE> listTypeFormalsMethod;
	private AST_TYPE returnType;
	
	public SymbolEntryMethod(String id, AST_TYPE returnType ,List<AST_TYPE> listFormalsMethod) {
		super(id, returnType, true, true, listFormalsMethod);
		this.returnType = returnType;
		this.listTypeFormalsMethod = listFormalsMethod;
		this.id = id;

	}




	public List<AST_TYPE> getListTypeFormalsMethod() {
		return listTypeFormalsMethod;
	}

	public void setListTypeFormalsMethod(List<AST_TYPE> listTypeFormalsMethod) {
		this.listTypeFormalsMethod = listTypeFormalsMethod;
	}

	public AST_TYPE getReturnType() {
		return returnType;
	}

	public void setReturnType(AST_TYPE returnType) {
		this.returnType = returnType;
	}




}
