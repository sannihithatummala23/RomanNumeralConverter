###RomanNumeralConverter project documentation
####Project Overview

This project is for converting Integer to Roman numeral value. Following endpoint [http://localhost:8080/romannumeral?query={integer}]  is made available for taking integer as input and convert into Roman numeral value and return JSON response with input and output. 

#####Modules

* controller
> Class : IntegerToRomanConvController
>  
> Handles REST requests. We have coded GET request for romannumeral URI with one query param. Following is the URI supported by this controller class, http://localhost:8080/romannumeral?query={integer}, this URI takes input as integer and returns JSON response.
* service
> Class : IntegerToRomanConverterService
> 
>This has the business logic for the Integer to Roman numeral conversion. From controller we receive the input as Integer and here we check and convert it to Roman numeral and store the result in RomanNumeral object by setting both input and converted values as strings and return it back to controller. Incase if the input is out of range, method throws InvalidInputException.
* model
> Class : RomanNumeral
> 
>Here we have RomanNumeral model object used as part of Romannumeral conversion service class for returning response with input and converted value.
* configuration
> Class : RomanNumeralConverterConfig
> 
> We are maintaining configuration classes for binding the properties defined in application.properties file.
* common
> Class : RomanNumeralConstants
> 
> We are having constant class where we are defining error messages. 
* exception
> Class : InvalidInputException
> 
> This holds the customized exception classes
* handler
> Class : RomanNumeralConverterExceptionHandler
>  
> In this we are having classes to handle the exceptions thrown by controller classes.

#####Steps to run the project

>1. Docker image is available in docker hub, following command needs to be executed for pulling image and bringing up the container.
>2. 


#####Testing methodology
 >1. JUNIT test cases are already included as part of project to test positive (i.e giving integer as input) and negative case(i.e giving string as input).
 >2. Once application is up we can test following scenarios,
 >   * Case 1:
     URI : http://localhost:8080/romannumeral?query=3999
     Expected Output : 
     {
     "input": "3999",
     "output": "MMMCMXCIX"
     }
 >   * Case 2: URI : http://localhost:8080/romannumeral?query=0
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
 >   * Case 3 : URI : http://localhost:8080/romannumeral?query=sanni
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
 >   * Case 4 : URI : http://localhost:8080/romannumeral?query=null
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
 >   * Case 5 : URI : http://localhost:8080/romannumeral
       Expected Output : URI is not supported. Please make a request to : http://localhost:8080/romannumeral?query={provide integer input here} for getting integer converted to Roman numeral
 >   * Case 6 : URI : http://localhost:8080/romannumeral?query=4000
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
 >   * Case 7 : URI : http://localhost:8080/romannumeral?query=1.2
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
     