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

import com.medex.communicationmodules.OrderInfo;
import com.medex.communicationmodules.Status;
import com.medex.dependentresources.Prescription;
import com.medex.model.Ordr;
import com.medex.services.OrderService;


//Request resources which acts as a layer before our Order services

public class OrderResources {
	OrderService orderService = new OrderService();

	public OrderResources() {
	}


	
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderInfo> getOrders(@PathParam("Patientid")int patientid) {
		return orderService.getAllOrders(patientid);
	}

	@GET
	@Path("{Orderid}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderInfo getOrder(@PathParam("Patientid")int patientid, @PathParam("Orderid") int orderid) {
		return orderService.getOrder(patientid, orderid);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderInfo addOrder(@PathParam("Patientid")int patientid, Ordr order) {
		return orderService.addOrder(patientid, order);
	}
	
	
	
	@PUT
	@Path("{Orderid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderInfo updateOrder(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid, Ordr order) {
		order.setId(orderid);
		order.setPatientId(patientid);
		return orderService.updateOrder(patientid, orderid, order);
	}

	@DELETE
	@Path("{Orderid}")
	public Status removeOrder(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid) {
		return orderService.removeOrder(patientid, orderid);
	}

	
	@Path("{Orderid}/orderItems")
	public OrderItemResources getOrderItems()
	{
		return new OrderItemResources();
	}
	
	
	@DELETE
	@Path("{Orderid}/refund")
	@Produces(MediaType.APPLICATION_JSON)
	public Status getPrescriptions(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid) {
		return orderService.Refund(patientid, orderid);
	}
	

	


	
	
	
}
