package com.demo.drools.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DroolsDemo implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final DroolsExpertServiceAsync droolsExpertServiceAsync = GWT.create(DroolsExpertService.class);

    final Label lblMessage = new Label("<< Result appears here >>");
    final VerticalPanel verticalPanel2 = new VerticalPanel();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        FlexTable flexTable = new FlexTable();
        FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();

        final Label lblUserType = new Label("Select User Type");

        final RadioButton rbUserType1 = new RadioButton("userType", "Type 1");
        rbUserType1.setTitle("Admin User");

        final RadioButton rbUserType2 = new RadioButton("userType", "Type 2");
        rbUserType2.setTitle("Power User");

        final RadioButton rbUserType3 = new RadioButton("userType", "Type 3");
        rbUserType3.setTitle("Basic User");

        final Button btnRndSelectUserType = new Button("Randomly Select");

        final Label lblSeeWhatYouCanInput = new Label("See how many questions you need to answer");

        final VerticalPanel verticalPanel1 = new VerticalPanel();

        verticalPanel1.add(rbUserType1);
        verticalPanel1.add(rbUserType2);
        verticalPanel1.add(rbUserType3);

        flexCellFormatter.setColSpan(0, 0, 3);
        flexTable.setWidget(0, 0, lblSeeWhatYouCanInput);

        flexTable.setWidget(1, 0, lblUserType);
        flexTable.setWidget(1, 1, verticalPanel1);
        flexTable.setWidget(1, 2, btnRndSelectUserType);

        flexCellFormatter.setColSpan(2, 0, 3);
        flexTable.setWidget(2, 0, lblMessage);

        flexCellFormatter.setColSpan(3, 0, 3);
        flexTable.setWidget(3, 0, verticalPanel2);

        btnRndSelectUserType.setSize("100%", "100%");

        // flexTable.setBorderWidth(1);
        flexTable.setStyleName("pattern-style-b");
        flexTable.setSize("600px", "200px");

        RootPanel.get("gwtCode").add(flexTable);

        rbUserType1.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (rbUserType1.getValue()) {
                    triggerServerAction(1);
                }
            }
        });

        rbUserType2.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (rbUserType2.getValue()) {
                    triggerServerAction(2);
                }
            }
        });

        rbUserType3.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (rbUserType3.getValue()) {
                    triggerServerAction(3);
                }
            }
        });

        btnRndSelectUserType.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // the below is used to select a random integer between 1 and 3
                // - both inclusive
                // logic: Min + (int)(Math.random() * ((Max - Min) + 1))
                int selectedType = 1 + (int) (Math.random() * ((3 - 1) + 1));

                switch (selectedType) {
                case 1:
                    rbUserType1.setValue(true);
                    triggerServerAction(1);
                    break;
                case 2:
                    rbUserType2.setValue(true);
                    triggerServerAction(2);
                    break;
                case 3:
                    rbUserType3.setValue(true);
                    triggerServerAction(3);
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

    }

    private HorizontalPanel getNewWidgetSetForOneQuestion() {
        HorizontalPanel thisHPanel = new HorizontalPanel();

        thisHPanel.add(new Label("This is a question"));

        Widget thisInputWidget = null;
        // Generate a binary integer. If 0 then create TextBox, if 1 then create
        // ListBox
        int rndTxtBoxOrListBox = (int) (Math.random() * ((1 - 0) + 1));

        try {
            switch (rndTxtBoxOrListBox) {
            case 0:
                thisInputWidget = new TextBox();
                break;
            case 1:
                thisInputWidget = new ListBox();

                // Generate a random number of ListBox options between 2 and 10
                int rndNumOfOptions = 2 + (int) (Math.random() * ((10 - 2) + 1));
                rndNumOfOptions = rndNumOfOptions > 10 ? 10 : rndNumOfOptions;
                for (int i = 0; i < rndNumOfOptions; i++) {
                    ((ListBox) thisInputWidget).insertItem(new String("option " + i), i);
                }
                // this will help select some random values as default options,
                // instead of the item at index 0 being selected by default
                ((ListBox) thisInputWidget).setSelectedIndex(rndNumOfOptions / 2);

                break;
            default:
                throw new Exception("Unexpected random value generated");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        thisHPanel.add(thisInputWidget);

        return thisHPanel;
    }

    private void triggerServerAction(int userCode) {
        lblMessage.setText("Processing...");
        verticalPanel2.clear();
        verticalPanel2.add(new Image("/images/ajax-loader.gif"));

        HashMap inputParams = new HashMap();
        HashMap outputParams;

        inputParams.put("userCode", userCode);

        droolsExpertServiceAsync.getTestsAssignedData(inputParams, new AsyncCallback() {

            @Override
            public void onSuccess(Object result) {
                HashMap outputParams = (HashMap) result;
                String userType = (String) outputParams.get("userType");
                int countOfQuestions = (Integer) outputParams.get("countOfQuestions");

                lblMessage.setText("You are identified as a " + userType + " user." + "\n" + "You need to answer " + countOfQuestions
                        + " questions below:");

                verticalPanel2.clear();
                for (int i = 0; i < countOfQuestions; i++) {
                    verticalPanel2.add(getNewWidgetSetForOneQuestion());
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                lblMessage.setText("Some failure on backend: " + caught.fillInStackTrace());
            }

        });
    }

}
