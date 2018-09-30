package demo;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

	

@Controller
@RequestMapping("/kfc/brands")
public class JSONController extends CORSController {
	static Logger logger = Logger.getLogger(JSONController.class);
	static String[] names = {"one", "two"}; 
	
	@RequestMapping(value="{name}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
		logger.info("Responding to GET");
		Shop shop = new Shop(name);
		shop.setStaffName(new String[]{"Bob", "Jim"});

		return shop;

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
		logger.info("Responding to Put");
		
		names[Integer.parseInt(index)] = newName.value; 

		return new Shop("Worked fine");
	}
	
}