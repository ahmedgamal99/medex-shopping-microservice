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
import com.medex.dependentresources.Pharmacy;
import com.medex.services.PharmacyService;


//Request resources which acts as a layer before our Pharmacy services
@Path("/pharmacies")
public class PharmacyResources {
	PharmacyService pharmacyService = new PharmacyService();

	public PharmacyResources() {}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pharmacy> getPharmacies() {
		return pharmacyService.getAllPharmacies();
	}



	@Path("{Pharmacyid}/pharmaceuticalsStock")
	public PharmaceuticalsStockResources getPharmaceuticalsStock()
	{
		return new PharmaceuticalsStockResources();
	}
}
