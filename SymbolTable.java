import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import AST.AST_CLASSDECL;
import AST.AST_FIELD;
import AST.AST_FORMALS;
import AST.AST_FORMALS_LIST;
import AST.AST_METHOD;
import AST.AST_Node;
import AST.AST_STMT_TYPE;
import AST.AST_TYPE;

public class SymbolTable {
	
	// This HashTable is the symbol table.
	// It's key is a string (it's the name of the object)
	// It's value is linkedList of SymbolEntry, The first SymbolEntry in every list is the object in the nearest scope with the 'id' name. 
	
	private Hashtable<String, LinkedList<SymbolEntry>> table;
	
	// The name of the class we in it's scope (If we aren't in a scope of a class, the value in null).
	private String _currentClass;
	
	//List of the classes scopes. (In every scope of class it has fields and methods that defined in it)
	private ArrayList<ScopeNode> classScopes;

	//Where we are now in the hierarchy of scopes. 
	private LinkedList<ScopeNode> currentScopeHierarchy;
	
	/**
	 * C'tor
	 */
	public SymbolTable() {
		table = new Hashtable<String, LinkedList<SymbolEntry>>();
		classScopes = new ArrayList<ScopeNode>();
		currentScopeHierarchy = new LinkedList<ScopeNode>();
		_currentClass = null;

	}

	public Hashtable<String, LinkedList<SymbolEntry>> getTable() {
		return table;
	}


	/**
	 * Create new scope
	 */
	public void pushScope (boolean is_class_scope, String class_name) {
		//Initialize new set for the new scope.
		ScopeNode scope = new ScopeNode(is_class_scope, class_name);
		currentScopeHierarchy.addFirst(scope);
	}
	
	
	/**
	 * When we exit a scope, we want to delete from the symbolTable all the SymbolEntry that we added from this scope.
	 */
	
	public void popScope() {
		Hashtable<String, SymbolEntry> scopeSymbols = currentScopeHierarchy.getFirst().getSymbols();
		for (String id : scopeSymbols.keySet()) {
			//remove the first of the linked list (like in stack data structure)
			SymbolEntry symEntry = scopeSymbols.get(id);
			if (table.get(symEntry) != null) {
				remove_symbol(symEntry.getId()); 
			}
			else {
				return;  //should not happen never!
			}
		}
		
		insertClassScope();
		currentScopeHierarchy.removeFirst();
	}
	

	/**
	 * insert new SymbolEntry to the table according to it's AST_Node.
	 * @param node
	 */
	
	public void insertASTNode (AST_Node node) {
		if (node instanceof AST_CLASSDECL)
		{
			AST_CLASSDECL classDec = (AST_CLASSDECL) node;
			add_symbol (classDec.classId, classDec.type);
			setCurrentClass (classDec.classId);
			pushScope(true, classDec.classId);
			
			//TODO
			//******* TO - DO: How we can know when the class is finish and we want to do popScope() ??? *********
			// ****** Maybe we should do 'pushScope()' and 'popScope()' when we go over the AST and we know when the class is finished. ******
			// So, we should do it outside this class.
		}
		else if (node instanceof AST_FIELD) {
			AST_FIELD f = (AST_FIELD) node;
			add_symbol (f._id, f._type);
			if (! (f._comma_list.isEmpty())){
				for (int i=0; i < f._comma_list.size(); i++) {
					add_symbol (f._comma_list.get(i), f._type);
				}
			}
		}
		
		else if (node instanceof AST_METHOD) {
			AST_METHOD m = (AST_METHOD) node;
			add_symbol (m._id, m.type);
			//TODO 
			//need to pushScope ();
			
			//in the end need to popScope();
			
		}
		else if (node instanceof AST_FORMALS) {
			AST_FORMALS f = (AST_FORMALS) node;
			add_symbol(f._id, f.type);
			AST_FORMALS_LIST fl = f.f_list;
			for (int i=0; i < fl.formal_list.size(); i++) {
				add_symbol(fl.formal_list.get(i), fl.type_list.get(i));
			}
			
		}
		else if (node instanceof AST_STMT_TYPE) {
			AST_STMT_TYPE st = (AST_STMT_TYPE) node;
			add_symbol(st.id, st.type);
		}
		
		//TODO AST_STMT_ASSIGN, but know the field is var (AST_VAR is abstract class...this is not good)
		
		

	}
	
	/**
	 * insert new SymbolEntry to the table, according to it's id.
	 * @param id
	 * @param type
	 */
	
	public void add_symbol (String id, AST_TYPE type) {
		if (table.get(id) == null) {
			LinkedList<SymbolEntry> symEntryList = new LinkedList<SymbolEntry>();
			table.put(id, symEntryList);
		}
		SymbolEntry sym = new SymbolEntry(id, type);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		table.get(id).addFirst(sym);
		currentScopeHierarchy.getFirst().setSymbol(sym);;

	}
	public SymbolEntry find_symbol (String id) {
		
		if (table.get(id).isEmpty()) {
			return null;
		}
		return table.get(id).getFirst();
		
	}
	
	public void remove_symbol (String id) {
		table.get(id).removeFirst();
	}
	
	//true if x defined in current scope
	public boolean check_scope (String x)
	{
		return currentScopeHierarchy.getFirst().getSymbols().containsKey(x);
	}
	


	public void setCurrentClass (String classID) {
		_currentClass = classID;
	}
	
	public void insertClassScope () {
		if (currentScopeHierarchy.getFirst().isClassScope)
			classScopes.add(currentScopeHierarchy.getFirst());
		
	}

	
	
	
	
	
}
