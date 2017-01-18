package AST;

import IR.TEMP;

public class AST_EXP_BINOP extends AST_EXP
{
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP OP)
	{
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	public void print() {
		System.out.println("exp binop : ");
		left.print();
		OP.print();
		right.print();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table) {
		AST_TYPE leftType = left.calcType(table); 
		AST_TYPE rightType = right.calcType(table);
		if (leftType == null || rightType == null) {
			throw new RuntimeException("Cant make op on void exp");
		}
		if (!SemanticChecker.isBaseClassOf(leftType.getName(), rightType.getName())
				&& !SemanticChecker.isBaseClassOf(rightType.getName(), 
						leftType.getName())) {
			throw new RuntimeException("Incomparble types");
		}
		if (OP.getOp() == "EQUAL" || OP.getOp() == "NON EQUAL") {
			return new AST_TYPE_INT();
		}
		if (leftType instanceof AST_TYPE_INT && rightType instanceof AST_TYPE_INT) { // raw types
			return new AST_TYPE_INT();
		}
		if (leftType instanceof AST_TYPE_STRING && rightType instanceof AST_TYPE_STRING) {
			if (OP.getOp() == "PLUS") {
				return new AST_TYPE_STRING();
			}
		}
		throw new RuntimeException("can't invoke binary operation here " );
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		
	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		if (OP.getOp() == "PLUS") {
			return calcPlusOperator(table,genarator,fileName);
		}
		if (OP.getOp() == "MINUS") {
			return calcMinusOpeartor(table,genarator,fileName);
		}
		if (OP.getOp() == "TIMES") {
			return calcMultOpeartor(table,genarator,fileName);
		}
		if (OP.getOp() == "DIVIDE") {
			return calcDivideOperator(table,genarator,fileName);
		}
		else {
			return calcBinaryOperation(table,genarator,fileName);
		}
	}


	private TEMP calcBinaryOperation(SymbolTable table, CodeGenarator genarator, String fileName) {
		String cmd = "INITALVALUE";
		TEMP leftA = left.calcAddress(table, genarator, fileName);
		TEMP rightA = right.calcAddress(table, genarator, fileName);
		TEMP result = new TEMP();
		if (OP.getOp() == "GT") {
			cmd = MIPS_COMMANDS.SGT;
		}
		if (OP.getOp() == "GTOREQUAL") {
			cmd = MIPS_COMMANDS.SGE;		
		}
		if (OP.getOp() == "ST") {
			cmd = MIPS_COMMANDS.SLT;
		}
		if (OP.getOp() == "STOREQUAL") {
			cmd = MIPS_COMMANDS.SLE;
		}
		if (OP.getOp() == "EQUAL") {
			cmd = MIPS_COMMANDS.SEQ;
		}
		if (OP.getOp() == "NON EQUAL") {
			cmd = MIPS_COMMANDS.SNE;
		}
		CodeGenarator.printSETCommand(cmd, result.name, leftA.name, rightA.name);
		return result;
	}

	/**
	 * 
	 * ASSMEBLY CODE :
	 * beq $right $zero exit_label
	 * div $left $right
	 * mflo $result
	 */
	private TEMP calcDivideOperator(SymbolTable table, CodeGenarator genarator, String fileName) {
		TEMP leftA = left.calcAddress(table, genarator, fileName);
		TEMP rightA = right.calcAddress(table, genarator, fileName);
		TEMP result = new TEMP();
		CodeGenarator.printBEQCommand(rightA.name,MIPS_COMMANDS.ZERO
				,genarator.exitLabel.labelString);
		CodeGenarator.printDIVCommand(leftA.name, rightA.name);
		CodeGenarator.printMFLOCommand(result.name);
		return result;
	}

	/**
	 * 
	 * ASSEMBLY CODE
	 * mult $left $right
	 * mflo $result
	 */
	private TEMP calcMultOpeartor(SymbolTable table, CodeGenarator genarator, String fileName) {
		TEMP result = new TEMP();
		TEMP leftAddress = left.calcAddress(table, genarator, fileName);
		TEMP rightAddress = right.calcAddress(table, genarator, fileName);
		CodeGenarator.printMULTCommand(leftAddress.name, rightAddress.name);
		CodeGenarator.printMFLOCommand(result.name);
		return result;
	}

	/**
	 * CODE ASSEMBLY :
	 * li $temp1 -1
	 * mult $right $temp1
	 * mflo $newright
	 * add $result $left $newright
	 * 
	 * ref here : 
	 * http://stackoverflow.com/questions/16050338/mips-integer-multiplication-and-division
	 */
	private TEMP calcMinusOpeartor(SymbolTable table, CodeGenarator genarator, String fileName) {
		TEMP minusOne = new TEMP();
		TEMP newRight = new TEMP();
		TEMP result = new TEMP();
		TEMP leftAddress = left.calcAddress(table, genarator, fileName);
		TEMP rightAddress = right.calcAddress(table, genarator, fileName);
		CodeGenarator.printLICommand(minusOne.name, -1);
		CodeGenarator.printMULTCommand(rightAddress.name, minusOne.name);
		CodeGenarator.printMFLOCommand(newRight.name);
		CodeGenarator.printADDCommand(result.name, leftAddress.name, newRight.name);
		return result;
	}

	private TEMP calcPlusOperator(SymbolTable table, CodeGenarator genarator, String fileName) {
		if (left.calcType(table) instanceof AST_TYPE_INT)
			return calcPlusForIntegers(table,genarator,fileName);
		else
			return calcPlusForString(table,genarator,fileName);
	}
	/**
	 * CODE ASMEBLY : 
	 * add $result $left $right
	 * @param table
	 * @param genarator
	 * @param fileName
	 * @return address of $left+$right
	 */
	private TEMP calcPlusForIntegers(SymbolTable table, CodeGenarator genarator, String fileName) {
		TEMP addressLeft = left.calcAddress(table, genarator, fileName);
		TEMP addressRight = right.calcAddress(table, genarator, fileName);
		TEMP addressResult = new TEMP();
		CodeGenarator.printADDCommand(addressResult.name,addressLeft.name
				,addressRight.name); 
		return addressResult;
	}

	private TEMP calcPlusForString(SymbolTable table, CodeGenarator genarator, String fileName) {
		
		return null;
	}


	


}