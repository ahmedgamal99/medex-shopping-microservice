package com.medex.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medex.model.CartItem;

//This class is specifically for the cartItems database operations.
public class CartItemDB {
	public void insertCartItem(CartItem cartItem)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			//System.out.println(cartItem.getName() + " " + cartItem.getMedicineID() + " " + cartItem.getName() + cartItem.getPatientID() + " " + cartItem.getPharmacyName() + " " + cartItem.getCount() + " " + cartItem.getPharmacyID());
			
			session.save(cartItem); //Save transaction allows us to store the cartItem object into the database (This is like insert with the fields, etc, etc)
								   //But hibernate knows what to do using the annotation on the cartItem class
			
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
	
	//This is the update, which cartItem we want to delete
	public void updateCartItem(CartItem cartItem)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.saveOrUpdate(cartItem); //Save transaction allows us to store the cartItem object into the database (This is like insert with the fields, etc, etc)
										   //But hibernate knows what to do using the annotation on the cartItem class
			
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
	

	public void deleteCartItem(int id)
	{
		Transaction transaction = null; //You have to make a transaction object
		CartItem cartItem = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			cartItem = session.get(CartItem.class, id); //We have to get the specific cartItem using the ID from the database, so we can delete it
			
			session.delete(cartItem); //We delete that cartItem
			
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
	public void deleteCartItems(int patientid)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			String stringQuery = "DELETE FROM CartItem WHERE patientID = :patientid";
			Query query = session.createQuery(stringQuery).setParameter("patientid", patientid);
			query.executeUpdate();
			//Id of what you want to delete
			
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

	//Retrieve all cartItems from the database and store them in a list
	public List<CartItem> getCartItems(int patientid)
	{
		Transaction transaction = null;
		List<CartItem> cartItems = null;
		
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			cartItems = session.createQuery("from CartItem C where C.patientID = :patientid", CartItem.class).setParameter("patientid", patientid).list(); //This is a hibernate query (Get all cartItems from the cartItems database)
																		 //Each returned row is a cartItem object inserted into the list of cartItems --> cartItems
			transaction.commit();
		}
		return cartItems;
	}
	
	public CartItem getCartItem(int patientid, int cartitemid)
	{
		Transaction transaction = null;
		CartItem cartItem = null;
		try (Session session = HibernateUtil.getShoppingSessionFactory().openSession())
		{
			//start a transaction
			transaction = session.beginTransaction();
			
			// get one object
			String hql = " FROM CartItem C WHERE C.id = :cartitemid AND C.patientID = :patientid"; //From the cartItem table
			Query query = session.createQuery(hql);
			query.setParameter("patientid", patientid); //The parameter ":id" is set to the id we passed.
			query.setParameter("cartitemid", cartitemid); //The parameter ":id" is set to the id we passed.
			List results = query.getResultList(); //The results are given to us in a list.
												  //Since the id is unique, we will get a list of one item
			
			//If the result is not null, we get a single cartItem object
			if (results != null && !results.isEmpty())
			{
				cartItem = (CartItem) results.get(0); //So, we retrieve said cartItem from the first index in the list
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
		return cartItem; //Return the cartItem object retrieved
	}
	
}
