package com.medex.services;

import java.util.List;

import com.medex.communicationmodules.Status;
import com.medex.database.OrderDB;
import com.medex.database.OrderItemDB;
import com.medex.database.PatientDB;
import com.medex.database.PharmaceuticalDB;
import com.medex.model.OrderItem;

//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class OrderItemService {
	OrderItemDB orderItemdb = new OrderItemDB(); //(Instead of the pseudo-database)
	PatientDB patientdb = new PatientDB(); //(Instead of the pseudo-database)
	OrderDB orderdb = new OrderDB(); //(Instead of the pseudo-database)
	PharmaceuticalDB pharmaceuticalDB = new PharmaceuticalDB(); //(Instead of the pseudo-database)
	
	public OrderItemService() {} 
	
	//All what is below is just calling the functions belonging to the orderItems' database/table.
	
	public List<OrderItem>getAllOrderItems(int patientid, int orderid)
	{
		return orderItemdb.getOrderItems(patientid, orderid);
	}
	
	
	
	public OrderItem getOrderItem(int patientid, int orderid, int orderitemid)
	{
		return orderItemdb.getOrderItem(patientid, orderid, orderitemid);
	}
	
	
	public OrderItem addOrderItem(int patientid, OrderItem orderitem) {
		orderitem.setPatientID(patientid);
		System.out.println(String.valueOf(orderitem.getMedicineID()) +  " " + String.valueOf(orderitem.getOrderID()) +  " " + String.valueOf(orderitem.getPatientID()) +  " " + String.valueOf(orderitem.getPharmacyID()) +  " ");
		if (patientdb.getPatient(patientid) == null) return null;
		if (pharmaceuticalDB.getPharmaceutical(orderitem.getMedicineID()) == null) return null;
		orderItemdb.insertOrderItem(orderitem);
		return orderitem;
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// pharmacys/{id}/pharmaceuticalStocks web service requirement #5: Update a PharmaceuticalStock allocated in the pharmacy
////////////////////////////////////////////////////////////////////////////////////////

	public OrderItem updateOrderItem(int patientid, int orderid, int orderitemid, OrderItem orderitem)
	{
		if (patientdb.getPatient(patientid) == null) return null;
		if (orderdb.getOrder(patientid, orderid) == null) return null;
		if (orderItemdb.getOrderItem(patientid, orderid, orderitemid) == null) return null;
		if (pharmaceuticalDB.getPharmaceutical(orderitem.getMedicineID()) == null) return null;
		orderItemdb.updateOrderItem(orderitem);
		return orderitem;
	}
	
	public Status removeOrderItem(int patientid, int orderid, int orderitemid)
	{
		if (patientdb.getPatient(patientid) == null) return null;
		if (orderdb.getOrder(patientid, orderid) == null) return null;
		if (orderItemdb.getOrderItem(patientid, orderid, orderitemid) == null) return null;
		orderItemdb.deleteOrderItem(orderitemid);
		return new Status(true);
	}
}


