package com.medex.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medex.model.Ordr;

//This class is specifically for the orders database operations
public class OrderDB {
	public void insertOrder(Ordr order)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.save(order); //Save transaction allows us to store the order object into the database (This is like insert with the fields, etc, etc)
								   //But hibernate knows what to do using the annotation on the order class
			
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
	
	//This is the update, which order we want to delete
	public void updateOrder(Ordr order)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.saveOrUpdate(order); //Save transaction allows us to store the order object into the database (This is like insert with the fields, etc, etc)
										   //But hibernate knows what to do using the annotation on the order class
			
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
	public void deleteOrder(int id)
	{
		Transaction transaction = null; //You have to make a transaction object
		Ordr order = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			order = session.get(Ordr.class, id); //We have to get the specific order using the ID from the database, so we can delete it
			
			session.delete(order); //We delete that order
			
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

	//Retrieve all orders from the database and store them in a list
	public List<Ordr> getOrders(int patientid)
	{
		Transaction transaction = null;
		List<Ordr> orders = null;
		
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			orders = session.createQuery("FROM Ordr H WHERE H.patientID = :patientid", Ordr.class).setParameter("patientid", patientid).list(); //This is a hibernate query (Get all orders from the orders database)
																		 //Each returned row is a order object inserted into the list of orders --> orders
			transaction.commit();
		}
		return orders;
	}
	
	public Ordr getOrder(int patientid, int orderid)
	{
		Transaction transaction = null;
		Ordr order = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			//start a transaction
			transaction = session.beginTransaction();
			// get one object
			String hql = "FROM Ordr H WHERE H.id = :orderid and H.patientID = :patientid"; //From the order table
			
			Query query = session.createQuery(hql);

			query.setParameter("orderid", orderid); //The parameter ":id" is set to the id we passed.
			query.setParameter("patientid", patientid); //The parameter ":id" is set to the id we passed.
			List results = query.getResultList(); //The results are given to us in a list.
												  //Since the id is unique, we will get a list of one item

			
			//If the result is not null, we get a single order object
			if (results != null && !results.isEmpty())
			{
				order = (Ordr) results.get(0); //So, we retrieve said order from the first index in the list
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
		return order; //Return the order object retrieved
	}
	
}
