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
		_currentClass = "";

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
	 * insert new SymbolEntry to the table, according to it's id.
	 * @param id
	 * @param type
	 */
	
	public void add_symbol (String id, AST_TYPE type, boolean isInit) {
		if (table.get(id) == null) {
			LinkedList<SymbolEntry> symEntryList = new LinkedList<SymbolEntry>();
			table.put(id, symEntryList);
		}
		SymbolEntry sym = new SymbolEntry(id, type, isInit);
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
		if (x == null) 
			return false;
		return currentScopeHierarchy.getFirst().getSymbols().containsKey(x);
	}
	
	
	public boolean isSymbolInitalize (String id) {
		SymbolEntry sym = find_symbol(id);
		return sym.isInitalize();
	}
	


	public void setCurrentClass (String classID) {
		_currentClass = classID;
	}
	
	public void insertClassScope () {
		if (currentScopeHierarchy.getFirst().getIsClassScope())
			classScopes.add(currentScopeHierarchy.getFirst());
		
	}

	

	/**
	 * insert new SymbolEntry to the table according to it's AST_Node.
	 * If the AST_Node's rule use id, check if it's defined or initialize (according to the rule).
	 * @param node
	 */
	
	
	
	public void insertASTNode (AST_Node node) {
		
		if (node instanceof AST_CLASSDECL)
		{
			AST_CLASSDECL classDec = (AST_CLASSDECL) node;
			if (!insertClassDecl(classDec)) {
				//error
				return;
			}
			
	}
		else if (node instanceof AST_FIELD) {
			AST_FIELD f = (AST_FIELD) node;
			if (!(insertField(f))){
				// error
				return;
			}
		}
		
		else if (node instanceof AST_METHOD) {
			AST_METHOD m = (AST_METHOD) node;
			if (!insertMethod(m)) {
				//error
				return;
			}
			
		}

		else if (node instanceof AST_STMT_TYPE) {
			AST_STMT_TYPE st = (AST_STMT_TYPE) node;
			if (!insertStmtType(st)) {
				//error
				return;
			}
		}		

	}
	
	public boolean insertClassDecl (AST_CLASSDECL classDec) {
		// if we defined object with the same id in the same scope. it's multiple define error
		String classId = classDec.getClassId();
		if (check_scope(classId)){
			return false;
		}
		// if the class extends other class but the other class is undefined it's an error
		String baseClassId = classDec.getBaseId();
		if (baseClassId != null && !(table.containsKey(baseClassId))) {
			return false;
		}
		add_symbol(classId, classDec.type, true);
		setCurrentClass (classDec.classId);

		return true;
	}
	
	public boolean insertField (AST_FIELD field) {
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(field.getName()))
			return false;
		// we don't need to initialize fields before we use them.
		add_symbol (field._id, field._type, true);
		if (! (field._comma_list.isEmpty())){
			for (int i=0; i < field._comma_list.size(); i++) {
				String fieldId = field._comma_list.get(i);
				// if we defined object with the same id in the same scope. it's multiple define error
				if (check_scope(fieldId))
					return false;
				add_symbol (fieldId, field._type, true);
			}
		}
		
		return true;
	}
	
	public boolean insertMethod (AST_METHOD method) {
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(method.getName()))
			return false;
		add_symbol (method._id, method.type, true);
		AST_FORMALS formal = method.formals;
		add_symbol(formal._id, formal.type, true);
		AST_FORMALS_LIST fl = formal.f_list;
		for (int i=0; i < fl.formal_list.size(); i++) {
			add_symbol(fl.formal_list.get(i), fl.type_list.get(i), true);
		}	
		return true;
	}
	
	public boolean insertStmtType (AST_STMT_TYPE stmtType) {
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope (stmtType.getName()))
			return false;
		boolean initalize = false;
		if (stmtType.exp != null) {
			initalize = true;
		}
		add_symbol(stmtType.id, stmtType.type, initalize);

		
		return true;
	}
	
	
	
}
