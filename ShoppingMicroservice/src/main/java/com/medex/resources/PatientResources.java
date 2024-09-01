//	 <Context docBase="ShoppingMicroservice" path="/ShoppingMicroservice" reloadable="true" source="org.eclipse.jst.jee.server:ShoppingMicroservice"/></Host>
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

import com.medex.communicationmodules.LatLon;
import com.medex.communicationmodules.PatientInfo;
import com.medex.communicationmodules.Status;
import com.medex.model.Patient;
import com.medex.services.PatientService;


//Request resources which acts as a layer before our Patient services
@Path("/patients")
public class PatientResources {
	PatientService patientService = new PatientService();

	public PatientResources() {
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientInfo> getPatients() {
		return patientService.getAllPatients();
	}

	@GET
	@Path("{Patientid}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientInfo getPatient(@PathParam("Patientid") int id) {
		return patientService.getPatient(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PatientInfo addPatient(Patient aPatient) {
		return patientService.addPatient(aPatient);
	}

	@PUT
	@Path("{Patientid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PatientInfo updatePatient(@PathParam("Patientid") int id, Patient Patient) {
		Patient.setId(id);
		return patientService.updatePatient(Patient);
	}

	@POST
	@Path("{Patientid}/pay")
	public Status payForCart(@PathParam("Patientid") int patientid, LatLon latlon)
	{
		System.out.println("1");
		return patientService.pay(patientid, latlon.getLat(), latlon.getLon());
	}
	
	@GET
	@Path("/login/{username}/{password}")
	public Patient getPatientLogin(@PathParam("username") String username, @PathParam("password") String password)
	{
		return patientService.getPatientLogin(username, password);
	}
	
	
	
	@DELETE
	@Path("{Patientid}")
	public Status removePatient(@PathParam("Patientid") int id, Patient Patient) {
		return patientService.removePatient(id);
	}
	@Path("{Patientid}/cartItems")
	public CartItemResources getCartItems(@PathParam("Patientid")int id)
	{
		return new CartItemResources();
	}
	@Path("{Patientid}/orders")
	public OrderResources getOrders()
	{
		return new OrderResources();
	}
	@Path("{Patientid}/prescriptions")
	public PrescriptionResources getPrescriptions()
	{
		return new PrescriptionResources();
	}

}
