package com.pagosonline.ccutils.format;

import org.apache.commons.lang3.StringUtils;


/**
 * Format utils for credit cards.
 * @author <a href="jorge.ulloa@pagosonline.com">Jorge M. Ulloa</a>
 */
public final class CreditCardFormatUtils {

	/**
	 * Default constructor.
	 */
	private CreditCardFormatUtils(){}
	
	private static final int FIRST_VISIBLE_DIGITS = 6;
	private static final int LAST_VISIBLE_DIGITS = 4;
	
	/**
	 * Masks a credit card number.
	 * If the number length is less or equals than (FIRST_VISIBLE_DIGITS + LAST_VISIBLE_DIGITS), 
	 * it returns the same number, if the number length is greater than 
	 * (FIRST_VISIBLE_DIGITS + LAST_VISIBLE_DIGITS), it masks with '*' character, all digits between
	 * the FIRST_VISIBLE_DIGITS and the LAST_VISIBLE_DIGITS.
	 * 
	 * For example:
	 * Input: 1234		Output: 1234
	 * Input: 4111111111111111111		Output: 411111*********1111
	 * 
	 * @param number the number to mask.
	 * @return the masked number.
	 */
	public static String mask(String number) {
	
		if (StringUtils.isBlank(number)) {
			throw new IllegalArgumentException("the number cannot be null or empty");
		}
		
		int numbersToMask = number.length() - (FIRST_VISIBLE_DIGITS + LAST_VISIBLE_DIGITS);
		
		if (numbersToMask <= 0) {
			return number;
		}
		
		StringBuilder maskedNumber = new StringBuilder(number.substring(0, FIRST_VISIBLE_DIGITS));
		
		for (int i = 0; i < numbersToMask; i++) {
			maskedNumber.append('*');
		}
		maskedNumber.append(number.substring(number.length() - LAST_VISIBLE_DIGITS));
		
		return maskedNumber.toString();
	}
}
