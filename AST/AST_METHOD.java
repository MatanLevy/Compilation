package AST;

public class AST_METHOD extends AST_Node {

	
	public AST_TYPE type;
	public AST_FORMALS formals;
	public AST_STMT_LIST stmt_list;
	public String _id;
	public String _className;
	
	public AST_METHOD(String id, AST_FORMALS f, AST_STMT_LIST l) {
		type = null;
		formals = f;
		stmt_list = l;
		_id = id;
	}

	public AST_METHOD(AST_TYPE t, String id, AST_FORMALS f, AST_STMT_LIST l) {
		type = t;
		formals = f;
		stmt_list = l;
		_id = id;
	}
	
	
	public String get_className() {
		return _className;
	}

	public void set_className(String _className) {
		this._className = _className;
	}

	public void print() {
		System.out.println("method : ");
		if (type != null) {
			type.print();
		}
		else {
			System.out.println("type is void");
		}
		System.out.println("id = "+_id);
		formals.print();
		stmt_list.print();
	}

	@Override
	public String getName() {
		return _id;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (!SemanticChecker.isTypeDefinedAlready(table, type))
			return false;
		if (!formals.checkSemantic(table))
			return false;
		if (!stmt_list.checkSemantic(table))
			return false;
		return true;
 
	} 

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		//Initialize the offset of the frame of the method to be 0.
		CodeGenarator.createFrame();
		String label = genartor.LabelGenerate(_id, _className);
		genartor.insertMethodNameAndLabelToMap(_id, label);
		genartor.insertMethodNameAndLabelToMap(_id, label.substring(0, label.length()-2));
		CodeGenarator.printLabel(label);
		if (!(_id.equals("main")))
			printPrologOfMethod();
		else {
			CodeGenarator.printADDICommand(MIPS_COMMANDS.FRAME_PTR, MIPS_COMMANDS.STACK_PTR, 0);
		}
		formals.mipsTranslate(table, assemblyFileName, genartor);
		stmt_list.mipsTranslate(table,assemblyFileName,genartor);
		if (!(_id.equals("main"))) {
			printEpilog();
		}
		CodeGenarator.removeFrame();
	}




	public void printEpilog() {
		int numberOfAllocatedTotalInStack = 4*(2 + formals.numberOfArgs());
		CodeGenarator.printADDICommand(MIPS_COMMANDS.STACK_PTR,MIPS_COMMANDS.FRAME_PTR,numberOfAllocatedTotalInStack);
		CodeGenarator.printLWCommand(MIPS_COMMANDS.FRAME_PTR,MIPS_COMMANDS.FRAME_PTR,4); //retrive fm
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
	}
	
	public void printPrologOfMethod () {
		//CodeGenarator.allocateMemory(4);
		
		//TODO if we use jal/jr (need to check) we don't need it ?
		//CodeGenarator.printSWCommand(MIPS_COMMANDS.RA, MIPS_COMMANDS.STACK_PTR, 0);
		
		CodeGenarator.allocateMemory(4, false);
		CodeGenarator.printSWCommand(MIPS_COMMANDS.FRAME_PTR, MIPS_COMMANDS.STACK_PTR, 0);
		CodeGenarator.allocateMemory(4, false);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.FRAME_PTR, MIPS_COMMANDS.STACK_PTR, 0);
	}


//Example of prolog:	
//	li Temp_11,4
//
//	sub Temp_10,$sp,Temp_11
//
//	addi $sp,Temp_10,0
//
//	sw $ra,0($sp)
//
//	li Temp_13,4
//
//	sub Temp_12,$sp,Temp_13
//
//	addi $sp,Temp_12,0
//
//	sw $fp,0($sp)
//
//	addi $fp,$sp,0


}
