package com.pagosonline.ccutils.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Format utils for credit cards tests.
 * 
 * @author <a href="jorge.ulloa@pagosonline.com">Jorge M. Ulloa</a>
 *
 */
public class CreditCardFormatUtilsTest {

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void mask00() {
		
		String number = "";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void mask01() {
		
		String number = "     ";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void mask02() {
		
		String number = null;
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test
	public void mask10() {
		
		String number = "1234";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test
	public void mask11() {
		
		String number = "34";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test
	public void mask12() {
		
		String number = "4";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals(number, masked);
	}
	
	@Test
	public void mask2() {
		
		String number = "01234";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals("01234", masked);
	}
	
	@Test
	public void mask3() {
		
		String number = "4123412341235321";
		String masked = CreditCardFormatUtils.mask(number);
		
		Assert.assertEquals("412341******5321", masked);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Test
	public void constructorAccesibilityTest() throws InstantiationException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{

		Class<CreditCardFormatUtils> claz = CreditCardFormatUtils.class;
		Constructor[] ctors = claz.getDeclaredConstructors();
		Assert.assertEquals(1, ctors.length, claz+" Utility class should only have one constructor");
		Constructor ctor = ctors[0];
		Assert.assertFalse(ctor.isAccessible(), claz+" Utility class constructor should be inaccessible");
		Assert.assertTrue(ctor.getModifiers()==Modifier.PRIVATE, claz+" Utility class constructor should be inaccessible");

		try{
			claz.newInstance();
			Assert.fail(claz+" Utility class constructor should be inaccessible");
		}catch(IllegalAccessException e){ }

		ctor.setAccessible(true);	    
		ctor.newInstance((Object[])null);
	}
}
