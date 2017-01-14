package IR;

public class LABEL {
	public String labelString;
	static int counter = 0;


	public LABEL(String labelString) {
		this.labelString = "Label_" + Integer.toString(counter) + "_" + labelString;
		counter++;
	}
	

}
