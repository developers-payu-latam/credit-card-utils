package com.pagosonline.ccutils.model;


/**
 * This enum represents a credit card county.
 * @author <a href="guillermo.garcia@pagosonline.com">Gullermo Garcia</a>
 */
public enum CreditCardCountry {

	/**Argentina Country */
	AR("ARGENTINA"),
	/** Peru Country. */
	PE("PERÚ");
	
	/** The Country Name */
	private String description;
	
	/**
	 * Return a {@link CreditCardCountry} from a given string.
	 * 
	 * @param description.
	 */
	private CreditCardCountry (final String description) {
		this.description = description;	
	}
	
	/**
	 * Return the country description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


}
