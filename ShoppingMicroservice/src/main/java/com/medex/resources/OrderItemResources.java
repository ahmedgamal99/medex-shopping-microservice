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
import com.medex.model.OrderItem;
import com.medex.services.OrderItemService;


//Request resources which acts as a layer before our OrderItem services
public class OrderItemResources {
	OrderItemService orderItemService = new OrderItemService();

	public OrderItemResources() {
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderItem> getOrderItems(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid) {
		return orderItemService.getAllOrderItems(patientid, orderid);
	}

	@GET
	@Path("{OrderItemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderItem getOrderItem(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid, @PathParam("OrderItemid") int orderitemid) {
		return orderItemService.getOrderItem(patientid, orderid, orderitemid);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderItem addOrderItem(@PathParam("Patientid") int patientid, OrderItem orderitem) {
		return orderItemService.addOrderItem(patientid, orderitem);
	}

	@PUT
	@Path("{OrderItemid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderItem updateOrderItem(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid, @PathParam("OrderItemid") int orderitemid, OrderItem orderitem) {
		orderitem.setId(orderitemid);
		orderitem.setPatientID(patientid);
		orderitem.setOrderID(orderid);
		return orderItemService.updateOrderItem(patientid, orderid, orderitemid, orderitem);
	}

	@DELETE
	@Path("{OrderItemid}")
	public Status removeOrderItem(@PathParam("Patientid") int patientid, @PathParam("Orderid") int orderid, @PathParam("OrderItemid") int orderitemid) {
		return orderItemService.removeOrderItem(patientid, orderid, orderitemid);
	}
}
