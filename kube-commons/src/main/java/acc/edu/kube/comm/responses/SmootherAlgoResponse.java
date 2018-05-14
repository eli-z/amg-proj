package acc.edu.kube.comm.responses;

import acc.edu.amg.data.MatrixData;

public class SmootherAlgoResponse {
	private int dimention;
	private int iterations;
	private MatrixData matrix;
	private double avgIterationsTime;
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
	public long getTimeTookMilli() {
		return timeTookMilli;
	}
	public void setTimeTookMilli(long timeTookMilli) {
		this.timeTookMilli = timeTookMilli;
	}
	public MatrixData getMatrix() {
		return matrix;
	}
	public void setMatrix(MatrixData matrix) {
		this.matrix = matrix;
	}
	public double getAvgIterationsTime() {
		return avgIterationsTime;
	}
	public void setAvgIterationsTime(double avgIterationsTime) {
		this.avgIterationsTime = avgIterationsTime;
	}
	
	
}
