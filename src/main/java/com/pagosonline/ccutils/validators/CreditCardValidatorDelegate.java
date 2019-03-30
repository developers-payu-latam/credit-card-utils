package com.pagosonline.ccutils.validators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.CodeValidator;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.pagosonline.ccutils.format.CreditCardFormatUtils;
import com.pagosonline.ccutils.model.CreditCardType;
import com.pagosonline.ccutils.util.i18n.CreditCardMessages;

/**
 * The credit card validator delegate.
 * 
 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
 * @since 4.9.5
 */
public class CreditCardValidatorDelegate {

	/** The message source for i18n. */
	private MessageSource messageSource;
	
	/**
	 * Instantiates a new credit card validator delegate.
	 *
	 * @param messageSource the message source
	 */
	public CreditCardValidatorDelegate(MessageSource messageSource) {
		
		this.messageSource = messageSource;
	}

	/**
	 * Find credit card type.
	 *
	 * @param cardNumber the card number
	 * @return the credit card type
	 */
	public CreditCardType findCreditCardType(String cardNumber) {
		
		cardNumber = cardNumber.replaceAll("\\D+", "");
		
		// types with a defined regular expression
		CreditCardType[] types = new CreditCardType[] {
				CreditCardType.VISA,
				CreditCardType.MASTERCARD,
				CreditCardType.AMEX,
				CreditCardType.DISCOVER,
				CreditCardType.DINERS,
				CreditCardType.NARANJA,
				CreditCardType.SHOPPING,				
				CreditCardType.CABAL,
				CreditCardType.ARGENCARD,
				CreditCardType.CENCOSUD,
				CreditCardType.HIPERCARD,
				CreditCardType.CODENSA,
				CreditCardType.CREDENCIAL				
				};
		
		for (int i = 0; i < types.length; i++) {
			
			if(cardNumber.matches(types[i].getRegExp())) {
				return types[i];
			}
		}
		
		return CreditCardType.UNKNOWN;
	}
	
	/**
	 * Validates a credit card number given its type.
	 *
	 * @param number the number
	 * @param creditCardType the credit card type
	 */
	public void validate(final String number, final CreditCardType creditCardType) {
		
		if (StringUtils.isBlank(number)) {
			throw new IllegalArgumentException(messageSource.getMessage(CreditCardMessages.CREDIT_CARD_NUMBER_NOT_NULL_EMPTY,
					new Object[] {}, LocaleContextHolder.getLocale()));
		}

		if (creditCardType == null) {
			throw new IllegalArgumentException(messageSource.getMessage(CreditCardMessages.CREDIT_CARD_TYPE_NOT_NULL,
					new Object[] {}, LocaleContextHolder.getLocale()));
		}		
		
		List<CodeValidator> issuerValidators = buildIssuerValidators(creditCardType);
		
		org.apache.commons.validator.routines.CreditCardValidator validator = 
				new org.apache.commons.validator.routines.CreditCardValidator(issuerValidators.toArray(new CodeValidator[issuerValidators.size()]));
		
		if( !validator.isValid(number)) {
			
			throw new IllegalArgumentException(messageSource.getMessage(CreditCardMessages.CREDIT_CARD_NUMBER_NOT_VALID,
					new Object[] { CreditCardFormatUtils.mask(number), creditCardType }, LocaleContextHolder.getLocale()));
		}		
	}
	
	/**
	 * Validates if a credit card will be expired for the next payment
	 * 
	 * @param expirationDate, the expiration date
	 * @param paymentDate, the next payment date
	 * @return true if will expire, false otherwise
	 */
	public boolean willExpireOnPaymentDate(final String expirationDate, final Date paymentDate){
		
		String pattern = "(19|20)(\\d{2})/(0[1-9]|1[0-2])";
		if (!expirationDate.matches(pattern)) {
			throw new IllegalArgumentException("The expiration date is not well formed (AAAA/MM)");
		}

		int year = Integer.valueOf(expirationDate.split("/")[0]);
		int month = Integer.valueOf(expirationDate.split("/")[1]);

		Calendar paymentDateCal = Calendar.getInstance();
		paymentDateCal.setTime(paymentDate);
		int monthPayment = paymentDateCal.get(Calendar.MONTH) + 1;
		int yearPayment = paymentDateCal.get(Calendar.YEAR);
		
		return year < yearPayment || (yearPayment == year && month < monthPayment);
	}
	
	/**
	 * Builds the issuer validators.
	 * @param creditCardType 
	 *
	 * @return the validators list
	 */
	private List<CodeValidator> buildIssuerValidators(CreditCardType type) {
		
		List<CodeValidator> validators = new ArrayList<>(CreditCardType.values().length);
		
		// build the card type validator if has a regexp defined
		if (type.getRegExp() != null) {
			
			CodeValidator codeValidator = null;
			
			if (type.applyLuhnCheck()) {
				codeValidator = new CodeValidator(type.getRegExp(), LuhnCheckDigit.LUHN_CHECK_DIGIT);
			}
			else {
				codeValidator = new CodeValidator(type.getRegExp(), NoCheckDigit.NO_CHECK_DIGIT);
			}
			
			validators.add(codeValidator);
		}
		
		return validators;
	}
	
}
