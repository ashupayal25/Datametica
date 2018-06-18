package com.ashishpayal.datametica.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is the entity class which contains all the details of any transaction done by any user.
 * @author Ashish_Payal
 *
 */
public class Transaction {

	private String message;
	private Map<Integer, Integer> withdrawnNotes;
	private int amount;

	public Transaction(int amount) {
		this.amount = amount;
		this.withdrawnNotes = new HashMap<>();
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setWithdrawnNotes(Map<Integer, Integer> withdrawnNotes) {
		this.withdrawnNotes = withdrawnNotes;
	}

	@Override
	public String toString() {
		System.out.println("--------------------------------------------");
		if(this.withdrawnNotes==null || this.withdrawnNotes.isEmpty()){
			return this.message+amount;
		}
		String str=this.message+amount;
		for(Entry<Integer, Integer> e : withdrawnNotes.entrySet()){
			str +=" \n"+e.getValue() + " notes of " + e.getKey()+" Rs.";
		}
		return str;
	}

	
}
