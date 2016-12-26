package AST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList; 
import java.util.List;


public class SymbolTable {
	
	// This HashTable is the symbol table.
	// It's key is a string (it's the name of the object)
	// It's value is linkedList of SymbolEntry, The first SymbolEntry in every list is the object in the nearest scope with the 'id' name. 
	
	private Hashtable<String, LinkedList<SymbolEntry>> tableOfSymbols;
	
	// The name of the class we in it's scope (If we aren't in a scope of a class, the value in null).
	private String _currentClass;
	
	public String get_currentClass() {
		return _currentClass;
	}

	public void set_currentClass(String _currentClass) {
		this._currentClass = _currentClass;
	}


	//List of the classes scopes. (In every scope of class it has fields and methods that defined in it)
	private ArrayList<ScopeNode> classScopes;

	//Where we are now in the hierarchy of scopes. 
	private LinkedList<ScopeNode> currentScopeHierarchy;
	
	/**
	 * C'tor
	 */
	public SymbolTable() {
		tableOfSymbols = new Hashtable<String, LinkedList<SymbolEntry>>();
		classScopes = new ArrayList<ScopeNode>();
		currentScopeHierarchy = new LinkedList<ScopeNode>();
		_currentClass = "";
		pushScope(false, _currentClass);

	}

	public Hashtable<String, LinkedList<SymbolEntry>> getTable() {
		return tableOfSymbols;
	}


	/**
	 * Create new scope
	 */
	public void pushScope (boolean is_class_scope, String class_name) {
		//Initialize new set for the new scope.
		ScopeNode scope = new ScopeNode(is_class_scope, class_name);
		currentScopeHierarchy.addFirst(scope);
	}
	
	public void pushScope (boolean is_class_scope, String class_name, String method_name) {
		//Initialize new set for the new scope.
		ScopeNode scope = new ScopeNode(is_class_scope, class_name, method_name);
		currentScopeHierarchy.addFirst(scope);
	}
	/**
	 * When we exit a scope, we want to delete from the symbolTable all the SymbolEntry that we added from this scope.
	 */
	
	public void popScope() {
		Hashtable<String, SymbolEntry> scopeSymbols = currentScopeHierarchy.getFirst().getSymbols();
		for (String id : scopeSymbols.keySet()) {
			//remove the first of the linked list (like in stack data structure)
			if (tableOfSymbols.get(id) != null) {
				remove_symbol(id); 
			}
			else {
				return;  //should not happen never!
			}
		}
		
		insertClassScope();
		currentScopeHierarchy.removeFirst();
	}
	
	public ScopeNode getClassScope (String className) {
		ScopeNode scopeIter = null;
		for (int i=0; i < classScopes.size(); i++) {
			scopeIter = classScopes.get(i);
			String classNameScopeIter = scopeIter.className;
			if (classNameScopeIter.equals(className))
				return scopeIter;
		}
		return scopeIter;
	}
	
	
	/**
	 * insert new SymbolEntry to the table, according to it's id.
	 * @param id
	 * @param type
	 */
	
	public void add_symbol (String id, AST_TYPE type, boolean isInit,
			boolean ismethod,List<AST_TYPE> listmethod) {
		initEntryOfIdInTableOfSymbols(id);
		SymbolEntry sym = new SymbolEntry(id, type, isInit,ismethod,listmethod);
		sym.setInWhichClassDefined(_currentClass);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		tableOfSymbols.get(id).addFirst(sym);
		currentScopeHierarchy.getFirst().setSymbol(sym);

	}
	
	public void add_symbol_method (String id, AST_TYPE returnType, List<AST_TYPE> listmethod) {
		initEntryOfIdInTableOfSymbols(id);
		SymbolEntryMethod sym = new SymbolEntryMethod(id, returnType, listmethod);
		sym.setInWhichClassDefined(_currentClass);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		tableOfSymbols.get(id).addFirst(sym);
		currentScopeHierarchy.getFirst().setSymbol(sym);

	}
	
	public void add_symbol (SymbolEntry symbol) {
		String id = symbol.id;
		initEntryOfIdInTableOfSymbols(id);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		tableOfSymbols.get(id).addFirst(symbol);
		currentScopeHierarchy.getFirst().setSymbol(symbol);

	}
	
	public void initEntryOfIdInTableOfSymbols (String id) {
		if (tableOfSymbols.get(id) == null) {
			LinkedList<SymbolEntry> symEntryList = new LinkedList<SymbolEntry>();
			tableOfSymbols.put(id, symEntryList);
		}
	}
	
	public void addAllSymbols (ScopeNode scope) {
		for (String id : scope.getSymbols().keySet()) {
			SymbolEntry sym = scope.getSymbols().get(id);
			add_symbol(sym);
		}
	}
	
	public SymbolEntry find_symbol (String id) {
		
		if (tableOfSymbols.get(id)==null || tableOfSymbols.get(id).isEmpty()) {
			return null;
		}
		return tableOfSymbols.get(id).getFirst();
		
	}
	
	public void initalizeSymbolEntryInCurrScope (String id) {
		SymbolEntry sym = tableOfSymbols.get(id).getFirst();
		tableOfSymbols.get(id).removeFirst();
		sym.initalize = true;
		tableOfSymbols.get(id).addFirst(sym);
	}
	
	public void remove_symbol (String id) {
		tableOfSymbols.get(id).removeFirst();
	}
	
	//true if x defined in current scope
	public boolean check_scope (String x)
	{
		/*System.out.println("NEW CHECK");
		int i = 0;
		for (ScopeNode node : currentScopeHierarchy) {
			System.out.println(i++);
			for (String key : node.getSymbols().keySet()) {
			    System.out.println(key + ":" + node.getSymbols().get(key));
			}
		} */  //prints I've made to understand might help you  
		if (x == null) 
			return false;
		return currentScopeHierarchy.getFirst().getSymbols().containsKey(x);
	}
	
	//check if defined class with classID name.
	public boolean check_classScopes (String classID) {
		if (classID == null)
			return false;
		for (int i=0 ; i < classScopes.size(); i++) {
			if (classScopes.get(i).className.equals(classID))
				return true;
		}
		return false;
	}
	
	public boolean isSymbolInitalize (String id) {
		SymbolEntry sym = find_symbol(id);
		return sym.isInitalize();
	}
	


	public void setCurrentClass (String classID) {
		_currentClass = classID;
	}
	
	public void insertClassScope () {
		if (currentScopeHierarchy.getFirst().getIsClassScope()){
			ScopeNode scope = currentScopeHierarchy.getFirst();
			for (String str : scope.symbols.keySet()) {
				scope.symbols.get(str).setInWhichClassDefined(_currentClass);

			}
			classScopes.add(scope);
		
	}}

	

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
		if (check_classScopes(classId)){
			throw new RuntimeException("duplicate defined Class : " + classId);
		}
		// if the class extends other class but the other class is undefined it's an error
		String baseClassId = classDec.getBaseId();
		if (baseClassId != null && !(check_classScopes(baseClassId))) {
			throw new RuntimeException("Class " + classId + " extends class that "
					+ "hasn't been defined yet (" + baseClassId + ")");
		}

		add_symbol(classId, classDec.type, true,false,null);
		setCurrentClass (classDec.classId);
		pushScope(true, classId);
		// if the class extends other class, we put all the methods/fields that defined in the other class, in the current class's scope.
		if (baseClassId != null) {
			ScopeNode baseClassScope = getClassScope(baseClassId);
			if (baseClassScope == null)//error 
				return false;
			addAllSymbols(baseClassScope);
		}


		return true;
	}
	
	public boolean insertField (AST_FIELD field) {
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(field.getName()))
			error(true, false, field.getName());
		// we don't need to initialize fields before we use them.
		add_symbol (field._id, field._type, true,false,null);
		if (! (field._comma_list.isEmpty())){
			for (int i=0; i < field._comma_list.size(); i++) {
				String fieldId = field._comma_list.get(i);
				// if we defined object with the same id in the same scope. it's multiple define error
				if (check_scope(fieldId))
					error(true, false, fieldId);
				add_symbol (fieldId, field._type, true,false,null);
			}
		}
		
		return true;
	}
	
	public boolean insertMethod (AST_METHOD method) {
		String methodName = method.getName();
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(methodName))
		{
			SymbolEntry symbol = find_symbol (methodName);
			if (symbol.getInWhichClassDefined().equals(_currentClass))
				error(true, false, methodName);
		}
		add_symbol_method(methodName, method.type, 
				generateFormalsList(method));
		pushScope(false, null, method._id);

		AST_FORMALS formal = method.formals;
		if (formal._id != null){
			add_symbol(formal._id, formal.type, true,false,null);
			HashSet <String> formalsId = new HashSet<String>();
			formalsId.add(formal._id);
			AST_FORMALS_LIST fl = formal.f_list;
			for (int i=0; i < fl.formal_list.size(); i++) {
				String f_id = fl.formal_list.get(i);
				if (formalsId.contains(f_id)) //if there is formal with the same name of other formal, it's an error
					error(true, false, f_id);
				formalsId.add(f_id);
				add_symbol(f_id, fl.type_list.get(i), true,false,null);
			}
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
		add_symbol(stmtType.id, stmtType.type, initalize,false,null);

		
		return true;
	}
	
	
	
	
	public static List<AST_TYPE> generateFormalsList(AST_METHOD method) {
		List<AST_TYPE> argumentsList = new ArrayList<>();
		AST_FORMALS formals = method.formals;
		if (formals.type == null)
			return argumentsList;
		argumentsList.add(formals.type);
		argumentsList.addAll(formals.f_list.type_list);
		return argumentsList;
	}
	
	public AST_TYPE returnTypeCurrentMethod () {
		String method_name = currentScopeHierarchy.getFirst().methodName;
		if (method_name==null || method_name.equals("")) { //it's not a scope of method.
			throw new RuntimeException("We are not in method scope");
		}
		SymbolEntry symbolMethod = find_symbol(method_name);
		if (!(symbolMethod instanceof SymbolEntryMethod))
			throw new RuntimeException(method_name + " is not a method");
		return symbolMethod.getType();
		
		
	}
	
	
	public void error (boolean multiDefine, boolean undefinded, String id) {
		if (multiDefine)
			throw new RuntimeException("multipile defintion of : " + id);
		if (undefinded)
			throw new RuntimeException("undefined: " + id);
		if (!multiDefine && !undefinded) 
		{
			throw new RuntimeException(id);

		}

	}
	
	
	
}
