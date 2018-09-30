package demo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.apache.log4j.jmx.LoggerDynamicMBean;

public class ApplicationConfig extends Application {

	Logger logger = Logger.getLogger(ApplicationConfig.class);
			
	public ApplicationConfig() {
		super();
		logger.info("HERE2!!!");
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		logger.info("HERE!!!");
		HashSet classSet = new HashSet<?>();
		classSet.add(DemoClass.class);
		return classSet;
	}

}
