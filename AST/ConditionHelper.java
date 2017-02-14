package AST;

import COUNTERS.LABEL;
import COUNTERS.TEMP;

public class ConditionHelper {

	
public static LABEL keepCondition = new LABEL("keep");
	
	public static LABEL eqlabel = new LABEL("when_equal");
	public static LABEL neqlabel = new LABEL("when_not_equal");
	public static LABEL gtlabel = new LABEL("when_gt_equal");
	public static LABEL geqlabel = new LABEL("when_ge_equal");
	public static LABEL ltlabel = new LABEL("when_lt_equal");
	public static LABEL leqlabel = new LABEL("when_leq_equal");
	public static TEMP left = new TEMP();
	public static TEMP right = new TEMP();
	public static TEMP result = new TEMP();
	
	
	public static void printAllLabels() {
		printKeepCond();
		printLabelForCondition(eqlabel.labelString, "bne", left.name, right.name, result.name);
		printLabelForCondition(neqlabel.labelString, "beq", left.name, right.name, result.name);
		printLabelForCondition(gtlabel.labelString, "ble", left.name, right.name, result.name);
		printLabelForCondition(geqlabel.labelString, "blt", left.name, right.name, result.name);
		printLabelForCondition(ltlabel.labelString, "bge", left.name, right.name, result.name);
		printLabelForCondition(leqlabel.labelString, "bgt", left.name, right.name, result.name);
		
	}
	public static void printKeepCond() {
		CodeGenarator.printLBLCommand(keepCondition.labelString);
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
	}
	public static void printLabelForCondition(String cond,String oppsite_condition,String r1,String r2,
			String tempvalue) {
		CodeGenarator.printLBLCommand(cond);
		System.out.format("\t%s %s, %s, %s%n",oppsite_condition, r1, r2,keepCondition.labelString);
		CodeGenarator.printLICommand(tempvalue, 1);
		CodeGenarator.printJRCommand(MIPS_COMMANDS.RA);
		
	}
}
