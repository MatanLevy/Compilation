package AST;

public abstract class AST_RAW_TYPE extends AST_TYPE {
	
	protected String description;
	
	public void print() {
		System.out.println("raw type : ");
		System.out.println("desc = " + description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		AST_RAW_TYPE other = (AST_RAW_TYPE) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
	
	

}
