package com.demo.drools.server;

import org.drools.WorkingMemory;

public class TestsRulesEngine {

	private RulesEngine rulesEngine;


	public TestsRulesEngine() throws RulesEngineException {
		super();
		rulesEngine = new RulesEngine("/rules/testRules1.drl");
	}

	public void assignTests(final User user) {
		rulesEngine.executeRules(new WorkingEnvironmentCallback() {
			public void initEnvironment(WorkingMemory workingMemory) {
				// Set globals first before asserting/inserting any knowledge!
				workingMemory.insert(user);
			};
		});
	}

}
