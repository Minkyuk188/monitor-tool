package cmg.org.monitor.dao;

import cmg.org.monitor.entity.shared.ServiceMonitor;
import cmg.org.monitor.entity.shared.SystemMonitor;

public interface ServiceMonitorDAO {
	void addServiceMonitor(SystemMonitor system, ServiceMonitor serviceMonitor);
}