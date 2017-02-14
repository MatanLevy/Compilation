package AST;

import COUNTERS.TEMP;


public class AST_VAR_FIELD extends AST_VAR
{
	public AST_EXP var;
	public String fieldName;
	
	public AST_VAR_FIELD(AST_EXP e, String fieldName2) {
		var = e;
		fieldName = fieldName2; 
	}
	
	public void print() {
		System.out.println("var field : ");
		var.print();
		System.out.println("field name = " + fieldName);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return fieldName;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize) {
		AST_TYPE classType = (var.typeUptoDate)? var.type : var.calcType(table);
		if (!(classType instanceof AST_TYPE_CLASS)) 
			throw new RuntimeException("cannt access field of type not class");
		String className = classType.getName();
		SymbolEntry entry = (table.get_currentClass().equals(className)) ? 
				table.find_symbol(fieldName) : table.getClassScope(className).
				getSymbols().get(fieldName);
		if (entry == null)
			throw new RuntimeException("no such field : " + fieldName +
					" in class " + className);
		if (entry.isIs_method())
			throw new RuntimeException(fieldName + " is method,not field");
		return entry.getType();
	}



	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {

		if (!CodeGenarator.currentMethod.equals("main"))
			genarator.thisAddress = CodeGenarator.getThis();

		TEMP objectAddress = (var == null) ? genarator.thisAddress : var.calcAddress(table, genarator, fileName);
		genarator.checkNotNull(objectAddress);
		String classStaticName = (var == null) ? CodeGenarator.currentClass : ((AST_TYPE_CLASS)(var.calcType(table))).getName();
		int offsetOfFieldInHeap = (VirtualTableManager.getListOfActualFields(classStaticName).indexOf(fieldName)+1) * 4;
		TEMP offsetAddress = new TEMP();
		CodeGenarator.printADDICommand(offsetAddress.name, objectAddress.name, offsetOfFieldInHeap);
		return offsetAddress;
	}
}