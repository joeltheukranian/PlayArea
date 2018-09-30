package demotest;

import java.util.concurrent.Semaphore;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import demo.HibernateUtil;
import demo.Shop;
import junit.framework.TestCase;

public class DemoTest extends TestCase {
//	static Semaphore sem = new Semaphore(3);
	private int threadsDone;
	private boolean optimisticLockFailure;
	private int threadsStarted;

	@Test
	public void testSaveShopsFails() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean firstPassed = false, secondPassed = false;
		Shop shop = new Shop("Stub");
		
		try {
			session.beginTransaction();
			shop = new Shop("My Shop");
			shop.setName("superman");
			shop.setUserId(1);
			session.save(shop);
			session.getTransaction().commit();

			firstPassed = true;
			
			session.clear();
			session.beginTransaction();
			shop = new Shop("My Shop");
			shop.setName("batman");
			shop.setUserId(1);
			session.save(shop);
			session.getTransaction().commit();
			
			secondPassed = true;
		
		} catch (HibernateException hibernateException) {
			// should not be able to store the same id twice
			System.out.println("**" + hibernateException.getMessage());
			
			//rollback
			session.getTransaction().rollback();
		} finally {
			//delete ID 1
			session.beginTransaction();
			shop.setUserId(1);
			session.delete(shop);
			session.getTransaction().commit();
		}
		
		assertEquals("Second commit should not have passed", false, secondPassed);
	}

	@Test
	public void testOptimisticLock() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Shop shop = new Shop("Stub");
		this.threadsDone = 0;
		this.threadsStarted = 0;
		this.optimisticLockFailure = false;

		//Create
		session.beginTransaction();
		shop = new Shop("My Shop");
		shop.setName("superman");
		shop.setUserId(1);
		session.save(shop);
		session.getTransaction().commit();

		//lock test: two threads read and write
		try {
			threadLockLaunch();
			threadLockLaunch();
			
			//wait for  threads to start
			System.out.println("Main Waiting for threads to start: ");
			while(this.threadsStarted < 2) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Threads started");
			
			//wait for  threads to finish. 10 seconds max
			int totalWaitAllowed = 10000;
			System.out.println("Main Waiting for threads to finish");
			while((this.threadsDone < 2) && (totalWaitAllowed > 0)) {
				System.out.println("Still Main Waiting for threads to finish");
				try {
					Thread.sleep(500);
					totalWaitAllowed -= 500;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		} 
		finally {
			//delete ID 1
			session.clear();
			session.beginTransaction();
			shop = new Shop("My Shop");
//			shop.setUserId(1);
			session.load(shop, 1);
			session.delete(shop);
			session.getTransaction().commit();
			session.close();
		}
		
		assertEquals("There should have been an opt lock failure", true, this.optimisticLockFailure);
	}
	
	private void threadLockLaunch() {
		Thread thread = new Thread(
			new Runnable(){      
			   @Override
			   public void run(){
			     System.out.println("Thread Launched");
			     readAndWriteWithLock();
			   }
			 }
		);
		thread.start();
	}
	
	private void readAndWriteWithLock() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Shop shop = new Shop("Stub");
		shop.setUserId(1);
		
		System.out.println("1");
		try {
			System.out.println("1a");
			//read, change, write
			session.beginTransaction();
			System.out.println("1b");
			session.clear();
			session.load(shop, 1);
			System.out.println("1load");

			//wait for other threads
			try {
				System.out.println("Increment threads started");
//				sem.acquire();

				synchronized(this) {
					++this.threadsStarted;
				}
				
				while(this.threadsStarted < 2) {
					System.out.println("Waiting for other threads: " + this.threadsStarted);
					Thread.sleep(500);
				}
				System.out.println("Thread about to update");
			} catch (InterruptedException e) {
				System.out.println("Thread interrrupted");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//change
			shop.setName("New name");
			session.save(shop);
			session.getTransaction().commit();
			
		} catch (HibernateException hibernateException) {
			System.out.println("Thread Hibernate exception");
			// one will fail on lock
			this.optimisticLockFailure = true;
			System.out.println("**" + hibernateException.getMessage());
			
			//rollback
			session.getTransaction().rollback();
			session.close();
//			session.getTransaction().rollback();
		} finally {
			System.out.println("thread finally");
//			sem.release();

			synchronized(this) {
				++this.threadsDone;
			}
		}
	}

	@Test
	public void testLoadShop() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Shop shop = new Shop("Stub");
		
		try {
			session.beginTransaction();
			shop = new Shop("My Shop");
			shop.setName("superman");
			shop.setUserId(1);
			session.save(shop);
			session.getTransaction().commit();
	
			session.clear();
			session.beginTransaction();
			shop = new Shop("My Shop");
			shop.setUserId(1);
			shop.setName(null);
			session.load(shop,1 );
			System.out.println("Shop name: " + shop.getName());
			session.getTransaction().commit();
		
		} catch (HibernateException hibernateException) {
			// should not be able to store the same id twice
			System.out.println("**" + hibernateException.getMessage());
			
		} finally {
			//delete ID 1
			session.clear();
			session.beginTransaction();
			shop.setUserId(1);
			session.delete(shop);
			session.getTransaction().commit();
		}
		
		assertEquals("Load should succeed", "superman", shop.getName());
	}
	
}
