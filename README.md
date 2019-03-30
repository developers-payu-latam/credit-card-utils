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
```java 

2. Validate a security code


## Contributing

1. Clone it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request