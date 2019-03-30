package com.pagosonline.ccutils.util.i18n;

/**
 * The list of messages used for i18n.
 * 
 * @author <a href="fernando.moreno@payulatam.com">Fernando Moreno</a>
 * @date 12/09/2014
 */
public interface CreditCardMessages {

	String CREDIT_CARD_NUMBER_NOT_NULL_EMPTY = "credit_card_number_not_null_empty";
	String CREDIT_CARD_TYPE_NOT_NULL = "credit_card_type_not_null";
	String CREDIT_CARD_NUMBER_NOT_VALID = "credit_card_number_not_valid";
	String PAYMENT_METHOD_NOT_SUPPORTED = "payment_method_not_supported";

	/**
	 * Message for a credit cart with invalid security code
	 */
	String CREDIT_CARD_INVALID_SECURITY_CODE = "invalid_security_code";
	
	/**
	 * Message for a credit cart with invalid security code
	 */
	String CREDIT_CARD_AMEX_INVALID_LENGTH_SECURITY_CODE = "credit_card_amex_security_code_invalid_length";
	
	/**
	 * Message for a credit cart Perú with invalid security code 
	 */
	String CREDIT_CARD_AMEX_PE_INVALID_LENGTH_SECURITY_CODE = "credit_card_amex_pe_security_code_invalid_length";
	
	/**
	 * Message for a credit cart with invalid security code
	 */
    String CREDIT_CARD_INVALID_LENGTH_SECURITY_CODE="credit_card_invalid_length_security_code";
	
	
}
