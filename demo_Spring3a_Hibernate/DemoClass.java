package demo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("test")
public class DemoClass {
	
	@GET
	@Path("get")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPath() {
		return "Hi";
	}
}
