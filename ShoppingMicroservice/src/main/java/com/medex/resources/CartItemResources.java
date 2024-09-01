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
import com.medex.model.CartItem;
import com.medex.services.CartItemService;


//Request resources which acts as a layer before our CartItem services

public class CartItemResources {
	CartItemService cartItemService = new CartItemService();

	public CartItemResources() {
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartItem> getCartItems(@PathParam("Patientid") int patientid) {
		return cartItemService.getAllCartItems(patientid);
	}

	@GET
	@Path("{CartItemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public CartItem getCartItem(@PathParam("Patientid") int patientid, @PathParam("CartItemid") int cartitemid) {
		return cartItemService.getCartItem(patientid, cartitemid);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartItem addCartItem(@PathParam("Patientid")int patientid, CartItem cartitem)
	{

		return cartItemService.addCartItem(patientid, cartitem);
	}
	

	//Requirement 9: Create a REST API the provides the ability to delete a PharmaceuticalStock that is allocated to a host (Note: This place it back into the pool!)
	//hosts/{id}/pharmaceuticalStocks web service Requirement #4: Delete a PharmaceuticalStock from the host.
	//Return a JSON object with status true if delete is successful or false if the delete is not successful.
	@DELETE
	@Path("{CartItemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Status removeCartItem(@PathParam("Patientid")int patientid, @PathParam("CartItemid") int cartitemid)
	{
		return cartItemService.removeCartItem(patientid, cartitemid);
	}
	
	
	//hosts/{id}/pharmaceuticalStocks web service requirement #5: Update a PharmaceuticalStock allocated in the host
	//return a JSON object with status true if update is successful or false if the update is not successful. The host must contain adequate resources.
	@PUT
	@Path("{CartItemid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartItem updateCartItem(@PathParam("Patientid")int patientid, @PathParam("CartItemid") int cartitemid, CartItem cartitem)
	{
		cartitem.setId(cartitemid);
		return cartItemService.updateCartItem(patientid, cartitemid, cartitem);
	}
}
