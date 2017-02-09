package AST;

import IR.TEMP;
import com.sun.org.apache.bcel.internal.classfile.Code;

public class AST_STMT_RETURN_EXP extends AST_STMT {

	private AST_EXP exp;

	public AST_STMT_RETURN_EXP(AST_EXP e) {
		this.exp = e;
	}
	
	public void print() {
		System.out.println("return_exp statement");
		exp.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		AST_TYPE evlType = exp.calcType(table);
		AST_TYPE returnType= table.returnTypeCurrentMethod();
		if (returnType == null) //void
			throw new RuntimeException("void method mustn't return value");
		if (!(SemanticChecker.isBaseClassOf(returnType.getName(), 
				evlType.getName())))
			throw new RuntimeException("method should return " + returnType.getName()
			+ " and not " + evlType.getName());
		return true;
			
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genarator) {
		
		TEMP tempExp = exp.calcAddress(table, genarator, assemblyFileName);
		CodeGenarator.printLWCommand(MIPS_COMMANDS.V0, tempExp.name, 0);
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);

	}

	public void mipsTranslateReturn(SymbolTable table,String assemblyFileName,CodeGenarator genarator,int numberOfArguments) {
		int numberOfAllocatedTotalInStack = 4*(2 + numberOfArguments);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.STACK_PTR,MIPS_COMMANDS.FRAME_PTR,numberOfAllocatedTotalInStack);
		CodeGenarator.printLWCommand(MIPS_COMMANDS.FRAME_PTR,MIPS_COMMANDS.FRAME_PTR,4); //retrive fm
		TEMP returnValue = exp.calcAddress(table,genarator,assemblyFileName);
		CodeGenarator.printSWCommand(returnValue.name,MIPS_COMMANDS.STACK_PTR,0);
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
		CodeGenarator.removeFrame();
	}

}
  