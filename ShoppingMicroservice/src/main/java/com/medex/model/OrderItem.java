package com.medex.model;

import javax.persistence.*;

//The orderItem class

@Entity //A 'serializable' entity
@Table(name = "OrderItem") //Where this entity will be placed
//Now, the fields that we will annotate will be stored in the hosts table.
public class OrderItem {
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //"Do this identification for me, generate that specific ID for me
	@Column(name="id")
	int id;
	@Column(name="patientID")
	int patientID;
	@Column(name="orderID") //Which column?
	int orderID;
	@Column(name="medicineID")
	int medicineID;
	@Column(name="count")
	int count;
	@Column(name="pharmacyID")
	int pharmacyID;
	@Column(name="price")
	int price;
	@Column(name="name")
	String name;
	
	
	public OrderItem() {}

	//Non default constructor
	public OrderItem(int orderID, int amedicineID, int acount, int apatientID, int aPharmacyID, String name, int price, int id) {
		this.id = id;
		this.orderID = orderID;
		this.patientID = apatientID;
		this.medicineID = amedicineID;
		this.count = acount;
		this.name = name;
		this.price = price;
		this.pharmacyID = aPharmacyID;
	}
	public int getOrderID()
	{
		return orderID;
	}
	public int getMedicineID()
	{
		return medicineID;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int acount) {
		this.count = acount;
	}
	public void setOrderID(int aorderID)
	{
		this.orderID = aorderID;
	}
	public void setMedicineID(int amedicineID)
	{
		this.medicineID = amedicineID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getPharmacyID() {
		return pharmacyID;
	}

	public void setPharmacyID(int pharmacyID) {
		this.pharmacyID = pharmacyID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}