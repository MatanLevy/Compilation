package AST;

public class AST_PROGRAM extends AST_Node{
	
	public AST_CLASSDECL_LIST class_dec_list;
	
	public AST_PROGRAM(AST_CLASSDECL_LIST cdl) {
		class_dec_list = cdl;
	}
	
	public AST_CLASSDECL_LIST getClassDeclList() {
		return class_dec_list;
	}
	
	
	
	public void print() {
		System.out.println("program : ");
		class_dec_list.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		boolean returnVal = class_dec_list.checkSemantic(table);
		if (!(table.isMainDefined())) {
			throw new RuntimeException("main did not defined in this program");
		}
		return returnVal;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		VirtualTableManager.printAllClasses();
		System.out.println(".text\n");
		System.out.println("main:\n");
		CodeGenarator.printJCommand(VirtualTableManager.mainLabel);
		ConditionHelper.printAllLabels();
		genartor.printExit(); //creating exit label
		CodeGenarator.printConcateStrings();
		class_dec_list.mipsTranslate(table, assemblyFileName, genartor);
	}

}
