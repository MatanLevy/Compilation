package AST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList; 
import java.util.List;
import java.util.Map.Entry;


public class SymbolTable {
	
	// This HashTable is the symbol table.
	// It's key is a string (it's the name of the object)
	// It's value is linkedList of SymbolEntry, The first SymbolEntry in every list is the object in the nearest scope with the 'id' name. 
	
	private Hashtable<String, LinkedList<SymbolEntry>> tableOfSymbols;

	  
	
 
	
	// The name of the class we in it's scope (If we aren't in a scope of a class, the value in "").

	private String _currentClass;
	

	//List of the classes scopes. (In every scope of class it has fields and methods that defined in it)
	private ArrayList<ScopeNode> classScopes;

	//Where we are now in the hierarchy of scopes. 
	private LinkedList<ScopeNode> currentScopeHierarchy;
	
	//true if main defined in the program. else, false.
	private boolean isMainDefined;  
	
	private int NumberOfSymbolEntryDefinedInCurrentScope;
	
	private int numberOfFieldInCurrentClass;
	
	/**
	 * C'tor
	 */
	public SymbolTable() {
		tableOfSymbols = new Hashtable<String, LinkedList<SymbolEntry>>();
		classScopes = new ArrayList<ScopeNode>();
		currentScopeHierarchy = new LinkedList<ScopeNode>();
		_currentClass = "";
		isMainDefined = false;
		pushScope(false, _currentClass);
		NumberOfSymbolEntryDefinedInCurrentScope = 0;

	}

	public boolean isMainDefined() {
		return isMainDefined;
	}

	public void setMainDefined(boolean isMainDefined) {
		this.isMainDefined = isMainDefined;
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
		NumberOfSymbolEntryDefinedInCurrentScope = scopeSymbols.size();
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
	
	//Called only after popScope and if the scope we pop is from type { stmt*}. 
	//This function decrease the counter of the local variables by the number of the local variables we defined in this scope. 
	public void decreaseOffsetOfLocalVars() {

		SymbolEntry.decreaseCounerOffsetOfLocalVarCurrentMethodByNum(NumberOfSymbolEntryDefinedInCurrentScope);

	}
	
	public void initCounterOffsetWhenPopScopeOfClass () {
		SymbolEntry.initcounterOffsetOfFieldsCurrentClass();
		SymbolEntry.initCounterOffsetMethodsCurrentClass();
	}
	
	public void initConterOffsetWhenPopScopeOfMethod () {
		SymbolEntry.initCounterOffsetOfLocalVarCurrentMethod();
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
	
	
	//return the amount of fields defined in specific class Scope.
	public int returnNumberOfFieldsDefinedInGivenClassScope (ScopeNode classScope) {
		int result = 0;
		for (String id : classScope.getSymbols().keySet()) {
			SymbolEntry sym = classScope.getSymbols().get(id);
			if (!(sym instanceof SymbolEntryMethod))
				result++;
		}			
		return result;
		
	}

	
	//return the amount of methods defined in specific class Scope.
	public int returnNumberOfMethodsDefinedInGivenClassScope (ScopeNode classScope) {
		int result = 0;
		for (String id : classScope.getSymbols().keySet()) {
			SymbolEntry sym = classScope.getSymbols().get(id);
			if (sym instanceof SymbolEntryMethod)
				result++;
		}			
		return result;
		
	}
	
	/**
	 * insert new SymbolEntry to the table, according to it's id.
	 * @param id
	 * @param type
	 */
	
	public SymbolEntry add_symbol (String id, AST_TYPE type, boolean isInit, boolean ismethod, List<AST_TYPE> listmethod) {
		initEntryOfIdInTableOfSymbols(id);
		SymbolEntry sym = new SymbolEntry(id, type, isInit, ismethod, listmethod);
		sym.setInWhichClassDefined(_currentClass);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		tableOfSymbols.get(id).addFirst(sym);
		currentScopeHierarchy.getFirst().setSymbol(sym);
		return sym;

	}
	
	public SymbolEntryMethod add_symbol_method (String id, AST_TYPE returnType, List<AST_TYPE> listmethod) {
		initEntryOfIdInTableOfSymbols(id);
		SymbolEntryMethod sym = new SymbolEntryMethod(id, returnType, listmethod);
		sym.setInWhichClassDefined(_currentClass);
		//insert the SymbolEntry to the start of the linked list. (like in stack data structure)
		tableOfSymbols.get(id).addFirst(sym);
		currentScopeHierarchy.getFirst().setSymbol(sym);
		return sym;

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
	
	public void remove_symbol (String id) {
		tableOfSymbols.get(id).removeFirst();
	}
	
	//true if x defined in current scope
	public boolean check_scope (String x)
	{		
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
//		SymbolEntry sym = find_symbol(id);
//		return sym.isInitalize();
		return true;
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

		add_symbol(classId, classDec.type, true, false, null);
		setCurrentClass (classDec.classId);
		pushScope(true, classId);
		// if the class extends other class, we put all the methods/fields that defined in the other class, in the current class's scope.
		if (baseClassId != null) {
			ScopeNode baseClassScope = getClassScope(baseClassId);
			if (baseClassScope == null)//error 
				return false;
			addAllSymbols(baseClassScope);
			int addToOffsetField = returnNumberOfFieldsDefinedInGivenClassScope(baseClassScope);
			SymbolEntry.updateOffsetField(addToOffsetField);
			int addToOffsetMethod = returnNumberOfMethodsDefinedInGivenClassScope(baseClassScope);
			SymbolEntry.updateOffsetMethod(addToOffsetMethod);
		}


		return true;
	}
	
	
	
	public boolean insertField (AST_FIELD field) {
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(field.getName()))
			error(true, false, field.getName());
		// we don't need to initialize fields before we use them.
		add_symbol(field._id, field._type, true, false, null).setOffsetField();
		numberOfFieldInCurrentClass++;
		if (! (field._comma_list.isEmpty())){
			for (int i=0; i < field._comma_list.size(); i++) {
				String fieldId = field._comma_list.get(i);
				// if we defined object with the same id in the same scope. it's multiple define error
				if (check_scope(fieldId))
					error(true, false, fieldId);
				add_symbol(fieldId, field._type, true, false, null).setOffsetField();
				numberOfFieldInCurrentClass++;
			}
		}
		
		return true;
	}
	
	public boolean insertMethod (AST_METHOD method) {
		String methodName = method.getName();
		
		checkIfMethodIsMain(method);
		
		// if we defined object with the same id in the same scope. it's multiple define error
		if (check_scope(methodName))
		{
			SymbolEntry symbol = find_symbol (methodName);
			if (symbol.getInWhichClassDefined().equals(_currentClass))
				error(true, false, methodName);
		}
		add_symbol_method(methodName, method.type, generateFormalsList(method)).setOffsetMethod();
		pushScope(false, null, method._id);

		AST_FORMALS formal = method.formals;
		if (formal._id != null){
			add_symbol(formal._id, formal.type, true, false, null);
			HashSet <String> formalsId = new HashSet<String>();
			formalsId.add(formal._id);
			AST_FORMALS_LIST fl = formal.f_list;
			for (int i=0; i < fl.formal_list.size(); i++) {
				String f_id = fl.formal_list.get(i);
				if (formalsId.contains(f_id)) //if there is formal with the same name of other formal, it's an error
					error(true, false, f_id);
				formalsId.add(f_id);
				add_symbol(f_id, fl.type_list.get(i), true, false, null);
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
		add_symbol(stmtType.id, stmtType.type, initalize, false, null).setOffsetLocalVar();

		
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
	public String checkIfWeAreInMethodScope() {
		String method_name = null;
		for (ScopeNode scope : currentScopeHierarchy) {
			method_name = scope.methodName;
			if (method_name != null && method_name != "") {
				return method_name;
			}
		}
		return null;
	}
	
	
	public AST_TYPE returnTypeCurrentMethod() {
		String method_name = checkIfWeAreInMethodScope();
		if (method_name == null) {
			throw new RuntimeException("We are not in method scope");
		}
		SymbolEntry symbolMethod = find_symbol(method_name); 
		if (!(symbolMethod instanceof SymbolEntryMethod))
			throw new RuntimeException(method_name + " is not a method");
		return symbolMethod.getType();
	}
	
	public String get_currentClass() {
		return _currentClass;
	}

	public void set_currentClass(String _currentClass) {
		this._currentClass = _currentClass;
		this.numberOfFieldInCurrentClass = 0;
	}

	
	public void checkIfMethodIsMain(AST_METHOD method) {
		String methodName = method.getName();
		String wrongMainSignature = "wrong main signature";
		//check if it's main method.
		if (methodName.equals("main")) {
			if (isMainDefined)
				error(true, false, methodName);
			else {
				List<AST_TYPE> formalsListType = generateFormalsList(method);
				if (method.type != null || formalsListType.size() != 1) {
					throw new RuntimeException(wrongMainSignature);
				}
				if (!(formalsListType.get(0) instanceof AST_TYPE_BRACK))
				{
					throw new RuntimeException(wrongMainSignature);
				}
				AST_TYPE_BRACK formal = (AST_TYPE_BRACK) formalsListType.get(0);
				AST_TYPE formalType = formal.getType();
				if (formalType instanceof AST_TYPE_STRING){
					setMainDefined(true);
				}
				else {
					throw new RuntimeException(wrongMainSignature);

				}
				}
		}
	}
	
	public int returnTheSizeOfTheObjectFromClassTypeOnTheHeap(String className) {
		if (!className.equals(_currentClass)) {
			ScopeNode scopeOfClass = getClassScope(className);
			int size = returnNumberOfFieldsDefinedInGivenClassScope(scopeOfClass);

			// place for virtual function address
			size++;

			return size * 4;
		}
		else {
			// we want only the number of fields plus 1 for the VFTable.
			return (numberOfFieldInCurrentClass + 1) * 4; 
		}
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
