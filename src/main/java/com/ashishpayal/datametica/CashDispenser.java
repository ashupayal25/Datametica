package com.ashishpayal.datametica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.ashishpayal.datametica.constants.Constants;
import com.ashishpayal.datametica.model.Transaction;
import com.ashishpayal.datametica.reader.IConfigReader;

/**
 * This class implements Callable Interface which take the amount as the parameter to withdraw the cash from system,
 * so that multiple people can withdraw the cash in parallel.
 *  
 * @author Ashish_Payal
 *
 */
public class CashDispenser {
	
	IConfigReader configreader;
	
	Map<Integer, Integer> denominations;
	
	final static Logger logger = Logger.getLogger(CashDispenser.class);

	
	public CashDispenser(IConfigReader configReader) {
		this.configreader = configReader;
		try {
			this.denominations = configReader.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Transaction withdraw(Integer amount) {
		int tempAmount = amount;
		
		List<Integer> notes = new ArrayList<>(this.denominations.keySet());
		Collections.sort(notes,Comparator.reverseOrder());

		Transaction transaction = new Transaction(amount);
		Map<Integer,Integer> withdrawnNotes = new HashMap<Integer,Integer>();

		if(getBalance() < amount){
			transaction.setMessage(Constants.CASH_INSUFFICIENT);
			return transaction;
		}
		
		for (int i = 0; i < notes.size(); i++) {
			int note = notes.get(i);
			int noteAvailableCount = this.denominations.get(note);
			int noteCounter = 0;
			if (tempAmount >= note && noteAvailableCount > 0) {
				noteCounter = tempAmount / note;

				noteCounter = noteCounter > noteAvailableCount 
						? noteAvailableCount : noteCounter;

				tempAmount = tempAmount - noteCounter * note;
				this.denominations.put(note, this.denominations.
						get(note) - noteCounter);

				withdrawnNotes.put(note, noteCounter);
			}
		}
		transaction.setWithdrawnNotes(withdrawnNotes);
		transaction.setMessage(Constants.DENOMINATION_OUTPUT);
		
		return transaction;

	}
	
	private synchronized int getBalance(){
		int totalAvailableAmnt=0;
		for (Entry<Integer, Integer> e : this.denominations.entrySet()) {
			totalAvailableAmnt += (e.getKey()* e.getValue());			
		}
		return totalAvailableAmnt;
	}
}
