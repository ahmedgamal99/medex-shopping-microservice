package com.medex.model;

import javax.persistence.*;

//The cartItem class
@Entity //A 'serializable' entity
@Table(name = "CartItem") //Where this entity will be placed
//Now, the fields that we will annotate will be stored in the hosts table.
public class CartItem {
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //"Do this identification for me, generate that specific ID for me
	@Column(name="id")
	int id;
	@Column(name="patientID")
	int patientID;
	@Column(name="medicineID")
	int medicineID;
	@Column(name="name")
	String name;
	@Column(name="pharmacyID")
	int pharmacyID;
	@Column(name="pharmacyName")
	String pharmacyName;
	@Column(name="count")
	int count;
	
	
	public CartItem() {}

	//Non default constructor
	public CartItem(int id, int amedicineID, int apharmacyID, int acount, int apatientID, String name, String pharmacyName) {
		this.id = id;
		this.medicineID = amedicineID;
		this.pharmacyID = apharmacyID;
		this.count = acount;
		this.patientID = apatientID;
		this.name = name;
		this.pharmacyName = pharmacyName;
	}
	public int getId()
	{
		return id;
	}
	public int getMedicineID()
	{
		return medicineID;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int acount) {
		this.count = acount;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setMedicineID(int amedicineID)
	{
		this.medicineID = amedicineID;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	
}

