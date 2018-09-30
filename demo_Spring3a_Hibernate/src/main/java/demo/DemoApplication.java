package demo;

import java.util.Date;
import org.hibernate.Session;

public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + Oracle");
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Shop shop = new Shop("My Shop");

//		user.setUserId(100);
		shop.setName("superman");
		shop.setUserId(1);
//		shop.setStaffName("supermans boss");
//		shop.setCreatedDate(new Date());
		
		Shop shop2 = new Shop("My 2nd Shop");
		shop2.setName("batman");
		shop2.setUserId(2);
		
		session.save(shop);
		session.save(shop2);
		session.getTransaction().commit();
	}
}
