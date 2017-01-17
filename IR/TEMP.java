package IR;

public class TEMP {
	public int SerialNumber;
	public String name;
	static int counter = 1;
	
	public TEMP() {
		SerialNumber = counter;
		name = "Temp_" + Integer.toString(counter);
		counter++;
	}
	
	// c'tor to create fp.
	public TEMP(boolean isFp) {
		if (isFp) {
			name = "fp";
			SerialNumber = 0;
		}
	}
	@Override
	public String toString() {
		return name;
	}

}
