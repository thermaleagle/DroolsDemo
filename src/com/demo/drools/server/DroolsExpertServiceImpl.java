package com.demo.drools.server;

import java.util.HashMap;

import com.demo.drools.client.DroolsExpertService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DroolsExpertServiceImpl extends RemoteServiceServlet implements DroolsExpertService {

    private TestsRulesEngine testsRulesEngine = new TestsRulesEngine();

    @Override
    public HashMap getTestsAssignedData(HashMap inputParams) {
        HashMap outputParams = new HashMap();
        int userCode = (Integer) inputParams.get("userCode");

        User thisUser = new User();
        
        thisUser.setCode(userCode);

        testsRulesEngine.assignTests(thisUser);

        outputParams.put("userType", thisUser.getType());
        outputParams.put("countOfQuestions", thisUser.getCountOfQuestions());

        return outputParams;
    }
}
