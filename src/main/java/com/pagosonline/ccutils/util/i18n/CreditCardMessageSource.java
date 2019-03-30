package com.pagosonline.ccutils.util.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Component to build i18n messages.
 * 
 * @author <a href="fernando.moreno@payulatam.com">Fernando Moreno</a>
 * @date 12/09/2014
 */
@Component
public class CreditCardMessageSource extends ReloadableResourceBundleMessageSource {

	/**
	 * File names.
	 */
	private static final String[] BASENAMES = new String[] { "classpath:CreditCardMessages"};
	/** Cache time */
	private static final int CACHE_SECONDS = 60 * 60;
	/** Resources encoding */
	private static final String ENCODING = "UTF-8";

	/**
	 * Default constructor
	 */
	public CreditCardMessageSource() {
		setBasenames(BASENAMES);
		setCacheSeconds(CACHE_SECONDS);
		setDefaultEncoding(ENCODING);
	}

}