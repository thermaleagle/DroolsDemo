package com.demo.drools.server;

import java.util.Collection;

public interface TestDAO {

	Test findByKey(Integer id);

	Collection findAll();

}
