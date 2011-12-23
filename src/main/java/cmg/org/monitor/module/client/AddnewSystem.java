package cmg.org.monitor.module.client;

import cmg.org.monitor.entity.shared.SystemMonitor;
import cmg.org.monitor.util.shared.HTMLControl;
import cmg.org.monitor.util.shared.MonitorConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class AddnewSystem extends AncestorEntryPoint {
	ListBox listGroup;
	TextBox txtName;
	TextBox txtURL;
	TextBox txtIP;
	TextBox txtRemote;
	TextBox txtEmail;
	ListBox listActive;
	ListBox listProtocol;
	Button bttCreate;
	Button bttBack;
	Button bttReset;
	Label labelName;
	Label labelurl;
	Label labelip;
	Label labelremoteurl;
	Label labelactive;
	Label labelprotocol;
	Label labelmailgroup;
	Label labeladdnew;
	Label labelEmail;
	AbsolutePanel panelAdding;
	AbsolutePanel panelValidateName;
	AbsolutePanel panelValidateURL;
	AbsolutePanel panelValidateIP;
	AbsolutePanel panelValidateRemoteURL;
	AbsolutePanel panelButton;
	AbsolutePanel panelValidateEmail;
	private static FlexTable tableForm;
	AbsolutePanel panelLabelEmail;
	AbsolutePanel panelTextEmail;
	AbsolutePanel panelValidateRemoteServer;
	AbsolutePanel panelValidateEmailServer;

	@Override
	protected void init() {
		if (currentPage == HTMLControl.PAGE_ADD_SYSTEM) {
			initFlextTable();
		}
	}

	/**
	 * @param active
	 * @return
	 */
	private boolean isActive(String active) {
		boolean isActive = false;
		if (active.equals("Yes")) {
			isActive = true;
		}
		return isActive;
	}

	/**
	 * @param email
	 * @return
	 */

	private String validateEmail(String email) {
		String msg = "";
		if (email == null || email == "") {
			msg = "This field is required";
		}
		String pattern = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		RegExp regExp = RegExp.compile(pattern);
		boolean matchFound = regExp.test(email);
		if (matchFound == false) {
			msg = "email is not validate";
		}
		return msg;
	}

	/**
	 * @param name
	 * @return
	 */
	private String validateName(String name) {
		String msg = "";
		if (name == null || name.trim().length() == 0) {
			msg = "This field is required ";
		} else if (name.contains("$") || name.contains("%")
				|| name.contains("*")) {
			msg = "name is not validate";
		}

		return msg;

	}

	/**
	 * @param url
	 * @return
	 */
	private String validateURL(String url) {
		String msg = "";
		if (url == null || url.trim().length() == 0) {
			msg = "This field is required ";
		} else if (url.length() < 3) {
			msg = "URL is not validate";
		}
		return msg;
	}

	/**
	 * @param remoteUrl
	 * @return
	 */
	private String validateRemoteURL(String remoteUrl) {
		String msg = "";
		if (remoteUrl == null || remoteUrl.trim().length() == 0) {
			msg += "This field is required ";
		} else if (remoteUrl.length() < 3) {
			msg += "Remote url is not validate";
		}
		return msg;
	}

	/**
	 * @param ip
	 * @return
	 */
	private String validateIP(String ip) {
		String msg = "";
		if (ip == "" || ip == null) {
			msg = "This field is required";
		}
		String patternStr = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
		RegExp regExp = RegExp.compile(patternStr);
		boolean matchFound = regExp.test(ip);
		if (matchFound == false) {
			msg = "ip is not validate";
		}
		return msg;
	}

	protected void initFlextTable() {
		monitorGwtSv.groups(new AsyncCallback<String[]>() {
			@Override
			public void onSuccess(String[] result) {
				// TODO Auto-generated method stub
				tableForm = new FlexTable();
				tableForm.setCellPadding(3);
				tableForm.setCellSpacing(3);
				tableForm.getFlexCellFormatter().setWidth(0, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(1, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(2, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(3, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(4, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(5, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(6, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(7, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(8, 0, "100px");
				tableForm.getFlexCellFormatter().setWidth(9, 0, "100px");

				labelName = new Label();
				labelName.setText("Name");

				labelurl = new Label();
				labelurl.setText("URL");

				labelip = new Label();
				labelip.setText("IP");

				labelremoteurl = new Label();
				labelremoteurl.setText("Remote-URL");

				labelactive = new Label();
				labelactive.setText("Active");

				labelprotocol = new Label();
				labelprotocol.setText("Protocol");

				labelEmail = new Label();
				labelEmail.setText("Email");

				labelmailgroup = new Label();
				labelmailgroup.setText("Notification mail group");

				txtName = new TextBox();
				txtName.setWidth("196px");
				txtName.setHeight("30px");

				txtURL = new TextBox();
				txtURL.setWidth("196px");
				txtURL.setHeight("30px");

				txtIP = new TextBox();
				txtIP.setWidth("196px");
				txtIP.setHeight("30px");

				txtRemote = new TextBox();
				txtRemote.setWidth("196px");
				txtRemote.setHeight("30px");

				txtEmail = new TextBox();
				txtEmail.setWidth("196px");
				txtEmail.setHeight("30px");

				listActive = new ListBox();
				listActive.setWidth("198px");
				listActive.setHeight("28px");
				listActive.setSelectedIndex(0);
				listActive.addItem("Yes");
				listActive.addItem("No");

				listProtocol = new ListBox();
				listProtocol.setWidth("198px");
				listProtocol.setHeight("28px");
				listProtocol.setSelectedIndex(0);
				listProtocol.addItem(MonitorConstant.HTTP_PROTOCOL);
				listProtocol.addItem(MonitorConstant.SMTP_PROTOCOL);

				listGroup = new ListBox();
				listGroup.setWidth("198px");
				listGroup.setHeight("28px");
				for (int i = 0; i < result.length; i++) {
					listGroup.addItem(result[i]);
				}
				listGroup.setSelectedIndex(0);

				bttCreate = new Button();
				bttCreate.setText("Create");
				bttCreate.setStyleName("margin:6px;");
				bttCreate.addStyleName("form-button");
				
				bttReset = new Button();
				bttReset.setText("Reset");
				bttReset.setStyleName("margin:6px;");
				bttReset.addStyleName("form-button");

				bttBack = new Button();
				bttBack.setText("Back");
				bttBack.setStyleName("margin:6px;");
				bttBack.addStyleName("form-button");
				

				panelLabelEmail = new AbsolutePanel();
				panelLabelEmail.add(labelEmail);
				panelLabelEmail.setVisible(false);

				panelTextEmail = new AbsolutePanel();
				panelTextEmail.add(txtEmail);
				panelTextEmail.setVisible(false);

				panelButton = new AbsolutePanel();
				panelButton.add(bttCreate);
				panelButton.add(bttReset);
				panelButton.add(bttBack);

				panelAdding = new AbsolutePanel();
				panelAdding
						.add(new HTML(
								"<div id=\"img-adding\"><img src=\"images/icon/loading11.gif\"/></div>"));
				panelAdding.setVisible(false);

				panelValidateEmailServer = new AbsolutePanel();
				panelValidateEmailServer
						.add(new HTML(
								"<div class=\"error-left\"></div><div class=\"error-inner\">Email is exitsting</div>"));
				panelValidateEmailServer.setVisible(false);

				panelValidateRemoteServer = new AbsolutePanel();
				panelValidateRemoteServer
						.add(new HTML(
								"<div class=\"error-left\"></div><div class=\"error-inner\">Remote URL is exitsting</div>"));
				panelValidateRemoteServer.setVisible(false);

				panelValidateIP = new AbsolutePanel();
				panelValidateEmail = new AbsolutePanel();
				panelValidateName = new AbsolutePanel();
				panelValidateURL = new AbsolutePanel();
				panelValidateRemoteURL = new AbsolutePanel();
				panelValidateName.setVisible(false);
				panelValidateIP.setVisible(false);
				panelValidateURL.setVisible(false);
				panelValidateRemoteURL.setVisible(false);
				panelValidateEmail.setVisible(false);

				MyHandler handler = new MyHandler();
				bttCreate.addClickHandler(handler);
				myReset resetHandler = new myReset();
				bttReset.addClickHandler(resetHandler);
				myBack backHandler = new myBack();
				bttBack.addClickHandler(backHandler);
				myProtocol actionProtocol = new myProtocol();
				listProtocol.addClickHandler(actionProtocol);

				tableForm.setWidget(0, 0, labelName);
				tableForm.setWidget(0, 1, txtName);
				tableForm.setWidget(0, 2, panelValidateName);
				tableForm.setWidget(1, 0, labelurl);
				tableForm.setWidget(1, 1, txtURL);
				tableForm.setWidget(1, 2, panelValidateURL);
				tableForm.setWidget(2, 0, labelip);
				tableForm.setWidget(2, 1, txtIP);
				tableForm.setWidget(2, 2, panelValidateIP);
				tableForm.setWidget(3, 0, labelactive);
				tableForm.setWidget(3, 1, listActive);
				tableForm.setWidget(4, 0, labelprotocol);
				tableForm.setWidget(4, 1, listProtocol);
				tableForm.setWidget(5, 0, panelLabelEmail);
				tableForm.setWidget(5, 1, panelTextEmail);
				tableForm.setWidget(5, 2, panelValidateEmail);
				tableForm.setWidget(5, 3, panelValidateEmailServer);
				tableForm.setWidget(6, 0, labelmailgroup);
				tableForm.setWidget(6, 1, listGroup);
				tableForm.setWidget(7, 0, labelremoteurl);
				tableForm.setWidget(7, 1, txtRemote);
				tableForm.setWidget(7, 2, panelValidateRemoteURL);
				tableForm.setWidget(7, 3, panelValidateRemoteServer);
				tableForm.getFlexCellFormatter().setColSpan(8, 0, 2);
				tableForm.setWidget(8, 0, panelAdding);
				tableForm.getFlexCellFormatter().setColSpan(9, 0, 3);
				tableForm.setWidget(9, 0, panelButton);
				addWidget(HTMLControl.ID_BODY_CONTENT, tableForm);
				setVisibleLoadingImage(false);
				setOnload(false);
				setVisibleWidget(HTMLControl.ID_BODY_CONTENT, true);

			}

			@Override
			public void onFailure(Throwable caught) {
				showMessage("Oops! Error.",
						HTMLControl.HTML_SYSTEM_MANAGEMENT_NAME,
						"Goto System Management. ", HTMLControl.RED_MESSAGE,
						true);
			}
		});

	}

	/**
	 * @author NDC
	 * 
	 */
	class MyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (listProtocol.getItemText(listProtocol.getSelectedIndex())
					.equals(MonitorConstant.HTTP_PROTOCOL)) {
				String validateName = validateName(txtName.getText());
				String validateURL = validateURL(txtURL.getText());
				String validateIp = validateIP(txtIP.getText());
				String validateRemoteURL = validateRemoteURL(txtRemote
						.getText());
				panelValidateEmail.setVisible(false);
				panelValidateName.setVisible(false);
				panelValidateRemoteURL.setVisible(false);
				panelValidateIP.setVisible(false);
				panelValidateRemoteURL.setVisible(false);
				if (validateName != "") {
					panelValidateName.clear();
					panelValidateName.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateName + "</div>"));
					panelValidateName.setVisible(true);
					panelValidateURL.setVisible(false);
					panelValidateIP.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateURL != "") {
					panelValidateURL.clear();
					panelValidateURL.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateURL + "</div>"));
					panelValidateURL.setVisible(true);
					panelValidateName.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateIP.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateIp != "") {
					panelValidateIP.clear();
					panelValidateIP.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateIp + "</div>"));
					panelValidateIP.setVisible(true);
					panelValidateName.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateURL.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateRemoteURL != "") {
					if (!listProtocol.getItemText(
							listProtocol.getSelectedIndex()).equals("SMTP")) {
						panelValidateRemoteURL.clear();
						panelValidateRemoteURL.add(new HTML(
								"<div class=\"error-left\"></div><div class=\"error-inner\">"
										+ validateRemoteURL + "</div>"));
						panelValidateRemoteURL.setVisible(true);
						panelValidateIP.setVisible(false);
						panelValidateName.setVisible(false);
						panelValidateURL.setVisible(false);
						panelValidateEmail.setVisible(false);
						return;
					}
				}
				panelValidateRemoteURL.setVisible(false);
				panelValidateIP.setVisible(false);
				panelValidateName.setVisible(false);
				panelValidateURL.setVisible(false);
				panelValidateEmail.setVisible(false);
				panelValidateEmailServer.setVisible(false);
				panelValidateRemoteServer.setVisible(false);
				SystemMonitor system = new SystemMonitor();
				system.setName(txtName.getText().toString());
				system.setUrl(txtURL.getText().toString());
				system.setActive(isActive(listActive.getValue(listActive
						.getSelectedIndex())));
				system.setProtocol(listProtocol.getValue(listProtocol
						.getSelectedIndex()));
				system.setGroupEmail(listGroup.getItemText(listGroup
						.getSelectedIndex()));
				system.setIp(txtIP.getText());
				system.setRemoteUrl(txtRemote.getText());
				panelAdding.setVisible(true);
				sendData(system, txtURL.getText());

			} else if (listProtocol
					.getItemText(listProtocol.getSelectedIndex()).equals(
							MonitorConstant.SMTP_PROTOCOL)) {
				String validateName = validateName(txtName.getText());
				String validateURL = validateURL(txtURL.getText());
				String validateIp = validateIP(txtIP.getText());
				String validateEmail = validateEmail(txtEmail.getText());
				panelValidateEmail.setVisible(false);
				panelValidateName.setVisible(false);
				panelValidateRemoteURL.setVisible(false);
				panelValidateIP.setVisible(false);
				panelValidateRemoteURL.setVisible(false);
				if (validateName != "") {
					panelValidateName.clear();
					panelValidateName.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateName + "</div>"));
					panelValidateName.setVisible(true);
					panelValidateURL.setVisible(false);
					panelValidateIP.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateURL != "") {
					panelValidateURL.clear();
					panelValidateURL.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateURL + "</div>"));
					panelValidateURL.setVisible(true);
					panelValidateName.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateIP.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateIp != "") {
					panelValidateIP.clear();
					panelValidateIP.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateIp + "</div>"));
					panelValidateIP.setVisible(true);
					panelValidateName.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateURL.setVisible(false);
					panelValidateEmail.setVisible(false);
					return;
				} else if (validateEmail != "") {
					panelValidateEmail.clear();
					panelValidateEmail.add(new HTML(
							"<div class=\"error-left\"></div><div class=\"error-inner\">"
									+ validateEmail + "</div>"));
					panelValidateEmail.setVisible(true);
					panelValidateIP.setVisible(false);
					panelValidateName.setVisible(false);
					panelValidateRemoteURL.setVisible(false);
					panelValidateURL.setVisible(false);
					return;
				}
				panelValidateRemoteURL.setVisible(false);
				panelValidateIP.setVisible(false);
				panelValidateName.setVisible(false);
				panelValidateURL.setVisible(false);
				panelValidateEmail.setVisible(false);
				panelValidateEmailServer.setVisible(false);
				panelValidateRemoteServer.setVisible(false);
				SystemMonitor system = new SystemMonitor();
				system.setName(txtName.getText().toString());
				system.setUrl(txtURL.getText().toString());
				system.setActive(isActive(listActive.getValue(listActive
						.getSelectedIndex())));
				system.setProtocol(listProtocol.getValue(listProtocol
						.getSelectedIndex()));
				system.setGroupEmail(listGroup.getItemText(listGroup
						.getSelectedIndex()));
				system.setIp(txtIP.getText());
				system.setEmail(txtEmail.getText());
				panelAdding.setVisible(true);
				sendData(system, txtURL.getText());
			}
		}

		/**
		 * @param system
		 * @param url
		 */
		private void sendData(SystemMonitor system, String url) {
			monitorGwtSv.addSystem(system, url, new AsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					panelAdding.setVisible(false);
					if (result.equals("Email is existing")) {
						panelValidateEmailServer.setVisible(true);
					} else if (result.equals("Remote-URL is existing")) {
						panelValidateRemoteServer.setVisible(true);
					} else if (result.toLowerCase().trim().equals("done")) {
						showMessage("System added sucessfully. ",
								HTMLControl.HTML_SYSTEM_MANAGEMENT_NAME,
								"View system list. ", HTMLControl.RED_MESSAGE,
								true);
					} else {
						showMessage(result, HTMLControl.HTML_DASHBOARD_NAME,
								"Goto Dashboard. ", HTMLControl.RED_MESSAGE,
								true);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					showMessage("Oops! Error.",
							HTMLControl.HTML_DASHBOARD_NAME,
							"Goto Dashboard. ", HTMLControl.RED_MESSAGE, true);
				}
			});
		}
	}

	/**
	 * @author NDC
	 * 
	 */
	class myReset implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			txtName.setText("");
			txtIP.setText("");
			txtRemote.setText("");
			txtURL.setText("");
			listActive.setSelectedIndex(0);
			listGroup.setSelectedIndex(0);
			listProtocol.setSelectedIndex(0);
			txtRemote.setEnabled(true);
			panelLabelEmail.setVisible(false);
			panelTextEmail.setVisible(false);
			txtEmail.setText("");
			panelValidateEmail.setVisible(false);
			panelValidateRemoteURL.setVisible(false);
			panelValidateIP.setVisible(false);
			panelValidateName.setVisible(false);
			panelValidateURL.setVisible(false);
			panelValidateEmailServer.setVisible(false);
			panelValidateRemoteServer.setVisible(false);
		}
	}

	/**
	 * @author NDC
	 * 
	 */
	class myBack implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Window.Location.replace(HTMLControl.trimHashPart(Window.Location
					.getHref()) + HTMLControl.HTML_SYSTEM_MANAGEMENT_NAME);
		}
	}

	/**
	 * @author NDC
	 * 
	 */
	class myProtocol implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if (listProtocol.getSelectedIndex() == 0) {
				txtRemote.setEnabled(true);
				panelLabelEmail.setVisible(false);
				panelTextEmail.setVisible(false);
				txtEmail.setText("");
			} else if (listProtocol.getSelectedIndex() == 1) {
				txtRemote.setText("");
				txtRemote.setEnabled(false);
				panelLabelEmail.setVisible(true);
				panelTextEmail.setVisible(true);
			}

		}

	}
}
