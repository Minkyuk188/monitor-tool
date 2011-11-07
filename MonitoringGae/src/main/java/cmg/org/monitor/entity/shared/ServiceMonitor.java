package cmg.org.monitor.entity.shared;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Model/entity representation of a network.
 * @author lamphan
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class ServiceMonitor implements Model, IsSerializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String name;

	@Persistent
	private Date systemDate;

	@Persistent
	private int ping;

	@Persistent
	private boolean status;

	@Persistent
	private String description;

	@Persistent
	private Date timeStamp;
	
	@Persistent
	private SystemMonitor systemMonitor;
	/**
	 * Default constructor.<br>
	 */
	public ServiceMonitor() {
		
	}

	public ServiceMonitor(String name, Date systemDate, int ping,
			boolean status, String description, Date timeStamp) {
		super();

		this.name = name;
		this.systemDate = systemDate;
		this.ping = ping;
		this.status = status;
		this.description = description;
		this.timeStamp = timeStamp;
	}

	@Override
    public String getId() {
        return encodedKey;
    }
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public SystemMonitor getSystemMonitor() {
		return systemMonitor;
	}

	public void setSystemMonitor(SystemMonitor systemMonitor) {
		this.systemMonitor = systemMonitor;
	}

}