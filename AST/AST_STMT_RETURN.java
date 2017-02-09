package AST;

public class AST_STMT_RETURN extends AST_STMT {



	public AST_STMT_RETURN() {
	}

	public void print() {
		System.out.println("return statament");
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		AST_TYPE returnType= table.returnTypeCurrentMethod();
		if (returnType != null)
			throw new RuntimeException("method must return " + returnType.getName());
		return true;

	}


	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {

		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
	}

	public void mipsTranslateReturn(SymbolTable table,String assemblyFileName,CodeGenarator genarator,int numberOfArguments) {
		int numberOfAllocatedTotalInStack = 4*(2 + numberOfArguments);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.STACK_PTR,MIPS_COMMANDS.FRAME_PTR,numberOfAllocatedTotalInStack);
		CodeGenarator.printLWCommand(MIPS_COMMANDS.FRAME_PTR,MIPS_COMMANDS.FRAME_PTR,4); //retrive fm
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
		CodeGenarator.removeFrame();
	}
}
