package com.ashishpayal.datametica;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ashishpayal.datametica.constants.Constants;
import com.ashishpayal.datametica.model.Transaction;
import com.ashishpayal.datametica.reader.FileConfigReader;
import com.ashishpayal.datametica.reader.IConfigReader;
import com.ashishpayal.datametica.validation.Validator;

public class Runner {
	
	final static Logger logger = Logger.getLogger(Runner.class);

	public static void main(String[] args) {
		
		if(args.length==0){
			logger.info(Constants.USER_INPUT_NOT_ENTERED);
			return;
		}
		
		IConfigReader configReaderService = new FileConfigReader(Constants.DENOMINATION_DETAILS);
		CashDispenser atm = new CashDispenser(configReaderService);
		Validator validator = new Validator();
		
		List<Integer> amountsRequested = new ArrayList<>();
		for(int i=0; i< args.length;i++) {
			if(StringUtils.isNumeric(args[i])){
				
			}
			final Integer amount;
			try {
				amount = Integer.parseInt(args[i]);
				amountsRequested.add(amount);
			} catch(NumberFormatException e) {
				logger.info(Constants.INVALID_USER_INPUT);
			}
		}
		
		ExecutorService ex = Executors.newCachedThreadPool();
		List<Future<Transaction>> result=new ArrayList<Future<Transaction>>();
		for(Integer amount: amountsRequested) {
			Future<Transaction> cashWithdrawn;
			
			if(validator.validate(amount)){
				cashWithdrawn = ex.submit(() -> atm.withdraw(amount));
				result.add(cashWithdrawn);
			}else{
				logger.info(Constants.INVALID_USER_INPUT+amount);
				//System.out.println(Constants.INVALID_USER_INPUT+amount);
			}
		}
		
		ex.shutdown();
		for(Future<Transaction> f:result){
			try {
				//System.out.println(f.get());
				logger.info(f.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}

}
