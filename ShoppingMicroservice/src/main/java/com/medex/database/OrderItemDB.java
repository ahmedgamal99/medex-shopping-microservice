package com.medex.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medex.model.OrderItem;

//This class is specifically for the orderItems database operations
public class OrderItemDB {
	public void insertOrderItem(OrderItem orderItem)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.save(orderItem); //Save transaction allows us to store the orderItem object into the database (This is like insert with the fields, etc, etc)
								   //But hibernate knows what to do using the annotation on the orderItem class
			
			// commit transaction		
			transaction.commit(); //Finalize transaction
		}
		catch (Exception e) //If anything goes wrong
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	//This is the update, which orderItem we want to delete
	public void updateOrderItem(OrderItem orderItem)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.saveOrUpdate(orderItem); //Save transaction allows us to store the orderItem object into the database (This is like insert with the fields, etc, etc)
										   //But hibernate knows what to do using the annotation on the orderItem class
			
			// commit transaction
			
			transaction.commit(); //Finalize transaction
		}
		catch (Exception e) //If anything goes wrong
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	
	//Id of what you want to delete
	public void deleteOrderItem(int id)
	{
		Transaction transaction = null; //You have to make a transaction object
		OrderItem orderItem = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			orderItem = session.get(OrderItem.class, id); //We have to get the specific orderItem using the ID from the database, so we can delete it
			
			session.delete(orderItem); //We delete that orderItem
			
			// commit transaction
			transaction.commit(); //Finalize transaction
		}
		catch (Exception e) //If anything goes wrong
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Retrieve all orderItems from the database and store them in a list
	public List<OrderItem> getOrderItems(int patientid, int orderid)
	{
		Transaction transaction = null;
		List<OrderItem> orderItems = null;
		
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			orderItems = session.createQuery("from OrderItem O where O.orderID = :orderid AND O.patientID = :patientid", OrderItem.class).setParameter("orderid", orderid).setParameter("patientid", patientid).list(); //This is a hibernate query (Get all orderItems from the orderItems database)
																		 //Each returned row is a orderItem object inserted into the list of orderItems --> orderItems
			transaction.commit();
		}
		return orderItems;
	}
	
	public OrderItem getOrderItem(int patientid, int orderid, int orderitemid)
	{
		Transaction transaction = null;
		OrderItem orderItem = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			//start a transaction
			transaction = session.beginTransaction();
			
			// get one object
			System.out.println("1");
			String hql = "FROM OrderItem H WHERE H.id = :orderitemid and H.orderID = :orderid and H.patientID = :patientid"; //From the orderItem table
			System.out.println("12");
			Query query = session.createQuery(hql);
			System.out.println("13");
			query.setParameter("patientid", patientid); //The parameter ":id" is set to the id we passed.
			System.out.println("14");
			query.setParameter("orderid", orderid); //The parameter ":id" is set to the id we passed.
			System.out.println("15");
			query.setParameter("orderitemid", orderitemid); //The parameter ":id" is set to the id we passed.
			System.out.println("16");
			List results = query.getResultList(); //The results are given to us in a list.
												  //Since the id is unique, we will get a list of one item
			System.out.println("1");
			//If the result is not null, we get a single orderItem object
			if (results != null && !results.isEmpty())
			{
				orderItem = (OrderItem) results.get(0); //So, we retrieve said orderItem from the first index in the list
			}
			//commit transaction
			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return orderItem; //Return the orderItem object retrieved
	}
	
}
