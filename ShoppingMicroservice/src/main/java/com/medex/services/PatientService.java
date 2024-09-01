package com.medex.services;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.ws.rs.PathParam;

import com.medex.communicationmodules.OrderInfo;
import com.medex.communicationmodules.PatientInfo;
import com.medex.communicationmodules.Status;
import com.medex.database.PatientDB;
import com.medex.database.PharmaceuticalDB;
import com.medex.database.PharmaceuticalStockDB;
import com.medex.database.PrescriptionDB;
import com.medex.dependentresources.PharmaceuticalStock;
import com.medex.dependentresources.Prescription;
import com.medex.model.CartItem;
import com.medex.model.OrderItem;
import com.medex.model.Ordr;
import com.medex.model.Patient;

//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class PatientService {
	PatientDB patientdb = new PatientDB(); //(Instead of the pseudo-database)
	CartItemService cartItemService = new CartItemService();
	OrderService orderService = new OrderService();
	PharmaceuticalStockDB pharmaceuticalstockdb = new PharmaceuticalStockDB();
	PrescriptionService prescriptionservice = new PrescriptionService();
	PharmaceuticalDB pharmaceuticalDB = new PharmaceuticalDB();
	OrderItemService orderitemservice = new OrderItemService();
	PharmacyService pharmacyservice = new PharmacyService();
	
	
	public PatientService() {} 
	
	//All what is below is just calling the functions belonging to the pharmacies' database/table.
	
	public List<PatientInfo>getAllPatients()
	{
		List<Patient> patientList = patientdb.getPatients(); //Get all hosts.
		List<PatientInfo> patientinfoList = new ArrayList<PatientInfo>(); //Make a list that contains HostInfo instances
		if (patientList.isEmpty() == true) return null;
		for (Patient p : patientList)
			patientinfoList.add(PatientToPatientInfo(p));
		return patientinfoList;
	}
	
	public PatientInfo getPatient(int id)
	{
		Patient patient = patientdb.getPatient(id); //Get all hosts.
		if (patient == null) return null;
		return PatientToPatientInfo(patient);
	}
	
	
	public Patient getPatientLogin(String username, String password)
	{
		return patientdb.getPatientLogin(username, password); //Get all hosts.

	}
	
	public PatientInfo addPatient(Patient aPatient)
	{
		patientdb.insertPatient(aPatient);
		return PatientToPatientInfo(aPatient);
	}
	
	public PatientInfo updatePatient(Patient aPatient)
	{
		if (patientdb.getPatient(aPatient.getId()) == null) return null;
		patientdb.updatePatient(aPatient);
		return PatientToPatientInfo(aPatient);
	}
	
	public Status removePatient(int id)
	{
		if (patientdb.getPatient(id) == null) return new Status(false);
		patientdb.deletePatient(id);
		return new Status(true);
	}
	
	private PatientInfo PatientToPatientInfo(Patient aPatient)
	{
		PatientInfo patientInfo = new PatientInfo(aPatient);
		List<CartItem> lst = cartItemService.getAllCartItems(patientInfo.getId()); //For every host, get the list of VMs it holds and attach that to the hashmap of VMs that each instance of HostInfo has.
		if (lst.isEmpty() == false) { patientInfo.listToMapCart(lst); 	}
		
		return patientInfo;
	}

	public Status pay(int patientid, float lat, float lon) {
		List<CartItem> lst = cartItemService.getAllCartItems(patientid);
		if (patientdb.getPatient(patientid) == null) return new Status(false);
		int subtotal = 0;
		System.out.println("2");
		List<Prescription> lst2 = prescriptionservice.getAllPrescriptions(patientid);
		for (CartItem c : lst)
		{
			if (pharmaceuticalstockdb.getPharmaceuticalStock(c.getPharmacyID(), c.getMedicineID()) == null) return new Status(false);
			if (pharmaceuticalstockdb.getPharmaceuticalStock(c.getPharmacyID(), c.getMedicineID()).getcount() < c.getCount()) return new Status(false);
			
			boolean found = false;
			if (pharmaceuticalDB.getPharmaceutical(c.getMedicineID()).getPrescription() == true)
				for (Prescription p : lst2)
				{
					if (p.getPharmaceuticalID() == c.getMedicineID())
					{
						System.out.print(p.getCount() + " " + c.getCount());
						if (p.getCount() >= c.getCount()) 
						{
							found = true;
							p.setCount(p.getCount() - c.getCount());
							prescriptionservice.updatePrescription(p);
						}

					}
					if (found == false) return new Status(false);
				}

			subtotal += c.getCount() * pharmaceuticalstockdb.getPharmaceuticalStock(c.getPharmacyID(), c.getMedicineID()).getMedicinePrice();
			

			
		}
		System.out.println("3");
		if (subtotal > patientdb.getPatient(patientid).getWallet())			
		{
			for (Prescription p2: lst2)
			{
				prescriptionservice.updatePrescription(p2);
			}
			return new Status(false);
		}
		
		
		

		OrderInfo oinfo = orderService.addOrder(patientid, new Ordr(0, patientid, false, false, lat, lon));
		List<CartItem> lst3 = cartItemService.getAllCartItems(patientid);

		for (CartItem c : lst3)
		{
			OrderItem ordItem = new OrderItem(oinfo.getId(), c.getMedicineID(), c.getCount(), patientid, c.getPharmacyID(),  c.getName(),pharmaceuticalstockdb.getPharmaceuticalStock(c.getPharmacyID(), c.getMedicineID()).getMedicinePrice() ,0);
			orderitemservice.addOrderItem(patientid, ordItem);
			List<PharmaceuticalStock> lst15 = pharmaceuticalstockdb.getPharmaceuticalStocks(c.getPharmacyID());
			for (PharmaceuticalStock P: lst15)
				{
					if (P.getMedicineID() == c.getMedicineID())
					{
						PharmaceuticalStock pstock = new PharmaceuticalStock(P.getId(), c.getMedicineID(), c.getPharmacyID(), P.getMedicinePrice(), P.getCount()-c.getCount(), c.getName(), c.getPharmacyName());
						pharmaceuticalstockdb.updatePharmaceuticalStock(pstock);
					}		
				}
		}

	


		
		
		
		
		
		
		cartItemService.removeCartItems(patientid);
		
		Patient p = patientdb.getPatient(patientid);
		p.setWallet(p.getWallet()-subtotal);
		patientdb.updatePatient(p);
		return new Status(true);
	}


}


