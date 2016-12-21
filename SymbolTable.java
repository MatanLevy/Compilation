import java.util.ArrayList;
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
	
	// scopeCounter is the number of the scope which we are now in it.
	private int scopeCounter;
	
	// scopes save all the id we updated in the table, in the indexed scope.
	// The index in the arrayList is refer to the scope number.
	// The set in the index is refer to the id we saved in the table in this scope.
	private ArrayList<Set<String>> scopes;
	
	/**
	 * C'tor
	 */
	public SymbolTable() {
		table = new Hashtable<String, LinkedList<SymbolEntry>>();
		scopeCounter = 0;
		scopes = new ArrayList<Set<String>>();

	}

	public Hashtable<String, LinkedList<SymbolEntry>> getTable() {
		return table;
	}


	/**
	 * Create new scope
	 */
	public void pushScope () {
		scopeCounter++;
		//Initialize new set for the new scope.
		Set<String> s = new HashSet<String>();
		scopes.add(s);
	}
	
	/**
	 * When we exit a scope, we want to delete from the symbolTable all the SymbolEntry that we added from this scope.
	 */
	
	public void popScope() {
		for (String symEntry: scopes.get(scopeCounter)) {
			//remove the first of the linked list (like in stack data structure)
			if (table.get(symEntry) != null) {
				table.get(symEntry).removeFirst(); 
			}
			else {
				return;  //should not happen never!
			}
		}
		scopes.remove(scopeCounter);
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
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		table.get(id).addFirst(sym);
		scopes.get(scopeCounter).add(id);

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
			//******* TO - DO: How we can know when the class is finish and we want to do popScope() ??? *********
			// ****** Maybe we should do 'pushScope()' and 'popScope()' when we go over the AST and we know when the class is finished. ******
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
