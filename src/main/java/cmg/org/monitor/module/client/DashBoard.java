package cmg.org.monitor.module.client;

import cmg.org.monitor.entity.shared.SystemMonitor;
import cmg.org.monitor.ext.model.shared.UserLoginDto;
import cmg.org.monitor.util.shared.HTMLControl;
import cmg.org.monitor.util.shared.MonitorConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.formatters.BarFormat;
import com.google.gwt.visualization.client.formatters.BarFormat.Color;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

public class DashBoard implements EntryPoint {
	// Table of list system with google chart API
	static private Table tableListSystem;
	// Store data of list system
	static private DataTable dataListSystem;
	// Options of table list system
	static private Options opsTableListSystem;
	//
	private final DashBoardServiceAsync dashboardSv = GWT
			.create(DashBoardService.class);

	private static boolean isDone = false;

	private static int count = 0;

	Timer timerMess;
	Timer timerReload;

	@Override
	public void onModuleLoad() {
		dashboardSv.getUserLogin(new AsyncCallback<UserLoginDto>() {
			@Override
			public void onFailure(Throwable caught) {
				setVisibleLoadingImage(false);
				initMessage("Server error. ", HTMLControl.HTML_DASHBOARD_NAME,
						"Try again. ", HTMLControl.RED_MESSAGE);
				setVisibleMessage(true, HTMLControl.RED_MESSAGE);
			}
			@Override
			public void onSuccess(UserLoginDto result) {
				setVisibleLoadingImage(false);
				if (result != null) {
					if (result.isLogin()) {
						RootPanel.get("menuContent").add(
								HTMLControl.getMenuHTML(
										HTMLControl.DASHBOARD_PAGE,
										result.getRole()));
						RootPanel.get("nav-right")
								.add(HTMLControl.getLogoutHTML(result
										.getLogoutUrl(),result.getEmail()));
						if (result.getRole() == MonitorConstant.ROLE_GUEST) {
							RootPanel.get("page-heading").add(new HTML("<h1>Login</h1>"));
							initMessage(
									"Hello "
											+ result.getNickName()
											+ ". You might not have permission to use Monitor System. ",
									result.getLogoutUrl(),
									"Login with another account.",
									HTMLControl.YELLOW_MESSAGE);
							setVisibleMessage(true, HTMLControl.YELLOW_MESSAGE);
						} else {
							RootPanel.get("page-heading").add(new HTML("<h1>Dashboard</h1>"));
							initMessage(
									"Wellcome to Monitor System, "
											+ result.getNickName()
											+ ". If have any question. ",
									HTMLControl.HTML_ABOUT_NAME, "Contact Us.",
									HTMLControl.GREEN_MESSAGE);
							setVisibleMessage(true, HTMLControl.GREEN_MESSAGE);
							init();
						}
					} else {
						RootPanel.get("page-heading").add(new HTML("<h1>Login</h1>"));
						initMessage("Must login to use Monitor System. ", result.getLoginUrl(),
								"Login. ", HTMLControl.RED_MESSAGE);
						setVisibleMessage(true, HTMLControl.RED_MESSAGE);
					}
				} else {
					initMessage("Server error. ", HTMLControl.HTML_DASHBOARD_NAME,
							"Try again. ", HTMLControl.RED_MESSAGE);
					setVisibleMessage(true, HTMLControl.RED_MESSAGE);
				}
			}
		});
	}

	void init() {
		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {

				// Create table
				tableListSystem = new Table();
				// Create options and datatable of table list system
				createOptionsTableListSystem();
				RootPanel.get("statusMes").add(new HTML("<h3>Latest status of systems (update in every 30 minutes)</h3>"));
				
				// Add table to div id 'tableListSystem'
				RootPanel.get("tableListSystem").add(tableListSystem);
				// Create Timer object which auto refresh data table list system
				timerReload = new Timer() {
					@Override
					public void run() {
						callBack();
					}
				};
				timerReload.run();
				timerReload.scheduleRepeating(MonitorConstant.REFRESH_RATE);
			}

		};
		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	}

	public static void clear(Element parent) {
		Element firstChild;
		while ((firstChild = DOM.getFirstChild(parent)) != null) {
			DOM.removeChild(parent, firstChild);
		}
	}

	/*
	 * Create callback to server via RPC
	 */
	void callBack() {
		dashboardSv.listSystems(new AsyncCallback<SystemMonitor[]>() {
			@Override
			public void onFailure(Throwable caught) {
				initMessage("Server error. ", HTMLControl.HTML_DASHBOARD_NAME, "Try again.",
						HTMLControl.RED_MESSAGE);
				setVisibleMessage(true, HTMLControl.RED_MESSAGE);
				setVisibleLoadingImage(true);
				showReloadCountMessage(HTMLControl.YELLOW_MESSAGE);
			}

			@Override
			public void onSuccess(SystemMonitor[] result) {
				drawTable(result);
			}
		});
	}

	/*
	 * Draw table ui with result callback from server via RPC
	 */
	void drawTable(SystemMonitor[] result) {
		if (result != null) {
			createDataListSystem();
			dataListSystem.addRows(result.length);
			for (int i = 0; i < result.length; i++) {
				dataListSystem.setValue(i, 0, HTMLControl.getLinkSystemDetail(
						result[i].getId(), result[i].getCode()));
				dataListSystem.setValue(
						i,
						1,
						(result[i].getName() == null) ? "N/A" : result[i]
								.getName());
				dataListSystem.setValue(
						i,
						2,
						(result[i].getUrl() == null) ? "N/A" : result[i]
								.getUrl());
				dataListSystem
						.setValue(i, 3, (result[i].getIp() == null) ? "N/A"
								: result[i].getIp());
				dataListSystem.setValue(
						i,
						4,
						(result[i].getLastCpuMemory() == null) ? 0
								: (result[i].isActive()
										&& result[i].getStatus() ? result[i]
										.getLastCpuMemory().getCpuUsage() : 0));
				dataListSystem.setValue(
						i,
						5,
						(result[i].getLastCpuMemory() == null) ? 0
								: (result[i].isActive()
										&& result[i].getStatus() ? result[i]
										.getLastCpuMemory()
										.getPercentMemoryUsage() : 0));
				dataListSystem.setValue(i, 6, HTMLControl
						.getHTMLStatusImage(result[i].getHealthStatus()));
				dataListSystem.setValue(i, 7,
						HTMLControl.getHTMLActiveImage(result[i].isActive()));
			}
			createFormatDataTableListSystem();
			setVisibleMessage(false, HTMLControl.RED_MESSAGE);
			setVisibleLoadingImage(false);
			// draw table
			tableListSystem.draw(dataListSystem, opsTableListSystem);
		} else {
			initMessage("No system found. ",
					HTMLControl.HTML_ADD_NEW_SYSTEM_NAME, "Add new system.",
					HTMLControl.RED_MESSAGE);
			setVisibleMessage(true, HTMLControl.RED_MESSAGE);
			showReloadCountMessage(HTMLControl.YELLOW_MESSAGE);
			setVisibleLoadingImage(true);
		}
	}

	void showReloadCountMessage(final int typeMessage) {
		count = MonitorConstant.REFRESH_RATE / 1000;
		if (timerMess != null) {
			setVisibleMessage(false, typeMessage);
			timerMess.cancel();
		}
		initMessage("Reload table in " + HTMLControl.getStringTime(count),
				HTMLControl.HTML_DASHBOARD_NAME, "Reload now.", typeMessage);
		setVisibleMessage(true, typeMessage);
		timerMess = new Timer() {
			@Override
			public void run() {
				initMessage(
						"Reload table in " + HTMLControl.getStringTime(--count),
						HTMLControl.HTML_DASHBOARD_NAME, "Reload now.",
						typeMessage);
				if (count <= 0) {
					setVisibleMessage(false, typeMessage);
					this.cancel();
				}
			}
		};
		timerMess.run();
		timerMess.scheduleRepeating(1000);
	}

	void setVisibleLoadingImage(boolean b) {
		RootPanel.get("img-loading").setVisible(b);
	}

	void setVisibleMessage(boolean b, int type) {
		RootPanel.get("message-" + HTMLControl.getColor(type)).setVisible(b);
	}

	/*
	 * Show message with content
	 */
	void initMessage(String message, String url, String titleUrl, int type) {
		RootPanel.get("content-" + HTMLControl.getColor(type)).clear();
		RootPanel.get("content-" + HTMLControl.getColor(type)).add(
				new HTML(message
						+ ((url.trim().length() == 0) ? "" : ("  <a href=\""
								+ url + "\">" + titleUrl + "</a>")), true));
	}

	/*
	 * Create options of table list system
	 */
	void createOptionsTableListSystem() {
		opsTableListSystem = Options.create();
		opsTableListSystem.setAllowHtml(true);
		opsTableListSystem.setShowRowNumber(true);
	}

	/*
	 * Create data table list system without value
	 */
	void createDataListSystem() {
		// create object data table
		dataListSystem = DataTable.create();
		// add all columns
		dataListSystem.addColumn(ColumnType.STRING, "SID");
		dataListSystem.addColumn(ColumnType.STRING, "System");
		dataListSystem.addColumn(ColumnType.STRING, "URL");
		dataListSystem.addColumn(ColumnType.STRING, "IP");
		dataListSystem.addColumn(ColumnType.NUMBER, "CPU Usage (%)");
		dataListSystem.addColumn(ColumnType.NUMBER, "Memory Usage (%)");
		dataListSystem.addColumn(ColumnType.STRING, "Health Status");
		dataListSystem.addColumn(ColumnType.STRING, "Monitor Status");

	}

	void createFormatDataTableListSystem() {
		// create options of bar format (format columns 'CPU Usage' and 'Memory
		// Usage')
		com.google.gwt.visualization.client.formatters.BarFormat.Options ops = com.google.gwt.visualization.client.formatters.BarFormat.Options
				.create();
		ops.setColorPositive(Color.RED);
		ops.setColorNegative(Color.RED);
		ops.setMax(100);
		ops.setMin(0);
		ops.setWidth(200);
		BarFormat bf = BarFormat.create(ops);
		bf.format(dataListSystem, 4);
		bf.format(dataListSystem, 5);
	}
}
