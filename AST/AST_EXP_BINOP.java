package AST;

import COUNTERS.LABEL;
import COUNTERS.TEMP;

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
//		AST_TYPE leftType = left.calcType(table); 
//		AST_TYPE rightType = right.calcType(table);
//		if (leftType instanceof AST_TYPE_STRING && rightType instanceof AST_TYPE_STRING && OP.getOp() == "PLUS") {
//			
//		}
		
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
				/*String leftStr=null;
				String rightStr=null;
				if (left instanceof AST_EXP_VAR) {
					AST_EXP_VAR left_dummy = (AST_EXP_VAR) left;
					leftStr = left_dummy.var.getName();
				}
				else if (left instanceof AST_EXP_LITERAL){
					AST_EXP_LITERAL left_dummy = (AST_EXP_LITERAL) left;
					leftStr = left_dummy.getName();
					
				}
				if (right instanceof AST_EXP_VAR) {
					AST_EXP_VAR right_dummy = (AST_EXP_VAR) right;
					rightStr = right_dummy.var.getName();
				}
				else if (right instanceof AST_EXP_LITERAL){
					AST_EXP_LITERAL right_dummy = (AST_EXP_LITERAL) right;
					rightStr = right_dummy.getName();
					
				}
				if (leftStr != null && rightStr!=null)
				{
					//delete unnecassary ""\"
					leftStr = leftStr.replace("\"", "");
					rightStr = rightStr.replace("\"", "");

					String concate = "\"" + leftStr+rightStr + "\"";
					STRING_LABEL dummy_label = new STRING_LABEL(concate, true);

				}*/
				
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
		TEMP leftA = left.calcAddress(table, genarator, fileName);
		TEMP rightA = right.calcAddress(table, genarator, fileName);
		TEMP result = new TEMP();
		CodeGenarator.printADDICommand(ConditionHelper.left.name, leftA.name, 0);
		CodeGenarator.printADDICommand(ConditionHelper.right.name, rightA.name, 0);
		CodeGenarator.printLICommand(ConditionHelper.result.name, 0);
		if (OP.getOp() == "GT") {
			CodeGenarator.printJALCommand(ConditionHelper.gtlabel.labelString);
		}
		if (OP.getOp() == "GTOREQUAL") {
			CodeGenarator.printJALCommand(ConditionHelper.geqlabel.labelString);		
		}
		if (OP.getOp() == "ST") {
			CodeGenarator.printJALCommand(ConditionHelper.ltlabel.labelString);
		}
		if (OP.getOp() == "STOREQUAL") {
			CodeGenarator.printJALCommand(ConditionHelper.leqlabel.labelString);
		}
		if (OP.getOp() == "EQUAL") {
				CodeGenarator.printJALCommand(ConditionHelper.eqlabel.labelString);
		}
		if (OP.getOp() == "NON EQUAL") {
			CodeGenarator.printJALCommand(ConditionHelper.neqlabel.labelString);
		}
		CodeGenarator.printADDICommand(result.name, ConditionHelper.result.name, 0);
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
		TEMP zero = new TEMP();
		CodeGenarator.printLICommand(zero.name, 0);
		CodeGenarator.printBEQCommand(rightA.name,zero.name
				,genarator.exitLabel.labelString);
		CodeGenarator.printDIVCommand(leftA.name, rightA.name);
		//CodeGenarator.printMFLOCommand(result.name);
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
		CodeGenarator.printMULCommand(result.name,leftAddress.name, rightAddress.name);
		//CodeGenarator.printMFLOCommand(result.name);
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
		CodeGenarator.printMULCommand(newRight.name,rightAddress.name, minusOne.name);
		//CodeGenarator.printMFLOCommand(newRight.name);
		CodeGenarator.printADDCommand(result.name, leftAddress.name, newRight.name);
		return result;
	}

	private TEMP calcPlusOperator(SymbolTable table, CodeGenarator genarator, String fileName) {
		if (left.calcType(table) instanceof AST_TYPE_INT)
			return calcPlusForIntegers(table,genarator,fileName);
		else{
			return calcPlusForString(table,genarator,fileName);
	}}
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
		TEMP result = new TEMP();
		TEMP addressLeft = left.calcAddress(table, genarator, fileName);
		TEMP addressRight = right.calcAddress(table, genarator, fileName);
		
		CodeGenarator.printADDICommand(MIPS_COMMANDS.T3, addressLeft.name, 0);
		CodeGenarator.printADDICommand(MIPS_COMMANDS.T4, addressRight.name, 0);
		CodeGenarator.printJALCommand(CodeGenarator.concat_strings.labelString);
		

		CodeGenarator.printADDICommand(result.name, MIPS_COMMANDS.T5, 0);
		return result;
	}

}