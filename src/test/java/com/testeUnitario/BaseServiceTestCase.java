package com.testeUnitario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations = {"/applicationContext.xml", "/dataAccessContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = false)
public abstract class BaseServiceTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	protected final Log log = LogFactory.getLog(getClass());
	public static ApplicationContext applicationContext;
	
}