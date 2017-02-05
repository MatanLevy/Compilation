package AST;

import IR.LABEL;
import IR.TEMP;

public class AST_STMT_WHILE extends AST_STMT {
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/* CONSTRUCTOR(S) */
	/*******************/

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT body) {
		this.cond = cond;
		this.body = body;
	}

	public void print() {
		System.out.println("while statemnt");
		cond.print();
		body.print();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		if (!(cond.calcType(table) instanceof AST_TYPE_INT))
			throw new RuntimeException("while condition not boolean");
		return body.checkSemantic(table);
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		LABEL whileLabel = new LABEL("while_loop");
		LABEL exitWhileLabel = new LABEL("exit_while_loop");
		LABEL condLabel = new LABEL("condition_while");
		CodeGenarator.printJUMPCommand(condLabel.labelString);
		CodeGenarator.printLBLCommand(whileLabel.labelString);
		body.mipsTranslate(table, assemblyFileName, genartor);
		CodeGenarator.printJUMPCommand(condLabel.labelString);
		CodeGenarator.printLBLCommand(condLabel.labelString);
		TEMP condAddress = cond.calcAddress(table, genartor, assemblyFileName);
		TEMP zero = new TEMP();
		CodeGenarator.printLICommand(zero.name, 0);
		CodeGenarator.printBNQCommand(condAddress.name, zero.name, whileLabel.labelString);
		CodeGenarator.printLBLCommand(exitWhileLabel.labelString);
	}

}