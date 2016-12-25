package AST;

public class AST_VAR_SIMPLE extends AST_VAR
{
	public String name;
	
	public AST_VAR_SIMPLE(String name)
	{
		this.name = name;
	}
	
	public void print() {
		System.out.println("var simple : " + name);
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AST_TYPE calcType(SymbolTable table) {
		if (table.find_symbol(name) == null) 
			throw new RuntimeException(name + " is not defined in this scope");
		if (!table.isSymbolInitalize(name))
			throw new RuntimeException(name + " is not initalized");
		return table.getTable().get(name).getFirst().getType();
	}
}