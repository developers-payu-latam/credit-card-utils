package com.pagosonline.ccutils.validators;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pagosonline.ccutils.model.CreditCardType;


/**
 * The credit card validator.
 * The actual version of this validator validates the following types of CC:
 * 
 * 1. VISA
 * 2. AMEX
 * 3. CREDENCIAL
 * 4. DINERS
 * 5. DISCOVER
 * 6. MASTERCARD
 * 
 * http://en.wikipedia.org/wiki/Bank_card_number
 * 
 * @author <a href="jorge.ulloa@pagosonline.com">Jorge M. Ulloa</a>
 * 
 * 
 * Removed deprecated code of apache validator.
 * Code clean up.
 * 
 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
 * @since 4.9.5
 *
 */
public final class CreditCardValidator {
	
	/** The message source for i18n. */
	private static MessageSource messageSource;
	
	/** Singleton credit card validator delegate instance. */
	public static CreditCardValidatorDelegate VALIDATOR;
	
	static{
		//Load the ApplicationContext and the 'creditCardMessageSource' bean
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
		
		messageSource = (MessageSource)context.getBean("creditCardMessageSource");
		
		VALIDATOR = new CreditCardValidatorDelegate(messageSource);
	}
	
	/**
	 * Default constructor.
	 */
	private CreditCardValidator(){
	}
	
	/**
	 * Returns the credit card type given a number.
	 * 
	 * @param cardNumber the credit card number.
	 * @return the {@link CreditCardType} that match. UNKNOWN otherwise.
	 */
	public static CreditCardType findCreditCardType(String cardNumber) {
		
		return VALIDATOR.findCreditCardType(cardNumber);
	}
	
	/**
	 * Validates a credit card number given its type.
	 * 
	 * @param number the number of the credit card.
	 * @param creditCardType the type of the credit card.
	 */
	public static void validate(final String number, final CreditCardType creditCardType) {
		
		VALIDATOR.validate(number, creditCardType);
	}
	
	/**
	 * Validates if a credit card will be expired for the next payment
	 * 
	 * @param expirationDate, the expiration date
	 * @param paymentDate, the next payment date
	 * @return true if will expire, false otherwise
	 */
	public static boolean willExpireOnPaymentDate(final String expirationDate, final Date paymentDate){
		
		return VALIDATOR.willExpireOnPaymentDate(expirationDate, paymentDate);
	}
	
	/**
	 * validate the security code by credit card type
	 * @param securityCode the security code
	 * @param type  the credit card type
	 * @param country the country account
	 * @throws IllegalArgumentException if security code is not valid
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	public static void validateSecurityCode(String securityCode, CreditCardType type, String country) {

		CreditCardSecurityCodeValidator.validateSecurityCode(securityCode, type, country);

	}
}
