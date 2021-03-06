package AST;

import java.util.ArrayList;

public class AST_STMT_LIST extends AST_Node
{
	public ArrayList<AST_STMT> list;


	public AST_STMT_LIST () {
		list = new ArrayList<AST_STMT>();
	}
	
	public void addStmt (AST_STMT stmt) {
		list.add(stmt);

	}
	public void print() {
		System.out.println("list of stmt : ");
		for (AST_STMT stmt : list) {
			stmt.print();
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		for (AST_STMT stmt : list) {
			if (!stmt.checkSemantic(table))
				return false;
		}
		return true;
	}

	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		for (AST_STMT stmt : list)
			stmt.mipsTranslate(table, assemblyFileName, genartor);
	}
	
}