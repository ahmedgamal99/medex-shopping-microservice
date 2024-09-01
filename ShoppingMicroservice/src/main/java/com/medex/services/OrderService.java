package com.medex.services;

import java.util.ArrayList;
import java.util.List;

import com.medex.communicationmodules.OrderInfo;
import com.medex.communicationmodules.Status;
import com.medex.database.OrderDB;
import com.medex.database.PatientDB;
import com.medex.database.PharmaceuticalStockDB;
import com.medex.dependentresources.Prescription;
import com.medex.model.OrderItem;
import com.medex.model.Patient;
import com.medex.model.CartItem;
import com.medex.model.Ordr;

//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class OrderService {
	OrderDB orderdb = new OrderDB(); //(Instead of the pseudo-database)
	OrderItemService orderItemService = new OrderItemService();
	PatientDB patientdb = new PatientDB();
	PharmaceuticalStockDB pharmaceuticalstockdb = new PharmaceuticalStockDB();
	PrescriptionService prescriptionservice = new PrescriptionService();
	public OrderService() {} 
	
	//All what is below is just calling the functions belonging to the pharmacies' database/table.
	
	public List<OrderInfo>getAllOrders(int patientid)
	{
		List<Ordr> orderList = orderdb.getOrders(patientid); //Get all hosts.
		List<OrderInfo> orderinfoList = new ArrayList<OrderInfo>(); //Make a list that contains HostInfo instances
		if (orderList.isEmpty() == true) return null;	
		for (Ordr o : orderList) {
			orderinfoList.add(OrderToOrderInfo(patientid, o));
			}		
		return orderinfoList;
	}
	
	public OrderInfo getOrder(int patientid, int orderid)
	{
		Ordr order = orderdb.getOrder(patientid, orderid); //Get all hosts.
		if (order == null) return null;
		return OrderToOrderInfo(patientid, order);
	}
	
	public OrderInfo addOrder(int patientid, Ordr order)
	{
		order.setPatientId(patientid);
		if (patientdb.getPatient(patientid) == null) return null;
		orderdb.insertOrder(order);
		return OrderToOrderInfo(patientid, order);
	}
	
	public OrderInfo updateOrder(int patientid, int orderid, Ordr order)
	{
		if (patientdb.getPatient(patientid) == null) return null;
		if (orderdb.getOrder(patientid, orderid) == null) return null;
		orderdb.updateOrder(order);
		return OrderToOrderInfo(patientid, order);
	}
	
	
	public Status Refund(int patientid, int orderid)
	{
		System.out.println(patientid + " " + orderid);
		System.out.println("X1");
		if (patientdb.getPatient(patientid) == null) return new Status(false);
		System.out.println("X2");
		if (orderdb.getOrder(patientid, orderid) == null) return new Status(false);
		System.out.println("X3");
		Ordr d = orderdb.getOrder(patientid, orderid);
		System.out.println("X4");
		if (d.getDone() == true || d.getInProgress() == true) return new Status(false);
		System.out.println("X5");
		int subtotal = 0;
		List<OrderItem> lst = orderItemService.getAllOrderItems(patientid, orderid);
		List<Prescription> lst2 = prescriptionservice.getAllPrescriptions(patientid);
		for (OrderItem oitem : lst)
		{
			if (pharmaceuticalstockdb.getPharmaceuticalStock(oitem.getPharmacyID(), oitem.getMedicineID()) == null) return new Status(true);
			int prescIDFound = -1;
			for (Prescription p : lst2)
			{
				if (p.getPharmaceuticalID() == oitem.getMedicineID())
				{
						prescIDFound = p.getId();
						break;
				}
			}
			if (prescIDFound != -1)
			{
				Prescription prescFound = prescriptionservice.getPrescription(patientid, prescIDFound);
				prescFound.setCount(prescFound.getCount() + oitem.getCount());
				prescriptionservice.updatePrescription(prescFound);
			}
			subtotal += oitem.getCount() * oitem.getPrice();
		}
		
		Patient p = patientdb.getPatient(patientid);
		p.setWallet(p.getWallet()+((subtotal)/2));
		patientdb.updatePatient(p);
		
		Ordr o = orderdb.getOrder(patientid, orderid);
		o.setDone(true);
		orderdb.updateOrder(o);
		return new Status(true);
	}

	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// pharmacys/{id}/pharmaceuticalStocks web service requirement #5: Update a PharmaceuticalStock allocated in the pharmacy
////////////////////////////////////////////////////////////////////////////////////////
	
	public Status removeOrder(int patientid, int orderid)
	{
		if (orderdb.getOrder(patientid, orderid) == null) return new Status(false);
		orderdb.deleteOrder(orderid);
		return new Status(true);
	}
	
	
	private OrderInfo OrderToOrderInfo(int patientid, Ordr aOrder)
	{
		OrderInfo orderInfo = new OrderInfo(aOrder);
		List<OrderItem> lst = orderItemService.getAllOrderItems(patientid, aOrder.getId()); //For every host, get the list of VMs it holds and attach that to the hashmap of VMs that each instance of HostInfo has.
		if (lst.isEmpty() == false) { orderInfo.listToMap(lst); }
		
		return orderInfo;
	}
}


