package com.mkyong.controller;

import com.mkyong.model.AjaxResponseBody;
import com.mkyong.model.SOAPClientSAAJ;
import com.mkyong.model.SearchCriteria;
import com.mkyong.model.User;
import com.mkyong.model.Shop;
import com.mkyong.services.UserService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@RestController
public class SearchController {

    UserService userService;
    boolean running = false;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/search")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchCriteria search, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }

        List<User> users = userService.findByUserNameOrEmail(search.getUsername());
        if (users.isEmpty()) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }
        result.setResult(users);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/api/listNames")
    public ResponseEntity<?> getNames() {

        AjaxResponseBody result = new AjaxResponseBody();

        List<User> users = new ArrayList<User>();

        User user1 = new User("joel", "password1", "joel@yahoo.com");
        User user2 = new User("sass", "password2", "sass@yahoo.com");
        User user3 = new User("steph", "password3", "steph@yahoo.com");

        users.add(user1);
        users.add(user2);
        users.add(user3);

    	String textToShow = "";
    	//start helper task. Send back to UI that it's still running if it is.
    	if(!running) {
    		textToShow = "Starting";
	    	Runnable runnable = () -> { 
	    		running = true;
	    		for (int i = 0; i < 5; i++) {
	    			try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		running = false;
			};
			new Thread(runnable).start();
    	} else {
    		textToShow = "Running";
    	}

        result.setResult(users);
		result.setMsg("success running:" + textToShow);
        
        return ResponseEntity.ok(result);

    }
    
    @GetMapping("/api/getShop/{id}")
    public ResponseEntity<?> getShop(@PathVariable int id) {
    	AjaxResponseBody result = new AjaxResponseBody();
    	
    	//create txn to give query context
    	Transaction txn = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    	
    	Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from Shop where id =  " + id);
    	
    	List<?> results = query.list();

    	txn.commit();
    	
    	String textToShow = "";
    	//start helper task
    	if(!running) {
    		textToShow = "Starting";
	    	Runnable runnable = () -> { 
	    		running = true;
	    		for (int i = 0; i < 5; i++) {
	    			try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		running = false;
			};
			new Thread(runnable).start();
    	} else {
    		textToShow = "Running";
    	}

    	//insert celsius to fahrenheit conversion
    	Shop firstShop = ((Shop)results.get(0));
    	firstShop.setName(firstShop.getName() + SOAPClientSAAJ.callSoapWebService(firstShop.getUserId()));
    	
		result.setMsg("success running:" + textToShow);
		result.setResult(results);
		return ResponseEntity.ok(result);
    }

    @GetMapping("/excelSample")
    public void excelSamples(HttpServletResponse response) {
	    File file = new File("/Users/joellucuik/TanShoe/Projections/projections_ShowingHST_(FullYear)_712.xlsx");
	    InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // xls file
	    response.addHeader("Content-disposition", "attachment;filename=sample.xlsx");
	    response.setContentType("application/octet-stream");

	    // Copy the stream to the response's output stream.
	    try {
			IOUtils.copy(inputStream, response.getOutputStream());
		    response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
			
    }

}
