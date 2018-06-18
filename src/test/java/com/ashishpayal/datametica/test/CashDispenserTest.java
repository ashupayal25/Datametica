package com.ashishpayal.datametica.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ashishpayal.datametica.CashDispenser;
import com.ashishpayal.datametica.constants.Constants;
import com.ashishpayal.datametica.model.Transaction;
import com.ashishpayal.datametica.reader.IConfigReader;
import com.ashishpayal.datametica.validation.Validator;

public class CashDispenserTest {
	
	private CashDispenser cashDispenser;
	
	private Validator validator;
	
	@BeforeMethod
	public void init() {
		IConfigReader configReader = new IConfigReader() {
			@Override
			public Map<Integer, Integer> read() throws Exception {
				Map<Integer,Integer> denom = new HashMap<>();
				denom.put(10, 1000);
		    	denom.put(20, 200);
		    	denom.put(2000, 2);
		    	denom.put(500, 20);
		    	return denom;
			}
			
		};
		cashDispenser = new CashDispenser(configReader);
		validator = new Validator();
		
	}
	
	//To validate insufficient Balance
	@Test
	void testInfufficientBalanceCalculateDenominations(){
		Integer amount = 500000;
		Transaction transaction = cashDispenser.withdraw(amount);
		assertEquals(Constants.CASH_INSUFFICIENT+amount, transaction.toString());
	}
	
	@Test
	void testInvalidAmountCalculateDenominations(){
		Integer amount = 12;
		if(validator.validate(amount)){
			Transaction transaction = cashDispenser.withdraw(amount);
	    	assertEquals(Constants.INVALID_AMOUNT+amount, transaction.toString());
		}
		
	}	

    @Test
    void testCalculateDenominations() {
    	String expectedResult = "Denominations dispensed for amount 5000 \n2 notes of 2000 Rs. \n2 notes of 500 Rs.";
    	String amount = "5000";
    	Transaction transaction = cashDispenser.withdraw(5000);
    	assertEquals(expectedResult, transaction.toString());

    	//Now 2000 Rs notes are not available
    	expectedResult = "Denominations dispensed for amount 5000 \n10 notes of 500 Rs.";
    	transaction = cashDispenser.withdraw(5000);
    	assertEquals(expectedResult, transaction.toString());
    	
    }
    
}