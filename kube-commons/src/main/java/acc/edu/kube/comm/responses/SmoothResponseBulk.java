package acc.edu.kube.comm.responses;

public class SmoothResponseBulk {
	private SmoothResponse[] responces;
	private long timeTook;
	public SmoothResponse[] getResponces() {
		return responces;
	}
	public void setResponces(SmoothResponse[] responces) {
		this.responces = responces;
	}
	public long getTimeTook() {
		return timeTook;
	}
	public void setTimeTook(long timeTook) {
		this.timeTook = timeTook;
	}
}
