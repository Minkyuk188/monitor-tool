package cmg.org.monitor.module.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cmg.org.monitor.ext.model.shared.UserDto;
import cmg.org.monitor.util.shared.HTMLControl;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

public class UserManagement extends AncestorEntryPoint {
	static private Table myTable;

	protected void init() {
		if (currentPage == HTMLControl.PAGE_USER_MANAGEMENT) {
			myTable = new Table();
			addWidget(HTMLControl.ID_BODY_CONTENT, myTable);
			initContent();
		}
	}

	private void initContent() {
		monitorGwtSv.listUser(new AsyncCallback<Map<String, UserDto>>() {

			@Override
			public void onSuccess(Map<String, UserDto> result) {
				if (result != null) {
					setVisibleLoadingImage(false);
					setVisibleWidget(HTMLControl.ID_BODY_CONTENT, true);
					drawTable(result);					
				} else {
					showMessage("Oops! Error.",
							HTMLControl.HTML_DASHBOARD_NAME,
							"Goto Dashboard. ",
							HTMLControl.RED_MESSAGE, true);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				showMessage("Oops! Error.",
						HTMLControl.HTML_DASHBOARD_NAME,
						"Goto Dashboard. ",
						HTMLControl.RED_MESSAGE, true);
			}
		});
	}

	@SuppressWarnings({ "rawtypes" })
	private AbstractDataTable createData(Map<String, UserDto> listUser) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Username");
		data.addColumn(ColumnType.STRING, "Email");
		data.addColumn(ColumnType.STRING, "Group");
		data.addColumn(ColumnType.STRING, "Permission");
		data.addRows(listUser.size());
		Set set = listUser.entrySet();
		Iterator iter = set.iterator();
		ArrayList<UserDto> user = new ArrayList<UserDto>();		
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			UserDto u = (UserDto) entry.getValue();
			user.add(u);
		}
		
		ArrayList<UserDto> sortUser = sortByname(user);
		for(int j = 0; j < sortUser.size();j++){
			String permission = "N/A";
			if (sortUser.get(j).getGroup().contains("admin")) {
				permission = "Admin";
			} else if (sortUser.get(j).getGroup().startsWith("monitor")) {
				permission = "Normal user";
			}
			data.setValue(j, 0, sortUser.get(j).getUsername());
			data.setValue(j, 1, sortUser.get(j).getEmail());
			data.setValue(j, 2, sortUser.get(j).getGroup());
			data.setValue(j, 3, permission);
		}
		return data;
	}

	 public ArrayList<UserDto> sortByname(ArrayList<UserDto> user) {
	       for (int i = 1; i < user.size(); i++) {
	           int j;
	           UserDto val = user.get(i);
	           for (j = i-1; j > -1; j--) {
	        	   UserDto temp = user.get(j);
	                   if (temp.compareByName(val) <= 0) {
	                          break;
	                   }
	                   user.set(j+1, temp);
	            }
	           user.set(j+1, val);
	       }
	       return user;
	     }
	
	private Options option() {
		Options option = Options.create();
		option.setAllowHtml(true);
		option.setShowRowNumber(true);
		return option;

	}

	private void drawTable(Map<String, UserDto> listUser) {
		myTable.draw(createData(listUser), option());

	}
}
