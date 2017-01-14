package IR;

public class TEMP {
	public int SerialNumber;
	public String name;
	static int counter = 0;
	
	public TEMP() {
		SerialNumber = counter;
		name = "Temp_" + Integer.toString(counter);
		counter++;
	}
	
	
	

}
