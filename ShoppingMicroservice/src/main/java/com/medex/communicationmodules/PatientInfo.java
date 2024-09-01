package com.medex.communicationmodules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import com.medex.model.CartItem;
import com.medex.model.Ordr;
import com.medex.model.Patient;


//The patient class
//Now, the fields that we will annotate will be stored in the hosts table.
public class PatientInfo {
	int id;
	String name;
	int wallet;
	Map<Integer, CartItem> cartItems = new HashMap<>(); 
	Map<Integer, OrderInfo> orders = new HashMap<>(); 
	public PatientInfo() {}

	
	//Non default constructor
	public PatientInfo(int id, String aname, int awallet) {
		this.id = id;
		this.name = aname;
		this.wallet = awallet;
	}
	public PatientInfo(Patient p) {
		this.id = p.getId();
		this.name = p.getName();
		this.wallet = p.getWallet();
	}
	public int getId()
	{
		return id;
	}

	public int getWallet() {
		return wallet;
	}
	public void setWallet(int awallet) {
		this.wallet = awallet;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void listToMapCart(List<CartItem> lst)
	{
		for (CartItem i : lst) 
		{
			cartItems.put(i.getId(),i);
		}
	}
	public Map<Integer, CartItem> getCartItems()
	{
		return cartItems;
	}
	public void listToMapOrder(List<OrderInfo> lst)
	{
		for (OrderInfo i : lst) 
		{
			orders.put(i.getId(),i);
		}
	}
	public Map<Integer, OrderInfo> getOrders()
	{
		return orders;
	}
}

