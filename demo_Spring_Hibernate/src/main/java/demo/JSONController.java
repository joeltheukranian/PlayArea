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
import org.hibernate.Query;
import org.hibernate.Transaction;
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
	
	
	@RequestMapping(value="{name}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
		//logger.info("Responding to GET" + Boolean.toString(validateXML()));
		Shop shop = new Shop(name);
		shop.setStaffName(new String[]{"Bob", "Jim"});
		//shop.setName(Boolean.toString(validateXML()));
		

		//validateXML();
		return shop;

	}

	
	@RequestMapping(value="/brand/{index}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopAtIndex(@PathVariable String index) {
		logger.info("Responding to GET");
//		Transaction txn = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

		//Hibernate
//		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from Shop where id = 1 ");
//		Shop shop = (Shop) query.list().get(0);
		
		//Simple
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
//				logger.info("UI waiting for lock...");
				return new Shop(Integer.toString(times) + " from Put");
			}
			
			return new Shop(newName.value + "Worked fine");
		} catch (Throwable t) {
			logger.error("Huh? ", t);
			return new Shop("Messed up");
		}
	}
	
}