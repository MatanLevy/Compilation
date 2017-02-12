package AST;

import IR.TEMP;

public class AST_EXP_NEW_CLASS extends AST_EXP {
	
	String _className;
	public AST_EXP_NEW_CLASS(String className) {
		this._className = className;
		this.type = new AST_TYPE_CLASS(className);
		this.typeUptoDate = true;
	} 
	public void print() {
		System.out.println("exp new class : " + _className);
	}
	@Override
	public String getName() {
		return _className;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (!typeUptoDate) {
			type = new AST_TYPE_CLASS(_className);
			typeUptoDate = true;
		}
		return type;
	}
	
	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		
	}

	@Override
	public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
		int sizeToAllocOnHeap = table.returnTheSizeOfTheObjectFromClassTypeOnTheHeap(_className);
		TEMP returnAddress = genarator.AllocOnHeap(sizeToAllocOnHeap);
		if (VirtualTableManager.getListOfActualFunctionsByName(_className).size() != 0) {
			TEMP temp = new TEMP();
			CodeGenarator.printLACommand(temp.name, "VFTable_" + _className);
			CodeGenarator.printSWCommand(temp.name,returnAddress.name,0);
		}
		return returnAddress;
	}

}
