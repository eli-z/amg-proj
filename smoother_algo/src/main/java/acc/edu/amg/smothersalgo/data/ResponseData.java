package acc.edu.amg.smothersalgo.data;

public class ResponseData {
	private int dimention;
	private int iterations;
	private double[] xVector;
	private long timeTookMilli;
	public int getDimention() {
		return dimention;
	}
	public void setDimention(int dimention) {
		this.dimention = dimention;
	}
	public int getIterations() {
		return iterations;
	}
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	public double[] getxVector() {
		return xVector;
	}
	public void setxVector(double[] xVector) {
		this.xVector = xVector;
	}
	public long getTimeTookMilli() {
		return timeTookMilli;
	}
	public void setTimeTookMilli(long timeTookMilli) {
		this.timeTookMilli = timeTookMilli;
	}
	
	
}
