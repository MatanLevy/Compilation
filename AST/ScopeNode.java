package AST;
import java.util.HashSet;
import java.util.Hashtable;

public class ScopeNode {
	Hashtable<String, SymbolEntry> symbols;
	boolean isClassScope;
	String className;
	
	public ScopeNode(boolean is_class_scope, String className) {
		this.symbols = new Hashtable<String, SymbolEntry> ();
		this.isClassScope = is_class_scope;
		this.className = className;
	}
	public Hashtable<String, SymbolEntry> getSymbols() {
		return symbols;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public void setSymbol(SymbolEntry symbol){
		symbols.put(symbol.id, symbol);
		
	}
	
	public boolean getIsClassScope() {
		return isClassScope;
	}
	public void setIsClassScope(boolean is_class_scope) {
		this.isClassScope = is_class_scope;
	}
	
	

}
