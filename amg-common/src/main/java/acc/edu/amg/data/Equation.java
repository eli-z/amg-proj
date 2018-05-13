package acc.edu.amg.data;

public class Equation {
	//a vector of the equation
	private double[] aVector;
	//a indexes of the equation
	private int[] iVector;
	//b of the equation
	private double b;
	//index of significant variable in the equation
	private int iIndex;
	
	public double[] getaVector() {
		return aVector;
	}
	public void setaVector(double[] aVector) {
		this.aVector = aVector;
	}
	public int[] getiVector() {
		return iVector;
	}
	public void setiVector(int[] iVector) {
		this.iVector = iVector;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public int getiIndex() {
		return iIndex;
	}
	public void setiIndex(int iIndex) {
		this.iIndex = iIndex;
	}
	
	
	
	
}
