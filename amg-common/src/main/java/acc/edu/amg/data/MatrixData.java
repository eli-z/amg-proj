package acc.edu.amg.data;

public class MatrixData {
	private Equation[] equations;
	private double[] bVector;
	public Equation[] getEquations() {
		return equations;
	}
	public void setEquations(Equation[] equations) {
		this.equations = equations;
	}
	public double[] getbVector() {
		return bVector;
	}
	public void setbVector(double[] bVector) {
		this.bVector = bVector;
	}
	
	public boolean validate() {
		return equations != null && bVector != null && equations.length == bVector.length;
	}
	
	public int dimention() {
		return bVector.length;
	}
}
