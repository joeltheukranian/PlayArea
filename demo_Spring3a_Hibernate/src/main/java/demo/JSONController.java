package demo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

	

@Controller
@RequestMapping("/kfc/brands")
public class JSONController extends CORSController {
	static Logger logger = Logger.getLogger(JSONController.class);
	static String[] names = {"one", "two"}; 
	static int times = 0;
	
	static ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
	public static Runnable runnable = () -> {
		while(true) {
			logger.info("****About to wait");
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			++times;
			logger.info("****Locking");
			if(lock1.hasQueuedThreads()) {
				logger.info("****UI action...set them free:" + lock1.getWriteHoldCount());
				lock1.writeLock().unlock();
			} else {
				if(lock1.getWriteHoldCount() == 0) {
					logger.info("***Acquire lock");
					lock1.writeLock().lock();
					logger.info("****Locked");
				}
			}
			
		}
	};
	static ThreadHelper helper = ThreadHelper.start(new Thread(runnable));
	
	@RequestMapping(value="{name}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
		logger.info("Responding to GET" + Boolean.toString(validateXML()));
		Shop shop = new Shop(name);
		shop.setStaffName(new String[]{"Bob", "Jim"});
		shop.setName(Boolean.toString(validateXML()));
		

		//validateXML();
		return shop;

	}

	public boolean validateXML() {
		//		import javax.xml.XMLConstants;
		//		import javax.xml.transform.Source;
		//		import javax.xml.transform.stream.StreamSource;
		//		import javax.xml.validation.*;
		//		import java.net.URL;
		//		import org.xml.sax.SAXException;
				//import java.io.File; // if you use File
		//		import java.io.IOException;
		//		...
		URL schemaFile = null;
		try {
			schemaFile = new URL("http://localhost:8444/maven-4.0.0.xsd");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// webapp example xsd:
		// URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
		// local file example:
		// File schemaFile = new File("/location/to/localfile.xsd"); // etc.
		Source xmlFile = new StreamSource(new File("http://localhost:8444/pom.xml"));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			validator.validate(xmlFile);
			// System.out.println(xmlFile.getSystemId() + " is valid");
		} catch (SAXException e) {
			System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="/brand/{index}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopAtIndex(@PathVariable String index) {
		logger.info("Responding to GET");
		Shop shop = new Shop(names[Integer.parseInt(index)]);
		shop.setStaffName(new String[]{"Bob", "Jim"});

		return shop;

	}

	@RequestMapping(value="/brand/{index}", method = RequestMethod.PUT)
	public @ResponseBody Shop putShopAtIndex(@PathVariable String index, @RequestBody NameParameter newName) {
		logger.info("Responding to Put with: " + newName.value);
		
		try {
			names[Integer.parseInt(index)] = newName.value; 
	
			if(newName.value.equalsIgnoreCase("1")) {
				logger.info("UI waiting for lock...");
				lock1.writeLock().lock();
	//			lock1.lock();
				try {
					return new Shop(Integer.toString(times) + " from Put");
				} finally {
					lock1.writeLock().unlock();
				}
			}
			
			return new Shop(newName.value + "Worked fine");
		} catch (Throwable t) {
			logger.error("Huh? ", t);
			return new Shop("Messed up");
		}
	}
	
}