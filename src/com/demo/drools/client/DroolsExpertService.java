package com.demo.drools.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("droolsExpertAdvice")
public interface DroolsExpertService extends RemoteService {
	HashMap getTestsAssignedData(HashMap inputParams);
}
