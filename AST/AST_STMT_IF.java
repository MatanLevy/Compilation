package AST;

import IR.LABEL;
import IR.TEMP;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	public AST_STMT_IF(AST_EXP cond, AST_STMT body) {
		this.cond = cond;
		this.body = body;
	}
	
	public void print() {
		System.out.println("if stmt :");
		System.out.println(" condition  = ");
		cond.print();
		System.out.println(" body =  ");
		body.print();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (!(cond.calcType(table) instanceof AST_TYPE_INT))
			throw new RuntimeException("if condition not boolean");
		return body.checkSemantic(table);
	}


	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		LABEL ifLabel = new LABEL("if");
		LABEL exitIfLabel = new LABEL("exit_if");
		LABEL condLabel = new LABEL("condition_if");
		CodeGenarator.printJUMPCommand(condLabel.labelString);
		CodeGenarator.printLBLCommand(ifLabel.labelString);
		body.mipsTranslate(table, assemblyFileName, genartor);
		CodeGenarator.printJUMPCommand(exitIfLabel.labelString);
		CodeGenarator.printLBLCommand(condLabel.labelString);
		TEMP condAddress = cond.calcAddress(table, genartor, assemblyFileName);
		CodeGenarator.printBNQCommand(condAddress.name, MIPS_COMMANDS.ZERO, ifLabel.labelString);
		CodeGenarator.printLBLCommand(exitIfLabel.labelString);
	}
}