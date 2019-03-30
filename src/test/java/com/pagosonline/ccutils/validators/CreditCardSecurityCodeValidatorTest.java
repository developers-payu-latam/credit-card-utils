package com.pagosonline.ccutils.validators;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pagosonline.ccutils.model.CreditCardCountry;
import com.pagosonline.ccutils.model.CreditCardType;

/**
 * the test class to CreditCardSecurityCodeValidator
 * 
 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
 *
 */
public class CreditCardSecurityCodeValidatorTest {

	
	@BeforeTest 
	public void init(){
		
		Locale locale= new Locale("es", "CO");  
		LocaleContextHolder.setLocale(locale);
	}
	
	
	
	/**
	 * test for invalid amex security code to AR
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateAMEXSecurityCodeTest(){
		
		CreditCardValidator.validateSecurityCode("123", CreditCardType.AMEX, CreditCardCountry.AR.name());
		Assert.fail("is valid?");
	}

	/**
	 * test for invalid amex security code to PE
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateAMEXSecurityCode2Test(){
		
		CreditCardValidator.validateSecurityCode("", CreditCardType.AMEX, CreditCardCountry.PE.name());
		Assert.fail("is valid?");
	}
	
	/**
	 * test for valid amex security code with 4 digits to AR
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	@Test
	public void validateAMEXSecurityCode3Test(){
		
		CreditCardValidator.validateSecurityCode("1231", CreditCardType.AMEX, CreditCardCountry.AR.name());

	}
	
	/**
	 * test for valid visa security code with 3 digits to PE
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	@Test
	public void validateVISASecurityCodeTest(){
		
		CreditCardValidator.validateSecurityCode("123", CreditCardType.VISA, CreditCardCountry.PE.name());

	}
	
	/**
	 * test for valid amex security code with 4 digits to PE
	 * @author <a href="mailto:johan.navarrete@payulatam.com">Johan Navarrete</a>
	 */
	@Test
	public void validateAMEXSecurityCode4DigitsPeTest() {

		CreditCardValidator.validateSecurityCode("1231", CreditCardType.AMEX,
				CreditCardCountry.PE.name());
	}
	
	/**
	 * test for valid amex security code with 3 digits to PE
	 * @author <a href="mailto:johan.navarrete@payulatam.com">Johan Navarrete</a>
	 */
	@Test
	public void validateAMEXSecurityCode3DigitsPeTest() {

		CreditCardValidator.validateSecurityCode("123", CreditCardType.AMEX,
				CreditCardCountry.PE.name());
	}
	
	/**
	 * test for valid amex security code with 2 digits to PE
	 * @author <a href="mailto:johan.navarrete@payulatam.com">Johan Navarrete</a>
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateAmexSecurityCode2DigitsPeTest() {

		CreditCardValidator.validateSecurityCode("12", CreditCardType.AMEX,
				CreditCardCountry.PE.name());
		Assert.fail("is valid?");
	}
}
