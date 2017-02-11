package AST;

import java.util.ArrayList;
import java.util.List;

public class AST_FIELD_METHOD_LIST extends AST_Node {

	
	public List<AST_FIELD> field_list;
	public List<AST_METHOD> method_list;
	public String _className;
	
	
	public AST_FIELD_METHOD_LIST() {
		field_list = new ArrayList<>();
		method_list = new ArrayList<>();
	}
	
	
	public String get_className() {
		return _className;
	}


	public void set_className(String _className) {
		this._className = _className;
	}


	public void addField(AST_FIELD f) {
		field_list.add(f);
	}

	public void addMethod(AST_METHOD m) {
		method_list.add(m);
	}
	public void print() {
		System.out.println("field method list : ");
		for (AST_FIELD field : field_list) {
			field.print();
		}
		for (AST_METHOD method : method_list) {
			method.print();
		}
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean checkSemantic(SymbolTable table) {
		for (AST_FIELD field : field_list) {
			//table.pushScope(false, null);
			table.insertASTNode(field);
			if (!field.checkSemantic(table))
				return false;
		}
		for (AST_METHOD method : method_list) {
			table.insertASTNode(method);
			method.set_className(_className);
//			table.pushScope(false, null, method._id);
			if (!method.checkSemantic(table))
				return false;
			table.popScope();
			table.initConterOffsetWhenPopScopeOfMethod();
		}
		return true;
	}
	@Override
	public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {
		for (AST_FIELD field : field_list) {
			table.insertASTNode(field);
			field.mipsTranslate(table, assemblyFileName, genartor);
		}
		for (AST_METHOD method : method_list) {
			CodeGenarator.currentMethod  = method._id;
			table.insertASTNode(method);
			method.mipsTranslate(table, assemblyFileName, genartor);
			table.popScope();
			table.initCounterOffsetWhenPopScopeOfClass();
		}
		
	}

}
