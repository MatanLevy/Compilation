package AST;

import IR.TEMP;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_EXP var;
	public AST_EXP subscript;
	
	public AST_VAR_SUBSCRIPT(AST_EXP e1, AST_EXP e2) {
		var = e1;
		subscript = e2;
	}
	
	public void print() {
		System.out.println("var subscript : ");
		var.print();
		subscript.print();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table, boolean needCheckInitialize) {
		AST_TYPE subtype = (subscript.typeUptoDate) ? subscript.type : 
			subscript.calcType(table);
		if (!(subtype instanceof AST_TYPE_INT))
			throw new RuntimeException("array index is not integer");
		AST_TYPE arrtype = (var.typeUptoDate) ? var.type : var.calcType(table);
		if (!(arrtype instanceof AST_TYPE_BRACK))
			throw new RuntimeException("cant invoke [ ] on raw type");
		return ((AST_TYPE_BRACK)arrtype).getType();
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TEMP calcAddress(SymbolTable table,CodeGenarator genarator,String fileName) {
		TEMP arrayAddressTemp = var.calcAddress(table, genarator, fileName);
		//CodeGenarator.printLACommand(arrayAddressTemp.name, var.getName());
		TEMP indexTemp = subscript.calcAddress(table, genarator, fileName);
		TEMP size = new TEMP();
		CodeGenarator.printLWCommand(size.name, arrayAddressTemp.name, 0);
		//if index lower than zero-exit
		TEMP zero = new TEMP();
		CodeGenarator.printLICommand(zero.name, 0);
		CodeGenarator.printSETCommand(MIPS_COMMANDS.BLT, zero.name, indexTemp.name,
				genarator.exitLabel.labelString);
		//if index greater than size-exit
		CodeGenarator.printSETCommand(MIPS_COMMANDS.BGE,  indexTemp.name, size.name, 
				genarator.exitLabel.labelString);
		//Double the index twice so we will get the address of the index*4. 
		//This is the address of index*4
		CodeGenarator.printADDCommand(indexTemp.name, indexTemp.name, indexTemp.name);
		CodeGenarator.printADDCommand(indexTemp.name, indexTemp.name, indexTemp.name);
		//add another 4 because the first address is the size
		CodeGenarator.printADDICommand(indexTemp.name, indexTemp.name, 4);
		TEMP addressTemp = new TEMP();
		// add the register of the array address with the register of the indexTemp.
		// we get the address of list[index]
		CodeGenarator.printADDCommand(addressTemp.name, indexTemp.name, arrayAddressTemp.name);
		return addressTemp;
	}
}

//Example of access to an array in specific index:

//la $t3, list         # put address of list into $t3
//li $t2, 6            # put the index into $t2
//add $t2, $t2, $t2    # double the index
//add $t2, $t2, $t2    # double the index again (now 4x)
//add $t1, $t2, $t3    # combine the two components of the address