package com.demo.drools.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DroolsDemo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final DroolsExpertServiceAsync droolsExpertServiceAsync = GWT
			.create(DroolsExpertService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		final Label lblUserType = new Label("Select User Type");
		final Label lblSeeWhatYouCanInput = new Label("See What You Can Input");
		final Button btnRndSelectUserType = new Button("Randomly Select");
//		final Button btnRndAssignFunc = new Button("Randomly Select");
//		final Button btnSeeTestsAssigned = new Button("See Tests Assigned");

		// final TextBox nameField = new TextBox();
		final VerticalPanel verticalPanel1 = new VerticalPanel();
		final VerticalPanel verticalPanel2 = new VerticalPanel();

		final RadioButton rbUserType1 = new RadioButton("userType", "Type 1");
		rbUserType1.setTitle("Basic User");
		
		final RadioButton rbUserType2 = new RadioButton("userType", "Type 2");
		rbUserType2.setTitle("Power User");
		
		final RadioButton rbUserType3 = new RadioButton("userType", "Type 3");
		rbUserType3.setTitle("Admin User");

		// final CheckBox cbMachineFuncDDNS = new CheckBox("DDNS Server");
		// final CheckBox cbMachineFuncDNS = new CheckBox("DNS Server");
		// final CheckBox cbMachineFuncGateway = new CheckBox("Gateway");
		// final CheckBox cbMachineFuncRouter = new CheckBox("Router");

		final Label lblMessage = new Label();
//		final Label lblDueDate = new Label("None");

		verticalPanel1.add(rbUserType1);
		verticalPanel1.add(rbUserType2);
		verticalPanel1.add(rbUserType3);

		// verticalPanel2.add(cbMachineFuncDDNS);
		// verticalPanel2.add(cbMachineFuncDNS);
		// verticalPanel2.add(cbMachineFuncGateway);
		// verticalPanel2.add(cbMachineFuncRouter);

		flexCellFormatter.setColSpan(2, 0, 3);

		flexTable.setWidget(0, 0, lblUserType);
		flexTable.setWidget(0, 1, verticalPanel1);
		flexTable.setWidget(0, 2, btnRndSelectUserType);
		flexTable.setWidget(1, 0, lblSeeWhatYouCanInput);
		flexTable.setWidget(1, 1, verticalPanel2);
		flexTable.setWidget(1, 2, btnRndAssignFunc);
		flexTable.setWidget(2, 0, btnSeeTestsAssigned);

		btnSeeTestsAssigned.setSize("100%", "100%");
		btnRndSelectUserType.setSize("100%", "100%");
		btnRndAssignFunc.setSize("100%", "100%");

		flexTable.setBorderWidth(1);

		RootPanel.get().add(flexTable);

		btnSeeTestsAssigned.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String serialNum = String.valueOf((int) (Math.random() * 10));

				String machineType = null;
				List machineFuncs = new ArrayList();

				if (rbUserType1.getValue()) {
					machineType = "Type 1";
				}
				if (rbUserType2.getValue()) {
					machineType = "Type 2";
				}

				if (cbMachineFuncDDNS.getValue()) {
					machineFuncs.add("DDNS");
				}
				if (cbMachineFuncDNS.getValue()) {
					machineFuncs.add("DNS");
				}
				if (cbMachineFuncGateway.getValue()) {
					machineFuncs.add("Gateway");
				}
				if (cbMachineFuncRouter.getValue()) {
					machineFuncs.add("Router");
				}

				HashMap inputParams = new HashMap();
				HashMap outputParams = new HashMap();

				inputParams.put("thisMachineSerial", serialNum);
				inputParams.put("thisMachineType", machineType);
				inputParams.put("thisMachineFuncs", machineFuncs);

				droolsExpertServiceAsync.getTestsAssignedData(inputParams,
						new AsyncCallback() {

							@Override
							public void onSuccess(Object result) {
								HashMap outputParams = (HashMap) result;
								Collection<String> testsAssigned = (Collection<String>) outputParams
										.get("testsAssigned");
								String dueDate = (String) outputParams
										.get("dueDate");
								for (Iterator iterator = testsAssigned
										.iterator(); iterator.hasNext();) {
									String thisTestName = (String) iterator
											.next();
									RootPanel.get("testsAssignedContainer")
											.add(new Label(thisTestName));
								}
								RootPanel.get("testsDueDateContainer").add(
										new Label(dueDate));
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Some failure on backend: "
										+ caught.fillInStackTrace());
							}

						});
			}
		});

		btnRndSelectUserType.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int selectedType = ((int) (Math.random() + 0.5)) + 1; // To
				// select
				// a
				// random
				// integer
				// from
				// 1 and
				// 2.

				switch (selectedType) {
				case 1:
					rbUserType1.setValue(true);
					break;
				case 2:
					rbUserType2.setValue(true);
					break;
				default:
					try {
						throw new Exception("Unexpected random value selected");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnRndAssignFunc.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int selectedFunctions[] = new int[4];
				selectedFunctions[0] = (int) (Math.random() + 0.5);
				selectedFunctions[1] = (int) (Math.random() + 0.5);
				selectedFunctions[2] = (int) (Math.random() + 0.5);
				selectedFunctions[3] = (int) (Math.random() + 0.5);

				if (selectedFunctions[0] == 1)
					cbMachineFuncDDNS.setValue(true);
				else
					cbMachineFuncDDNS.setValue(false);

				if (selectedFunctions[1] == 1)
					cbMachineFuncDNS.setValue(true);
				else
					cbMachineFuncDNS.setValue(false);

				if (selectedFunctions[2] == 1)
					cbMachineFuncGateway.setValue(true);
				else
					cbMachineFuncGateway.setValue(false);

				if (selectedFunctions[3] == 1)
					cbMachineFuncRouter.setValue(true);
				else
					cbMachineFuncRouter.setValue(false);
			}
		});
		// nameField.setText("GWT User");

		// We can add style names to widgets
		// sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		// RootPanel.get("nameFieldContainer").add(nameField);
		// RootPanel.get("sendButtonContainer").add(sendButton);

		// Focus the cursor on the name field when the app loads
		// nameField.setFocus(true);
		// nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				// sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					// sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			// private void sendNameToServer() {
			// // sendButton.setEnabled(false);
			// String textToServer = "";// nameField.getText();
			// textToServerLabel.setText(textToServer);
			// serverResponseLabel.setText("");
			// droolsExpertServiceAsync.getTestsAssignedData(textToServer,
			// new AsyncCallback<String>() {
			// public void onFailure(Throwable caught) {
			// // Show the RPC error message to the user
			// dialogBox
			// .setText("Remote Procedure Call - Failure");
			// serverResponseLabel
			// .addStyleName("serverResponseLabelError");
			// serverResponseLabel.setHTML(SERVER_ERROR);
			// dialogBox.center();
			// closeButton.setFocus(true);
			// }
			//
			// public void onSuccess(String result) {
			// dialogBox.setText("Remote Procedure Call");
			// serverResponseLabel
			// .removeStyleName("serverResponseLabelError");
			// serverResponseLabel.setHTML(result);
			// dialogBox.center();
			// closeButton.setFocus(true);
			// }
			// });
			// }
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		// sendButton.addClickHandler(handler);
		// nameField.addKeyUpHandler(handler);
	}
}
