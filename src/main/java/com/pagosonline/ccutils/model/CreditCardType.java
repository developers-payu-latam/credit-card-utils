package com.pagosonline.ccutils.model;

/**
 * This enum represents a credit card type.
 * @author <a href="jorge.ulloa@pagosonline.com">Jorge M. Ulloa</a>
 * 
 * Added regular expression by issuing network.
 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
 * @since 4.9.5
 * 
 */
public enum CreditCardType {
	
	/** Visa type. */
	VISA("^(4)(\\d{12}|\\d{15})$|^(606374\\d{10}$)"),
	
	/** Diners type. */
	DINERS("(^[35](?:0[0-5]|[68][0-9])[0-9]{11}$)|(^30[0-5]{11}$)|(^3095(\\d{10})$)|(^36{12}$)|(^3[89](\\d{12})$)"),
	
	/** Discover type. */
	DISCOVER("^(6011\\d{12})$|^(64[4-9]\\d{13})$|^(65\\d{14})$"),
	
	/** Amex type. */
	AMEX("^(3[47]\\d{13})$"),
	
	/** MasterCard type. Maestro's cards start with (5010 or 5887)*/
	MASTERCARD("^((?:5010|5887)(?:\\d{12,15})$)|^(5[1-5](?:\\d{14}|\\d{17})$)|^(2(?:2(?:2[1-9]|[3-9]\\d)|[3-6]\\d\\d|7(?:[01]\\d|20))(?:\\d{12}|\\d{15})$)"),
	
	/** Naranja Card Type*/
	NARANJA("^(589562)\\d{10}$", false),
	
	/** Shopping Card Type */
	SHOPPING("(^603488(\\d{10})$)|(^2799(\\d{9})$)", false),
	
	/** Cabal Card Type */
	CABAL("(^604(([23][0-9][0-9])|(400))(\\d{10})$)|(^589657(\\d{10})$)", false),
	
	/** Argencard Card Type */
	ARGENCARD("^(501105|532362)(\\d{10}$)"),
	
	/** Cencosud Card Type */
	CENCOSUD("^603493(\\d{10})$", false),
	
	/** Hipercard Card Type */
	HIPERCARD("^(3841[046]0|606282|637(5(68|99)|095|6(09|12)))(\\d{7}|\\d{10}|\\d{13})$", false),
	
	/** Codensa Card Type*/
	CODENSA("^590712(\\d{10})$", false),
	
	/** Credencial type. */
	CREDENCIAL("^(541203|540625|549151)(\\d{10})$", false),
	
	/** Elo card. */
	ELO("\\d{16}$", false),
	
	/** Presto Card Type*/
	PRESTO("\\d{16}$", false),
	
	/** Ripley Card Type */
	RIPLEY("\\d{16}$", false),
	
	/**  Visa debit card type. */
	VISA_DEBIT("\\d{16}$", false),
	
	/** MasterCard debit card type. */
	MASTERCARD_DEBIT("\\d{16}|\\d{19}$", false),
	
	/** Unknown Card Type */
	UNKNOWN(null, false),

	/**  Falabella card type. */
	CMR("^(627180|528209|513689)(\\d{10})$", false),
	
	/** jcb Card Type. */
	JCB("\\d{16}$", false),

	/** Aura Card Type. */
	AURA("\\d{16}$", false);
	
	
	
	/** The issuer card regular expression. */
	private String regExp;
	
	/** The luhn check. */
	private Boolean luhnCheck;
	
	/**
	 * Instantiates a new credit card type.
	 *
	 * @param regExp the regular expression
	 */
	private CreditCardType(String regExp) {
		this.regExp = regExp;
		this.luhnCheck = true;
	}
	
	/**
	 * Instantiates a new credit card type.
	 *
	 * @param regExp the regular expression
	 * @param luhnCheck true if should be validated using the luhn check digit 
	 */
	private CreditCardType(String regExp, Boolean luhnCheck) {
		this.regExp = regExp;
		this.luhnCheck = luhnCheck;
	}

	/**
	 * Gets the regular expression
	 *
	 * @return the regular expression
	 */
	public String getRegExp() {
		return regExp;
	}
	

	/**
	 * Apply luhn check.
	 *
	 * @return the boolean
	 */
	public Boolean applyLuhnCheck() {
		return luhnCheck;
	}

	/**
	 * Return a {@link CreditCardType} from a given string.
	 * 
	 * @param cardType the string card type.
	 * @return the {@link CreditCardType} if the type exist. null otherwise.
	 */
	public static CreditCardType fromString(String cardType) {
		
		if (cardType != null) {

			for (CreditCardType type : CreditCardType.values()) {
				if (type.name().equalsIgnoreCase(cardType)) {
					return type;
				}
			}
		}
		return null;
	}

}
