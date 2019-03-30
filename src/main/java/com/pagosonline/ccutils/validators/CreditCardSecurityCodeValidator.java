package com.pagosonline.ccutils.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pagosonline.ccutils.model.CreditCardCountry;
import com.pagosonline.ccutils.model.CreditCardType;
import com.pagosonline.ccutils.util.i18n.CreditCardMessages;

/**
 * The credit card security code validator.
 *
 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
 */
public final class CreditCardSecurityCodeValidator {

	/**
	 * Credit card security code validator utility constructor
	 */
	private CreditCardSecurityCodeValidator() {

		// Default private constructor
	}

	/**
	 * The message source for i18n.
	 */
	private static MessageSource messageSource;

	/**
	 * AMEX Cvv2 Credit Card length: 4 digits
	 */
	private static final int AMEX_CVV2_LENGTH = 4;
	
	/**
	 * AMEX Cvv2 Credit Card length: 3 digits for Perú
	 */
	private static final int AMEX_CVV2_PE_LENGTH = 3;	
	
	/**
	 *  Cvv2 Credit Card Min length: 1 digits 
	 */
	private static final int DEFAULT_CVV2_MIN_LENGTH = 1;
	
	/**
	 *  Cvv2 Credit Card Max length: 4 digits 
	 */
	private static final int DEFAULT_CVV2_MAX_LENGTH = 4;

	static {
		// Load the ApplicationContext and the 'creditCardMessageSource' bean
		@SuppressWarnings("resource")
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "application-context.xml" });
		messageSource = (MessageSource) context.getBean("creditCardMessageSource");
	}
	
	/**
	 * validate the security code by credit card type
	 *
	 * @param securityCode the security code
	 * @param type the credit card type
	 * @param credit card country
	 * @throws IllegalArgumentException if security code is not valid
	 * @author <a href="mailto:david.hidalgo@payulatam.com">david.hidalgo</a>
	 */
	public static void validateSecurityCode(final String securityCode, final CreditCardType type, final String country) {

		switch (type) {
			case AMEX:
				if (StringUtils.isBlank(securityCode)) {
					throw new IllegalArgumentException(messageSource.getMessage(
							CreditCardMessages.CREDIT_CARD_INVALID_SECURITY_CODE,
							new Object[] {}, LocaleContextHolder.getLocale()));
				}
				if(country.equals(CreditCardCountry.PE.name())) {
					if (securityCode.length() != AMEX_CVV2_PE_LENGTH) {
						if(securityCode.length() != AMEX_CVV2_LENGTH) {
							throw new IllegalArgumentException(messageSource.getMessage(
									CreditCardMessages.CREDIT_CARD_AMEX_PE_INVALID_LENGTH_SECURITY_CODE,
									new Object[] {}, LocaleContextHolder.getLocale()));
						}
					}
				}else {
					if (securityCode.length() != AMEX_CVV2_LENGTH) {
						throw new IllegalArgumentException(messageSource.getMessage(
								CreditCardMessages.CREDIT_CARD_AMEX_INVALID_LENGTH_SECURITY_CODE,
								new Object[] {}, LocaleContextHolder.getLocale()));
					}
				}
				break;
			default:
				if(StringUtils.isBlank(securityCode)) {
					throw new IllegalArgumentException(messageSource.getMessage(
							CreditCardMessages.CREDIT_CARD_INVALID_SECURITY_CODE,
							new Object[] {}, LocaleContextHolder.getLocale()));
				}
				
				if(securityCode.length() <  DEFAULT_CVV2_MIN_LENGTH || securityCode.length() > DEFAULT_CVV2_MAX_LENGTH) {
					throw new IllegalArgumentException(messageSource.getMessage(
							CreditCardMessages.CREDIT_CARD_INVALID_LENGTH_SECURITY_CODE,
							new Object[] {}, LocaleContextHolder.getLocale()));
				}
				
				break;
		}

	}
}
