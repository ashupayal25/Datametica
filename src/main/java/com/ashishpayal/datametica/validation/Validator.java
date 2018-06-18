package com.ashishpayal.datametica.validation;

public class Validator {
	
	public boolean validate(Integer amount) {
		if(amount % 10 != 0) {
			return false;
		}else if(amount < 0)
			return false;
		return true;
	}
}
