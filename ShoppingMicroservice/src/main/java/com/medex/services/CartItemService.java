package com.medex.services;

import java.util.List;

import com.medex.communicationmodules.Status;
import com.medex.database.CartItemDB;
import com.medex.database.PatientDB;
import com.medex.database.PharmaceuticalDB;
import com.medex.model.CartItem;

//This is the "backend" of our resources. This is where the logic is executed; the logic is basic since the database handles itself very well.
public class CartItemService {
	CartItemDB cartItemdb = new CartItemDB(); //(Instead of the pseudo-database)
	PatientDB patientdb = new PatientDB(); //(Instead of the pseudo-database)
	PharmaceuticalDB pharmaceuticalDB = new PharmaceuticalDB(); //(Instead of the pseudo-database)
	
	public CartItemService() {} 
	
	//All what is below is just calling the functions belonging to the cartItems' database/table.
	
	public List<CartItem>getAllCartItems(int patientid)
	{
		return cartItemdb.getCartItems(patientid);
	}
	
	
	
	public CartItem getCartItem(int patientid, int cartitemid)
	{
		return cartItemdb.getCartItem(patientid, cartitemid);
	}
	
	
	public CartItem addCartItem(int patientid, CartItem cartitem) {
		cartitem.setPatientID(patientid);
		if (patientdb.getPatient(patientid) == null) return null;
		if (pharmaceuticalDB.getPharmaceutical(cartitem.getMedicineID()) == null) return null;
		
		List<CartItem> lst = cartItemdb.getCartItems(patientid);
		if (!lst.isEmpty())
		{
			for (CartItem l : lst)
			{
				if (cartitem.getMedicineID() == l.getMedicineID())
				{
					return null;
				}
			}
		}
		cartItemdb.insertCartItem(cartitem);
		return cartitem;
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// pharmacys/{id}/pharmaceuticalStocks web service requirement #5: Update a PharmaceuticalStock allocated in the pharmacy
////////////////////////////////////////////////////////////////////////////////////////

	public CartItem updateCartItem(int patientid, int cartitemid, CartItem cartItem)
	{
		if (patientdb.getPatient(patientid) == null) return null;
		if (pharmaceuticalDB.getPharmaceutical(cartItem.getMedicineID()) == null) return null;
		if (cartItemdb.getCartItem(patientid, cartitemid) == null) return null;
		cartItemdb.updateCartItem(cartItem);
		return cartItem;
	}
	
	public Status removeCartItem(int patientid, int cartitemid)
	{
		if (patientdb.getPatient(patientid) == null) return new Status(false);
		if (cartItemdb.getCartItem(patientid, cartitemid) == null) return new Status(false);
		cartItemdb.deleteCartItem(cartitemid);
		return new Status(true);
	}
	
	
	public Status removeCartItems(int patientid)
	{
		if (patientdb.getPatient(patientid) == null) return new Status(false);
		cartItemdb.deleteCartItems(patientid);
		return new Status(true);
	}
	
	
}


