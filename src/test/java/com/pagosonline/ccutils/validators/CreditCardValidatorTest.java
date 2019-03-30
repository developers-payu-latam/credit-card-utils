package com.pagosonline.ccutils.validators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pagosonline.ccutils.model.CreditCardType;

/**
 * The numbers used in this test are test numbers from paypal:
 * https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm
 *
 * @author <a href="jorge.ulloa@pagosonline.com">Jorge M. Ulloa</a>
 *
 */
public class CreditCardValidatorTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardValidatorTest.class);

	private Random random = new Random(System.currentTimeMillis());
	
	/**
	 * Credit Card length: 16 digits
	 */
	private static final int CREDIT_CARD_LENGTH_16 = 16;

	private static String CREDIT_CARD_NUMBER_16 = "1111111111111111";
	private static String CREDIT_CARD_NUMBER_19 = "1111111111111111111";
	private static String FILE_BIN_VISA_NAME = "src/test/resources/VISA-BIN.txt";
	private static String FILE_BIN_MASTERCARD_NAME = "src/test/resources/Mastercard-BIN.txt";
	private static String FILE_BIN_AMEX_NAME = "src/test/resources/Amex-BIN.txt";
	private static String FILE_BIN_TNARANJA_NAME = "src/test/resources/TNaranja-BIN.txt";
	private static String FILE_BIN_CABAL_NAME = "src/test/resources/CABAL-BIN.txt";
	private static String FILE_BIN_SHOPPING_NAME = "src/test/resources/Shopping-BIN.txt";
	private static String FILE_BIN_CENCOSUD_NAME = "src/test/resources/Cencosud-BIN.txt";
	private static String FILE_BIN_ARGENCARD_NAME = "src/test/resources/Argencard-BIN.txt";
	private static String FILE_BIN_DINERS_NAME = "src/test/resources/Diners-BIN.txt";
	/** File of the condensa bins file**/
	private static final String FILE_BIN_CODENSA_NAME = "src/test/resources/Codensa-BIN.txt";
	
	/** Debit card visa for unit test*/
	private static final String DEBIT_CARD_VISA = "4239499992300060";
	/** Debit card master for unit test*/
	private static final String DEBIT_CARD_MASTER_16 = "5303715971862863";
	/** Debit card master for unit test*/
	private static final String DEBIT_CARD_MASTER_19 = "5596910444692700918";
	/** Debit card master for unit test*/
	private static final String CREDIT_CARD_MASTER_19 = "5596910444692700918";

	/**
	 * Maestro's cards
	 *
	 * @return the string[][]
	 */
	@DataProvider(name="creditCardMaestro")
	private String[][] creditCardMaestro(){
		
		return new String[][]{
			{generateValidCreditCardNumber("501020", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501021", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501023", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501062", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501038", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501057", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("588729", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501041", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501080", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501081", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501056", 16),"MASTERCARD"},
			{generateValidCreditCardNumber("501075", 16),"MASTERCARD"},
			
			//Card with 19 digits
			{"5010414386050340428","MASTERCARD"},
			{"5887294317380222057","MASTERCARD"},
			{"5010217559965057985","MASTERCARD"},
			{"5010231010906734490","MASTERCARD"},
			{"5010623743509183824","MASTERCARD"},
			{"5010388447340345312","MASTERCARD"},
			{"5010574022465813206","MASTERCARD"},
			{"5887296042710995713","MASTERCARD"},
			{"5010416745127899948","MASTERCARD"},
			{"5010807985113946280","MASTERCARD"},
			{"5010810650058359999","MASTERCARD"},
			{"5010566368945062827","MASTERCARD"},
			{"5010750048419943365","MASTERCARD"}, 
			
			//New credit cards
			{"501041037811620046","MASTERCARD"},
			{"501041007810620081","MASTERCARD"},
			{"501041077815620030","MASTERCARD"},
			{"501041087814620048","MASTERCARD"}
		};
		
	}
	

	@BeforeTest
	public void init(){
		//Set the locale for test purposes
//		Locale locale= Locale.US;
		Locale locale= new Locale("es", "CO");
//		Locale locale= new Locale("pt", "BR");
		LocaleContextHolder.setLocale(locale);
	}


	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validate00() {

		String number = " ";
		CreditCardType creditCardType = CreditCardType.VISA;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validate01() {

		String number = null;
		CreditCardType creditCardType = CreditCardType.VISA;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validate02() {

		String number = "12312312321312312312";
		CreditCardType creditCardType = null;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Unknown Credit Card Validator Test
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validate03() {
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.UNKNOWN;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test
	public void validateVisa10() {

		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.VISA;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}

		number = "4012888888881881";
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}

		number = "4222222222222";
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateVisa11() {

		String number = "41111111111111112";
		CreditCardType creditCardType = CreditCardType.VISA;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Test to validate VISA with BIN 606374 which belong to Cencosud and must
	 * be processed as VISA.
	 *
	 * @author <a href="oscar.garavito@pagosonline.com">Óscar Garavito</a>
	 * @since 4.5.9
	 */
	@Test
	public void validateVisaCencosud10() {
		String number = "6063742590235634";
		CreditCardType creditCardType = CreditCardType.VISA;
		try {
			CreditCardValidator.validate(number, creditCardType);
		} catch (IllegalArgumentException ex) {
			LOGGER.error(ex.getMessage());
			throw ex;
		}
	}

	@Test
	public void validateAmex10() {

		String number = "378282246310005";
		CreditCardType creditCardType = CreditCardType.AMEX;

		CreditCardValidator.validate(number, creditCardType);

		number = "371449635398431";
		CreditCardValidator.validate(number, creditCardType);

		number = "378734493671000";
		CreditCardValidator.validate(number, creditCardType);
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateAmex11() {

		String number = "3787344936710001";
		CreditCardType creditCardType = CreditCardType.AMEX;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test
	public void validateDiners10() {

		String number = "30569309025904";
		CreditCardType creditCardType = CreditCardType.DINERS;

		CreditCardValidator.validate(number, creditCardType);

		number = "38520000023237";
		CreditCardValidator.validate(number, creditCardType);
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateDiners11() {

		String number = "48520000023237";
		CreditCardType creditCardType = CreditCardType.DINERS;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateDiners12() {

		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.DINERS;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test
	public void validateDiscover10() {

		String number = "6011111111111117";
		CreditCardType creditCardType = CreditCardType.DISCOVER;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}

		number = "6011000990139424";
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateDiscover11() {

		String number = "6012111111111118";
		CreditCardType creditCardType = CreditCardType.DISCOVER;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateDiscover12() {

		String number = "378282246310005";
		CreditCardType creditCardType = CreditCardType.DISCOVER;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	@Test
	public void validateMasterCard10() {

		String number = "5555555555554444";
		CreditCardType creditCardType = CreditCardType.MASTERCARD;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}

		number = "5105105105105100";
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
			throw ex;
		}
	}
	
	
	/**
	 * Find card type for MasterCard INN 222100 should success.
	 * 
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 * @since 4.9.5
	 */
	@Test
	public void findCardTypeForMasterCard_2Inn_shouldSuccess() {

		String number = "2221000010000015";

		CreditCardType type = CreditCardValidator.findCreditCardType(number);
		
		Assert.assertEquals(type, CreditCardType.MASTERCARD);
		
	}
	
	/**
	 * Validate MasterCard INN 222100 should success.
	 * 
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 * @since 4.9.5
	 */
	@Test
	public void validateMasterCard_2Inn_shouldSuccess() {

		String number = "2221000010000015";

		CreditCardValidator.validate(number, CreditCardType.MASTERCARD);
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void validateMasterCard11() {

		String number = "5105105105105101";
		CreditCardType creditCardType = CreditCardType.MASTERCARD;

		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Validate Credencial card bin 541203 should success.
	 *
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 * @since 4.9.5
	 */
	@Test
	public void validateCredencialCardBin541203_shouldSuccess() {
		
		String number = "5412031111111114";
		CreditCardValidator.validate(number, CreditCardType.CREDENCIAL);
	}

	/**
	 * Validate Credencial card bin 540625 should success.
	 */
	@Test
	public void validateCredencialCardBin540625_shouldSuccess() {
		
		String number = "5406250000000000";
		CreditCardValidator.validate(number, CreditCardType.CREDENCIAL);
	}

	@Test
	public void validateElo1() {
		//TODO
		String number = "5412031111111114";
		CreditCardType creditCardType = CreditCardType.ELO;

		CreditCardValidator.validate(number, creditCardType);
	}


	/**
	 * Validate a Tarjeta Naranja card number
	 * 
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 * @since 4.9.5
	 */
	@Test
	public void validateTarjetaNaranja_shouldSuccess(){
		
		String number = "5895625600534000";
		CreditCardValidator.validate(number, CreditCardType.NARANJA);
		
	}

	/**
	 * Tarjeta Naranja Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateTarjetaNaranja_error(){
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.NARANJA;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}


	/**
	 * Tarjeta Shopping Validator Test
	 */
	@Test
	public void validateTarjetaShopping1(){
		String number = "6034880000000051";
		CreditCardType creditCardType = CreditCardType.SHOPPING;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Tarjeta Shopping Validator Test
	 */
	@Test
	public void validateTarjetaShopping2(){
		String number = "2799518777711";
		CreditCardType creditCardType = CreditCardType.SHOPPING;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Tarjeta Shopping Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateTarjetaShopping_error(){
		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.SHOPPING;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Validate a Argencard card number
	 * 
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 * @since 4.9.5
	 */
	@Test
	public void validateArgencard_shouldSuccess(){
		
		String number = "5323629993121008";
		
		CreditCardValidator.validate(number, CreditCardType.ARGENCARD);
	}

	/**
	 * Tarjeta Naranja Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateArgencard_error(){
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.ARGENCARD;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Cabal Validator Test
	 */
	@Test
	public void validateCabal(){
		String number = "5896570047186133";
		CreditCardType creditCardType = CreditCardType.CABAL;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Cabal Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateCabal_error(){
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.CABAL;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Presto Validator Test
	 */
	@Test
	public void validatePresto(){
		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.PRESTO;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Cabal Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validatePresto_error(){
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.PRESTO;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Validate JCB Credit Card Type with valid number.
	 */
	@Test
	public void validateJcbWithValidNumber(){
		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.JCB;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Validate JCB Credit Card Type with invalid number.
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateJcbWithInvalidNumber(){
		String number = "41111111111";
		CreditCardType creditCardType = CreditCardType.JCB;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Validate AURA Credit Card Type with valid number.
	 */
	@Test
	public void validateAuraWithValidNumber(){
		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.AURA;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Validate AURA Credit Card Type with invalid number.
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateAuraWithInvalidNumber(){
		String number = "41111111111";
		CreditCardType creditCardType = CreditCardType.AURA;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Presto Validator Test
	 */
	@Test
	public void validateRipley(){
		String number = "4111111111111111";
		CreditCardType creditCardType = CreditCardType.RIPLEY;
		CreditCardValidator.validate(number, creditCardType);
	}

	/**
	 * Cabal Validator Test
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateRipley_error(){
		String number = "4222222222222";
		CreditCardType creditCardType = CreditCardType.RIPLEY;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * No CreditCardType is defined
	 */
	@Test (expectedExceptions = IllegalArgumentException.class)
	public void validateCreditCardTypeError(){
		String number = "";
		CreditCardType creditCardType=null;
		try{
			CreditCardValidator.validate(number, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}

	/**
	 *
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void constructorAccesibilityTest() throws InstantiationException, IllegalArgumentException,
		IllegalAccessException, InvocationTargetException{

		Class<CreditCardValidator> claz = CreditCardValidator.class;
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


	@Test
	public void willExpireOnPaymentDateTest(){
		try {
			CreditCardValidator.willExpireOnPaymentDate("200", Calendar.getInstance().getTime());
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}

		try {
			CreditCardValidator.willExpireOnPaymentDate("1000/00", Calendar.getInstance().getTime());
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}

		try {
			CreditCardValidator.willExpireOnPaymentDate("2000/00", Calendar.getInstance().getTime());
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		Calendar payment  = Calendar.getInstance();

		payment.set(Calendar.YEAR, 2020);
		payment.set(Calendar.MONTH, Calendar.OCTOBER);
		Assert.assertFalse(CreditCardValidator.willExpireOnPaymentDate(
				"2020/10", payment.getTime()));


		payment.set(Calendar.YEAR, 2013);
		payment.set(Calendar.MONTH, Calendar.OCTOBER);
		Assert.assertTrue(CreditCardValidator.willExpireOnPaymentDate(
				"2013/09", payment.getTime()));

		payment.set(Calendar.YEAR, 2020);
		payment.set(Calendar.MONTH, Calendar.NOVEMBER);
		Assert.assertTrue(CreditCardValidator.willExpireOnPaymentDate(
				"2020/10", payment.getTime()));

		payment.set(Calendar.YEAR, 2020);
		payment.set(Calendar.MONTH, Calendar.OCTOBER);
		Assert.assertTrue(CreditCardValidator.willExpireOnPaymentDate(
				"2010/10", payment.getTime()));

		payment.set(Calendar.YEAR, 2020);
		payment.set(Calendar.MONTH, Calendar.MAY);
		Assert.assertFalse(CreditCardValidator.willExpireOnPaymentDate(
				"2021/10", payment.getTime()));

		payment.set(Calendar.YEAR, 2013);
		payment.set(Calendar.MONTH, Calendar.OCTOBER);
		Assert.assertFalse(CreditCardValidator.willExpireOnPaymentDate(
				"2014/10", payment.getTime()));

		payment.set(Calendar.YEAR, 2014);
		payment.set(Calendar.MONTH, Calendar.SEPTEMBER);
		Assert.assertFalse(CreditCardValidator.willExpireOnPaymentDate(
				"2014/10", payment.getTime()));

	}

	@Test
	public void findCreditCardTypeTest1() {

		// VISA card numbers
		String[] cardNumbers = new String[] {
				"4189001553405963",
				"4532095214799685",
				"4024007164517020",
				"4929152014152150",
				"4485055754347144",
				"4916881282746",
				"4024007179134",
				"4485901655554",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.VISA);
		}
	}

	@Test
	public void findCreditCardTypeTest2() {

		// MASTERCARD card numbers
		String[] cardNumbers = new String[] {
				"5273243423244958",
				"5201630501811665",
				"5358222272364529",
				"5108971913277739",
				"5495246625887526",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.MASTERCARD);
		}
	}

	@Test
	public void findCreditCardTypeTest3() {

		// AMEX card numbers
		String[] cardNumbers = new String[] {
				"348471153647155",
				"375864231531696",
				"375224705858598",
				"370395520225182",
				"375570444675119",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.AMEX);
		}
	}

	@Test
	public void findCreditCardTypeTest4() {

		// DINERS card numbers
		String[] cardNumbers = new String[] {
				"30309073467523",
				"38506235432764",
				"30258066302157",
				"38155349582088",
				"36364001841327",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.DINERS);
		}
	}

	@Test
	public void findCreditCardTypeTest5() {

		// DISCOVER card numbers
		String[] cardNumbers = new String[] {
				"6011164314767615",
				"6011646840573657",
				"6011081330856398",
				"6011588279557803",
				"6011410051324627",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.DISCOVER);
		}
	}

	@Test
	public void findCreditCardTypeTest6() {

		// SHOPPING card numbers
		String[] cardNumbers = new String[] {
				"6034881111111111",
				"6034882222222222",
				"6034883333333333",
				"2799111111111",
				"2799222222222",
				"2799999999999",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.SHOPPING);
		}
	}

	@Test
	public void findCreditCardTypeTest7() {

		// UNKOWN card numbers
		String[] cardNumbers = new String[] {
				"3528612576559479",
				"180013051123045",
				"214996015048177",
				"869934728432618",
				};

		for (String number : cardNumbers) {
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(number);
			Assert.assertEquals(creditCardType, CreditCardType.UNKNOWN);
		}
	}

	/*
	 * Los IIN pueden ser: 384100, 384140, 384160 y 606282
		Las tarjetas de Hipercard tienen 16 o 19 dígitos de longitud.
	 */
	@Test
	public void validateHipercardWorksWithValidIINsAndLength16() {
		String[] numbers = {"3841000000000000","3841400000000000","3841600000000000","6062820000000000"};
		for(String n:numbers) {
			CreditCardValidator.validate(n , CreditCardType.HIPERCARD);
		}
	}

	@Test
	public void validateHipercardWorksWithValidIINsAndLength19() {
		String[] numbers = {"3841000000000000000","3841400000000000000","3841600000000000000","6062820000000000000"};
		for(String n:numbers) {
			CreditCardValidator.validate(n , CreditCardType.HIPERCARD);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateHipercardWithLength17ThrowsException() {

		CreditCardValidator.validate("38410000000000000", CreditCardType.HIPERCARD);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateHipercardWith1DigitsThrowsException() {
		try{
			CreditCardValidator.validate("1", CreditCardType.HIPERCARD);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateHipercardWithNoDigitsThrowsException() {
		try{
			CreditCardValidator.validate("", CreditCardType.HIPERCARD);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateHipercardWith16CharsThrowsException() {
		String invalid = CREDIT_CARD_NUMBER_16.replaceAll(".", "a");
		try{
			CreditCardValidator.validate(invalid, CreditCardType.HIPERCARD);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateHipercardWith19CharsThrowsException() {
		String invalid = CREDIT_CARD_NUMBER_19.replaceAll(".", "a");
		try{
			CreditCardValidator.validate(invalid, CreditCardType.HIPERCARD);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Validate CENCOSUD card with valid number should success.
	 * 
	 * @author <a href="juan.roman@payulatam.com">Juan C. Roman</a>
	 */
	@Test
	public void validateCencosudCardWithValidNumber_shouldSuccess() {
		
		String card = "6034937272862830";
		
		CreditCardValidator.validate(card, CreditCardType.CENCOSUD);
	}


	@Test
	public void validateBINCencosud() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_CENCOSUD_NAME);
		String validCreditCardNumber = null;
		int length = 16;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.CENCOSUD);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}


	@Test
	public void validateBIN_TNaranja() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_TNARANJA_NAME);
		String validCreditCardNumber = null;
		int length = 16;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.NARANJA);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBIN_CABAL() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_CABAL_NAME);
		String validCreditCardNumber = null;
		int length = 16;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.CABAL);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBINMastercard() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_MASTERCARD_NAME);
		String validCreditCardNumber = null;
		int length = 16;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.MASTERCARD);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBINArgencard() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_ARGENCARD_NAME);
		String validCreditCardNumber = null;
		int length = 16;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.ARGENCARD);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBINShopping() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_SHOPPING_NAME);
		String validCreditCardNumber = null;
		int length = 13;

		for (String bin : creditCardNumberList) {
			if(bin.length()==6){
				length = 16;
			}
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.SHOPPING);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBINAmex() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_AMEX_NAME);
		String validCreditCardNumber = null;
		int length = 15;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			/*if(!creditCardType.equals(CreditCardType.AMEX)){
				LOGGER.log(Level.SEVERE, "");
			}*/
			Assert.assertEquals(creditCardType, CreditCardType.AMEX);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateConcreteBINAmex() {

		String validCreditCardNumber = "371111111111111";
		CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
		Assert.assertEquals(creditCardType, CreditCardType.AMEX);

		try{
			CreditCardValidator.validate(validCreditCardNumber, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.debug(ex.getMessage());
		}
	}

	@Test
	public void validateBINVisa() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_VISA_NAME);
		String validCreditCardNumber = null;
		int length = 13;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.VISA);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.debug(ex.getMessage());
			}
		}
	}

	@Test
	public void validateBINDiners() {

		List<String> creditCardNumberList = readBINFile(FILE_BIN_DINERS_NAME);
		String validCreditCardNumber = null;
		int length = 14;

		for (String bin : creditCardNumberList) {
			validCreditCardNumber = generateValidCreditCardNumber(bin, length);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
			Assert.assertEquals(creditCardType, CreditCardType.DINERS);

			try{
				CreditCardValidator.validate(validCreditCardNumber, creditCardType);
			}catch(IllegalArgumentException ex){
				LOGGER.error(ex.getMessage());
			}
		}
	}

	@Test
	public void validateConcreteBINDiners() {

		String validCreditCardNumber = "36153824757970";

		CreditCardType creditCardType = CreditCardValidator.findCreditCardType(validCreditCardNumber);
		Assert.assertEquals(creditCardType, CreditCardType.DINERS);

		try{
			CreditCardValidator.validate(validCreditCardNumber, creditCardType);
		}catch(IllegalArgumentException ex){
			LOGGER.error(ex.getMessage());
		}
	}
	
	/**
	 * Test to check if the Codensa validator is working correctly
	 * 
	 * @author <a href=camilo.white@payulatam.com>Camilo A. White</a>
	 */
	@Test
	public void testCodensaValidator() {
		List<String> condensaBins = readBINFile(FILE_BIN_CODENSA_NAME);
		String creditCardNumber = null;
		for (String bin : condensaBins) {
			creditCardNumber = generateValidCreditCardNumber(bin, CREDIT_CARD_LENGTH_16);
			CreditCardType creditCardType = CreditCardValidator.findCreditCardType(creditCardNumber);
			
			CreditCardValidator.validate(creditCardNumber, creditCardType);
			Assert.assertEquals(creditCardType, CreditCardType.CODENSA);
		}
	}
	
	/**
	 * Validate a debit card visa
	 * @author <a href="david.espitia@payulatam.com">Camilo Espitia</a>
	 */
	@Test(description = "Validate a debit card visa")
	public void validateDebitCardVisa(){

		try{
			CreditCardValidator.validate(DEBIT_CARD_VISA, CreditCardType.VISA_DEBIT);
		}catch(IllegalArgumentException exception){
			Assert.fail("Error in debit card visa validation", exception);
		}
		
	} 
	
	/**
	 * Validate a debit card Mastercard
	 * @author <a href="david.espitia@payulatam.com">Camilo Espitia</a>
	 */
	@Test(description = "Validate a debit card MasterCard")
	public void validateDebitCardMasterCard(){

		try{
			CreditCardValidator.validate(DEBIT_CARD_MASTER_16, CreditCardType.MASTERCARD_DEBIT);
		}catch(IllegalArgumentException exception){
			Assert.fail("Error in debit card mastercard validation", exception);
		}
		
	}

	/**
	 * Validate credit card master card with 19 digits.
	 *
	 * @author <a href="mario.murillo@payulatam.com">Mario Andres Murillo</a>
	 * @date 19/04/2017
	 */
	@Test(description = "Validate a credit card MasterCard with 19 digits")
	public void validateCreditCardMasterCardWith19Digits(){

		try{
			CreditCardValidator.validate(CREDIT_CARD_MASTER_19, CreditCardType.MASTERCARD);
		}catch(IllegalArgumentException exception){
			Assert.fail("Error in credit card mastercard validation", exception);
		}
		
	}

	/**
	 * Validate debit card master card with 19 digits.
	 *
	 * @author <a href="mario.murillo@payulatam.com">Mario Andres Murillo</a>
	 * @date 19/04/2017
	 */
	@Test(description = "Validate a debit card MasterCard with 19 digits")
	public void validateDebitCardMasterCardWith19Digits(){

		try{
			CreditCardValidator.validate(DEBIT_CARD_MASTER_19, CreditCardType.MASTERCARD_DEBIT);
		}catch(IllegalArgumentException exception){
			Assert.fail("Error in debit card mastercard validation", exception);
		}
		
	}
	
	
	/**
	 * Validate credit card maestro bins.
	 *
	 * @param creditCard the credit card
	 * @param creditCardType the credit card type
	 */
	@Test(description="Validates Maestro's cards (Mastercard) with 16 and 19 digits", dataProvider= "creditCardMaestro")
	public void validateCreditCardMaestroBins(final String creditCard, final String creditCardType){
		
		try{
			CreditCardValidator.validate(creditCard, CreditCardType.valueOf(creditCardType));
		}catch(IllegalArgumentException exception){
			Assert.fail("Error in debit card mastercard - maestro validation", exception);
		}
	
	}
	

	/**
	 * This method is responsable for read the file that contain BIN's to validate
	 * @param String fileName
	 * @return List<String>
	 * @author angela.aguirre@payulatam.com
	 */
	private List<String> readBINFile(String fileName) {
		List<String> creditCardNumberList = new ArrayList<>();
		FileReader archivo = null;
		String linea = "";
		BufferedReader lector = null;
		try {

			archivo = new FileReader(fileName);
			lector = new BufferedReader(archivo);

			while ((linea = lector.readLine()) != null) {
				creditCardNumberList.add(linea);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Archivo no encontrado");
		} catch (IOException e) {
			throw new RuntimeException("Ocurrio un error de entrada/salida");
		} finally {
			if (archivo != null) {
				try {
					archivo.close();
					if(lector!=null){
						lector.close();
					}
				} catch (IOException ex) {
					LOGGER.error(ex.getMessage());
				}
			}
		}
		return creditCardNumberList;
	}

	/**
	 * This method is responsable to generate a valid credit card number
	 * @param String bin, int length
	 * @return String
	 * @author angela.aguirre@payulatam.com
	 */
	private String generateValidCreditCardNumber(String bin, int length ){
		int randomNumberLength = length - (bin.length() + 1);

	    StringBuffer buffer = new StringBuffer(bin);
	    for (int i = 0; i < randomNumberLength; i++) {
	      int digit = this.random.nextInt(10);
	      buffer.append(digit);
	    }

	    // Do the Luhn algorithm to generate the check digit.
	    int checkDigit = this.getCheckDigit(buffer.toString());
	    buffer.append(checkDigit);

	    return buffer.toString();
	}

	/**
	 * This method is responsable to generate a valid credit card check digit
	 * @param String number
	 * @return int
	 * @author angela.aguirre@payulatam.com
	 */
	private int getCheckDigit(String number) {

		int sum = 0;
	    int remainder = (number.length() + 1) % 2;
	    for (int i = 0; i < number.length(); i++) {

	      // Get the digit at the current position.
	      int digit = Integer.parseInt(number.substring(i, (i + 1)));

	      if ((i % 2) == remainder) {
	        digit = digit * 2;
	        if (digit > 9) {
	          digit = (digit / 10) + (digit % 10);
	        }
	      }
	      sum += digit;
	    }

	    // The check digit is the number required to make the sum a multiple of
	    // 10.
	    int mod = sum % 10;
	    int checkDigit = ((mod == 0) ? 0 : 10 - mod);

	    return checkDigit;
	}

}
