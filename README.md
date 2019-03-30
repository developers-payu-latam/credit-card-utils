# credit-cards-utils 
Utilities to manage credit card information such as numbers validation, masking, etc.
 
## Installation

###Maven
```xml 
<dependency>
    <groupId>pol-utils</groupId>
    <artifactId>credit-cards-utils</artifactId>
    <version>0.0.1</version>
</dependency>
```

###Gradle
```   
compile(group: 'com.payulatam', name: 'creditcard-gatekeeper', version: '1.0.0')
```

## Usage

Write usage instructions

1. Mask a credit card number:

```java 
	String number = "4123412341235321";
	String masked = CreditCardFormatUtils.mask(number);
	Assert.assertEquals("412341******5321", masked);
``` 

2. Validate a security code

```java 
	# 4 digits  cvv2 for Amex Argentina is ok
	CreditCardValidator.validateSecurityCode("1231", CreditCardType.AMEX, CreditCardCountry.AR.name());

	# 3 digits cvv2 for Amex Argentina is not ok
	CreditCardValidator.validateSecurityCode("", CreditCardType.AMEX, CreditCardCountry.PE.name());
	Assert.p fail("is not valid");
```

3. Validate pan with franchise

```java 
	# Valid Mastercard pan
	String pan  = "2221000010000015";
    CreditCardValidator.validate(pan, CreditCardType.MASTERCARD);

	# Invalid Mastercard pan
	String number = "5105105105105101";
	CreditCardType creditCardType = CreditCardType.MASTERCARD;
	Assert.p fail("is not valid");
```
## Contributing

1. Clone it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request