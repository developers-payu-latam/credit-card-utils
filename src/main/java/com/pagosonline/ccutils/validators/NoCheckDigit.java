package com.pagosonline.ccutils.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;

/**
 * The No check digit implementation for card validation.
 * 
 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
 * @since 4.9.5
 */
public final class NoCheckDigit implements CheckDigit {
	
	/** Singleton Check Digit instance */
    public static final CheckDigit NO_CHECK_DIGIT = new NoCheckDigit();
    
    /**
	 * Instantiates a new no check digit.
	 */
	public NoCheckDigit() {
    	
    }

	/* (non-Javadoc)
	 * @see org.apache.commons.validator.routines.checkdigit.CheckDigit#calculate(java.lang.String)
	 */
	@Override
	public String calculate(String code) throws CheckDigitException {
		
		return StringUtils.EMPTY;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.validator.routines.checkdigit.CheckDigit#isValid(java.lang.String)
	 */
	@Override
	public boolean isValid(String code) {
		
		return true;
	}
	
}