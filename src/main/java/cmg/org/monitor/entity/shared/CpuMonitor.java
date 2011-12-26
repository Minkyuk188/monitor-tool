package cmg.org.monitor.entity.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CpuMonitor implements Serializable {
	private int cpuUsage;

	private int totalCpu;

	private String vendor;

	private String model;

	private Date timeStamp;
	
	public CpuMonitor() {
		cpuUsage = 0;
	}
	
	public int getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(int cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public int getTotalCpu() {
		return totalCpu;
	}

	public void setTotalCpu(int totalCpu) {
		this.totalCpu = totalCpu;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
