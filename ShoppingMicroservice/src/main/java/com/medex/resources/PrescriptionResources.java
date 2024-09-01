package com.medex.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.medex.communicationmodules.Status;
import com.medex.dependentresources.Prescription;
import com.medex.services.PrescriptionService;


//Request resources which acts as a layer before our Prescription services
public class PrescriptionResources {
	PrescriptionService prescriptionService = new PrescriptionService();

	public PrescriptionResources() {
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prescription> getPrescriptions(@PathParam("Patientid") int patientid) {
		return prescriptionService.getAllPrescriptions(patientid);
	}

	@GET
	@Path("{Prescriptionid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Prescription getPrescription(@PathParam("Patientid") int patientid, @PathParam("Prescriptionid") int prescriptionid) {
		return prescriptionService.getPrescription(patientid, prescriptionid);
	}
}
