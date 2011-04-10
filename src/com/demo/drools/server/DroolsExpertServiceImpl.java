package com.demo.drools.server;

import java.util.HashMap;
import java.util.List;

import com.demo.drools.client.DroolsExpertService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DroolsExpertServiceImpl extends RemoteServiceServlet implements
		DroolsExpertService {
	
	private TestDAO testDAO = new TestDAOImpl();
	private TestsRulesEngine testsRulesEngine = new TestsRulesEngine(testDAO);

	@Override
	public HashMap getTestsAssignedData(HashMap inputParams) {
		HashMap outputParams = new HashMap();
		String thisMachineSerial = (String) inputParams.get("thisMachineSerial");
		String thisMachineType = (String) inputParams.get("thisMachineType");
		List thisMachineFuncs = (List) inputParams.get("thisMachineFuncs");

		Machine thisMachine = new Machine();
		thisMachine.setSerialNumber(thisMachineSerial);
		thisMachine.setType(thisMachineType);
		thisMachine.setFunctions(thisMachineFuncs);
		
		testsRulesEngine.assignTests(thisMachine);
		System.out.println(thisMachine.getCreationTs());
		outputParams.put("testsAssigned", thisMachine.getTests());
		outputParams.put("dueDate", thisMachine.getTestsDueTime());
		return outputParams;
	}
}
