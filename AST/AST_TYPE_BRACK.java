package AST;

public class AST_TYPE_BRACK extends AST_TYPE {

	private AST_TYPE type;

	public AST_TYPE_BRACK(AST_TYPE t) {
		this.type = t;
	}
	
	public void print() {
		System.out.println("type brack : ");
		type.print();
	}
	
	public AST_TYPE getType() {
		return type;
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
