package test;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) {
		Consumer con = (s) -> { 
			System.out.println("hello world" + s);
		};
		Consumer con2 = (s) -> System.out.println("hello world2" + s);
		
		con.accept("Hey There");
		
		Runnable runnable = () -> { 
//			try {
//				Thread.currentThread().wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("start it");
			con.accept("Why?");
//			Thread.currentThread().notify();
		};
			
		
		
		//new ArrayList() {"hi", "there"};
		List myList = Stream.of("xyz", "abc").collect(Collectors.toList());
		myList.forEach(con);
		
		//Collections.synchronizedMap(m);
		List mySynchroList = Collections.synchronizedList(myList);
		
		Runnable runnable2 = () -> {
//			try {
//				Thread.currentThread().wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			mySynchroList.forEach(con2);
//			Thread.currentThread().notify();
		};
			
		new Thread(runnable).start();
		new Thread(runnable2).start();
	}
}
