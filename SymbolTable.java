import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import AST.AST_CLASSDECL;
import AST.AST_FIELD;
import AST.AST_Node;
import AST.AST_TYPE;

public class SymbolTable {
	
	// This HashTable is the symbol table.
	// It's key is a string (it's the name of the object)
	// It's value is linkedList of SymbolEntry, The first SymbolEntry in every list is the appropriate define of the key to the relevant scope.
	
	private Hashtable<String, LinkedList<SymbolEntry>> table;
	
	// scopeCounter is the scope which we are now in it
	private int scopeCounter;
	
	// currentScope save all the id we updated in the table, in the current scope.
	private Set<String> currentScope;
	
	/**
	 * C'tor
	 */
	public SymbolTable() {
		table = new Hashtable<String, LinkedList<SymbolEntry>>();
		scopeCounter = 0;
		currentScope = new HashSet<String>();

	}

	public Hashtable<String, LinkedList<SymbolEntry>> getTable() {
		return table;
	}


	/**
	 * Create new scope
	 */
	public void pushScope () {
		scopeCounter++;	
	}
	
	/**
	 * When we exit a scope, we want to delete from the symbolTable all the SymbolEntry that we added from this scope.
	 */
	
	public void popScope() {
		for (String symEntry: currentScope) {
			table.get(symEntry).removeFirst();
		}
		scopeCounter--;
	}
	
	/**
	 * insert new SymbolEntry to the table, according to it's id.
	 * @param id
	 * @param type
	 */
	
	public void insert (String id, AST_TYPE type) {
		if (table.get(id) == null || table.get(id).isEmpty()) {
			LinkedList<SymbolEntry> symEntryList = new LinkedList<SymbolEntry>();
			table.put(id, symEntryList);
		}
		SymbolEntry sym = new SymbolEntry(id, type, scopeCounter);
		table.get(id).addFirst(sym);
		currentScope.add(id);

	}
	/**
	 * insert new SymbolEntry to the table according to it's AST_Node.
	 * @param node
	 */
	
	public void insertASTNode (AST_Node node) {
		if (node instanceof AST_CLASSDECL)
		{
			AST_CLASSDECL classDec = (AST_CLASSDECL) node;
			insert (classDec.classId, classDec.type);
			pushScope();	
		}
		else if (node instanceof AST_FIELD) {
			AST_FIELD f = (AST_FIELD) node;
			insert (f._id, f._type);
			if (! (f._comma_list.isEmpty())){
				for (int i=0; i < f._comma_list.size(); i++) {
					insert (f._comma_list.get(i), f._type);
				}
			}
		}
		

	}
	
	




	
	
	
	
	
}
