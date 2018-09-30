package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping("/test")
public class DemoClass {
	
	@RequestMapping(value="get", method = RequestMethod.GET)
	public @ResponseBody String getPath() {
		return "Hi";
	}
}
