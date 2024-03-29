package cmg.org.monitor.module.client;

import cmg.org.monitor.util.shared.HTMLControl;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class MonitorAbout extends AncestorEntryPoint {

	FlexTable flexTable;

	protected void init() {
		if (currentPage == HTMLControl.PAGE_ABOUT) {
			monitorGwtSv.getAboutContent(new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					if (result != null) {
						addWidget(HTMLControl.ID_BODY_CONTENT, new HTML(result));
						setVisibleLoadingImage(false);
						setVisibleWidget(HTMLControl.ID_BODY_CONTENT, true);
						
					} else {
						showMessage("Oops! Error.", HTMLControl.HTML_DASHBOARD_NAME,
								"Goto Dashboard. ", HTMLControl.RED_MESSAGE, true);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					showMessage("Oops! Error.", HTMLControl.HTML_DASHBOARD_NAME,
							"Goto Dashboard. ", HTMLControl.RED_MESSAGE, true);
				}
			});

		}
	}
}
