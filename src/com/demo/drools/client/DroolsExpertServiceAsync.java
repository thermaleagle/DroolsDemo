package com.demo.drools.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>DroolsExpertService</code>.
 */
public interface DroolsExpertServiceAsync {
	void getTestsAssignedData(HashMap inputParams, AsyncCallback asyncCallback);
}
