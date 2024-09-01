package com.medex.services;

import java.util.ArrayList;
import java.util.List;


import com.medex.communicationmodules.Status;
import com.medex.database.PharmacyDB;
import com.medex.dependentresources.Pharmacy;



//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class PharmacyService {
	PharmacyDB pharmacydb = new PharmacyDB(); //(Instead of the pseudo-database)
	PharmaceuticalStockService pharmaceuticalStockService = new PharmaceuticalStockService();
	public PharmacyService() {} 
	
	//All what is below is just calling the functions belonging to the pharmacies' database/table.
	
	public List<Pharmacy>getAllPharmacies()
	{
		return pharmacydb.getPharmacies(); //Get all hosts.
		
	}
	
	public Pharmacy getPharmacy(int pharmacyid)
	{
		Pharmacy pharmacy = pharmacydb.getPharmacy(pharmacyid); //Get all hosts.
		if (pharmacy == null) return null;
		return pharmacy;
	}
}


