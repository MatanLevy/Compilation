package AST;

public class AST_TYPE_BRACK extends AST_TYPE {

	public AST_TYPE getType() {
		return type;
	}

	public void setType(AST_TYPE type) {
		this.type = type;
	}

	private AST_TYPE type;

	public AST_TYPE_BRACK(AST_TYPE t) {
		this.type = t;
	}
	public AST_RAW_TYPE getRawType() {
		AST_TYPE finallyRaw = this.type;
		while (!(finallyRaw instanceof AST_RAW_TYPE))
			finallyRaw = ((AST_TYPE_BRACK)finallyRaw).type;
		return (AST_RAW_TYPE)finallyRaw;
	}
	
	public void print() {
		System.out.println("type brack : ");
		type.print();
	}

	@Override
	public String getName() {
		return "array" + type.getName();
	}

	@Override
	public boolean checkSemantic(SymbolTable table) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AST_TYPE_BRACK other = (AST_TYPE_BRACK) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
