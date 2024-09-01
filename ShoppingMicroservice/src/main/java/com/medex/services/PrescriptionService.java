package com.medex.services;

import java.util.List;

import com.medex.communicationmodules.Status;
import com.medex.database.PrescriptionDB;
import com.medex.dependentresources.Prescription;
import com.medex.database.PatientDB;
import com.medex.database.PharmaceuticalDB;

//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class PrescriptionService {
	PrescriptionDB prescriptiondb = new PrescriptionDB(); //(Instead of the pseudo-database)
	PatientDB patientdb = new PatientDB(); //(Instead of the pseudo-database)
	PharmaceuticalDB pharmaceuticaldb = new PharmaceuticalDB(); //(Instead of the pseudo-database)
	
	public PrescriptionService() {} 
	
	//All what is below is just calling the functions belonging to the prescriptions' database/table.
	
	public List<Prescription>getAllPrescriptions(int patientid)
	{
		return prescriptiondb.getPrescriptions(patientid);
	}
	
	
	
	public Prescription getPrescription(int patientid, int prescriptionid)
	{
		return prescriptiondb.getPrescription(patientid, prescriptionid);
	}

	
	public Prescription updatePrescription(Prescription aPrescription)
	{
		prescriptiondb.updatePrescription(aPrescription);
		return aPrescription;
	}
	
}


