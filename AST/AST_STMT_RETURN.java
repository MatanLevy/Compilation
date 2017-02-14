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

		if (!CodeGenarator.currentMethod.equals("main"))
			CodeGenarator.printEpilogInReturn();
		else {
			//exit if it's main
			CodeGenarator.printLICommand(MIPS_COMMANDS.V0, MIPS_COMMANDS.exit);
			CodeGenarator.printSyscallCommand();
		}
	}

}
