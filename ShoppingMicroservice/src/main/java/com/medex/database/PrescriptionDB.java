package com.medex.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medex.dependentresources.Prescription;


//This class is specifically for the prescriptions database operations
public class PrescriptionDB {
	public void insertPrescription(Prescription prescription)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.save(prescription); //Save transaction allows us to store the prescription object into the database (This is like insert with the fields, etc, etc)
								   //But hibernate knows what to do using the annotation on the prescription class
			
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
	
	//This is the update, which prescription we want to delete
	public void updatePrescription(Prescription prescription)
	{
		Transaction transaction = null; //You have to make a transaction object
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			session.saveOrUpdate(prescription); //Save transaction allows us to store the prescription object into the database (This is like insert with the fields, etc, etc)
										   //But hibernate knows what to do using the annotation on the prescription class
			
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
	public void deletePrescription(int id)
	{
		Transaction transaction = null; //You have to make a transaction object
		Prescription prescription = null;
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession()) //And now we make a session using the HibernateUtil object
		{
			// start a transaction using the session
			transaction = session.beginTransaction();
			
			prescription = session.get(Prescription.class, id); //We have to get the specific prescription using the ID from the database, so we can delete it
			
			session.delete(prescription); //We delete that prescription
			
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

	//Retrieve all prescriptions from the database and store them in a list
	public List<Prescription> getPrescriptions(int patientid)
	{
		Transaction transaction = null;
		List<Prescription> prescriptions = null;
		
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			prescriptions = session.createQuery("from Prescription C where C.patientID = :patientid", Prescription.class).setParameter("patientid", patientid).list(); //This is a hibernate query (Get all prescriptions from the prescriptions database)
																		 //Each returned row is a prescription object inserted into the list of prescriptions --> prescriptions
			transaction.commit();
		}
		return prescriptions;
	}
	
	public Prescription getPrescription(int patientid, int prescriptionid)
	{
		Transaction transaction = null;
		Prescription prescription = null;
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession())
		{
			//start a transaction
			transaction = session.beginTransaction();
			
			// get one object
			System.out.println(String.valueOf(patientid) + " " + String.valueOf(prescriptionid));
			String hql = "FROM Prescription C WHERE C.id = :prescriptionid AND C.patientID = :patientid"; //From the prescription table
			Query query = session.createQuery(hql);
			query.setParameter("patientid", patientid); //The parameter ":id" is set to the id we passed.
			query.setParameter("prescriptionid", prescriptionid); //The parameter ":id" is set to the id we passed.
			List results = query.getResultList(); //The results are given to us in a list.
												  //Since the id is unique, we will get a list of one item
			
			//If the result is not null, we get a single prescription object
			if (results != null && !results.isEmpty())
			{
				prescription = (Prescription) results.get(0); //So, we retrieve said prescription from the first index in the list
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
		return prescription; //Return the prescription object retrieved
	}
	
	
	
	
	public Prescription getPrescriptionWithMedicine(int doctorid, int patientid, int medicineid)
	{
		Transaction transaction = null;
		Prescription prescription = null;
		try (Session session = HibernateUtil.getDoctorSessionFactory().openSession())
		{
			//start a transaction
			transaction = session.beginTransaction();
			
			// get one object
			
			String hql = "from Prescription P WHERE P.doctorID = :doctorid AND P.patientID = :patientid AND P.medicineID = :medicineid"; //From the prescription table
			Query query = session.createQuery(hql);
			query.setParameter("doctorid", doctorid); //The parameter ":id" is set to the id we passed.
			query.setParameter("patientid", patientid); //The parameter ":id" is set to the id we passed.
			query.setParameter("medicineid", medicineid); //The parameter ":id" is set to the id we passed.
			List results = query.getResultList(); //The results are given to us in a list.
												  //Since the id is unique, we will get a list of one item
			
			//If the result is not null, we get a single prescription object
			if (results != null && !results.isEmpty())
			{
				prescription = (Prescription) results.get(0); //So, we retrieve said prescription from the first index in the list
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
		return prescription; //Return the prescription object retrieved
	}

	
}
