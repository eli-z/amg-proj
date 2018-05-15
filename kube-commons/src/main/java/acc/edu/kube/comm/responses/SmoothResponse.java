package acc.edu.kube.comm.responses;

public class SmoothResponse {
	private double value;
	private long timeTook;
	
	public SmoothResponse(double value, long timeTook) {
		super();
		this.value = value;
		this.timeTook = timeTook;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public long getTimeTook() {
		return timeTook;
	}
	public void setTimeTook(long timeTook) {
		this.timeTook = timeTook;
	}
	public SmoothResponse() {
		super();
	}
	
	
}
