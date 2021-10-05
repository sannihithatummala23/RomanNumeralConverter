### RomanNumeralConverter project documentation
#### Project Overview

This project is for converting Integer to Roman numeral value. Following endpoint 'http://localhost:8080/romannumeral?query={integer}' is made available for taking integer as input and convert into Roman numeral value and return JSON response with input and output. 

### Modules

* controller
> Class : IntegerToRomanConvController
>  
> Handles REST requests. I have coded GET request for romannumeral URI with one query param. Following is the URI supported by this controller class, 'http://localhost:8080/romannumeral?query={integer}', which takes input as integer and returns JSON response.
* service
> Class : IntegerToRomanConverterService
> 
>This has the business logic for the Integer to Roman numeral conversion. From controller we receive the input as an Integer and here we check and convert it to Roman numeral and store the result in RomanNumeral object by setting both input and converted values as strings and return it back to controller. Incase if the input is out of range, method throws InvalidInputException.
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

##### DevOps CD Process:

This process presumes that your system has the following software's pre-installed: git, docker, kubernetes.

## Pre-Steps:
>1. Create a Docker image using the Dockerfile available in the GitHub Project 'https://github.com/sannihithatummala23/DevOps' by executing the command:
$docker build --build-arg url=https://github.com/sannihithatummala23/RomanNumeralConverter.git\
  --build-arg project=RomanNumeralConverter\
  -t sannihithatummala/a-project - < Dockerfile
The above Dockerfile which is a multi-stage build, in the first stage, clones the project from the GitHub url. In the second stage, it copies the required files from the first stage and helps to build the final image required to run the spring-boot application using the embedded Tomcat server.

>2. Then we upload the created docker image that can be found in ($docker image ls) to hub.docker.com executing the command:
$docker push sannihithatummala/a-project:1.0

## Automated Script - steps to run the project:
Run the 'automation-script' shell script available at 'https://github.com/sannihithatummala23/DevOps' which executes the below steps:

>1. Create a directory called 'project' and clone 'https://github.com/sannihithatummala23/DevOps' into it.

>2. Create a K8 nameSpace called 'monitoring' and execute the k8 deployment and service files to create a deployment and service for our application:
$kubectl create -f deployment.yml
$kubectl create -f service.yml

This ends up creating a pod and the service for the RomanNumeralConverter application. 

>3. In order to access the application 'http://localhost:8080/romannumeral?query=1', we need to execute k8 port-forwarding command that runs as backround process:
$kubectl port-forward service/romannumeralconverter-svc -n monitoring 8080:8080 &

>4. Deploy Grafana by executing the docker commands:
$docker run -d --name grafana -p 3000:3000 grafana/grafana
Navigate to 'http://localhost:3000' to explore the Grafana.

>5. Use the 'prometheus.yml' file available in the GitHub Project 'https://github.com/sannihithatummala23/DevOps' to configure the prometheus scrape_configs: 'spring-actuator' targets:[xx:xx:xx:xx:8080] with the Cluster-IP address we retrive from the k8 Service 'romannumeralconverter-svc', so that it retrives the metrics data from Spring Boot Actuator /prometheus endpoint 'http://localhost:8080/actuator/prometheus'. Then deploy prometheus by executing the docker command:
$docker pull prom/prometheus
$docker run -d --name prometheus -p 9090:9090 -v /{path to file}/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
Navigate to 'http://localhost:9090' to explore the Prometheus dashboard.

## Integrate Grafana with Prometheus metrics

Login and click on "Add Data Source" and select "Prometheus", then update HTTP URL(Cluster-IP address we retrive from the k8 Service 'romannumeralconverter-svc') as defined in the prometheus.yml file.

Create a dashboard to visualize Prometheus metrics.

##### Testing methodology
 >1. JUNIT test cases are already included as part of project to test positive (i.e giving integer as input) and negative case(i.e giving string as input).
 >2. Once application is up we can test following scenarios,
 >   * Case 1:
     URI : 'http://localhost:8080/romannumeral?query=3999'
     Expected Output : 
     {
     "input": "3999",
     "output": "MMMCMXCIX"
     }
     ![3999](https://user-images.githubusercontent.com/65324839/136107067-1404f8bb-6646-4cb4-8c98-fc9e61e1058f.JPG)

 >   * Case 2: URI : 'http://localhost:8080/romannumeral?query=0'
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
       ![0](https://user-images.githubusercontent.com/65324839/136107018-75805066-bcb6-46ac-898e-a2b780d133bd.JPG)

 >   * Case 3 : URI : 'http://localhost:8080/romannumeral?query=sanni'
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
       ![sanni](https://user-images.githubusercontent.com/65324839/136106941-649e8c99-71c3-41d4-80c7-5283a01fe41c.JPG)

 >   * Case 4 : URI : 'http://localhost:8080/romannumeral?query=null'
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
       ![null](https://user-images.githubusercontent.com/65324839/136106702-1d10feed-2a32-41a8-ae3d-51ef35fc4c71.JPG)

 >   * Case 5 : URI : 'http://localhost:8080/romannumeral'
       Expected Output : URI is not supported. Please make a request to : 'http://localhost:8080/romannumeral?query={integer}' for getting integer converted to Roman numeral
       ![romannumeral](https://user-images.githubusercontent.com/65324839/136106673-7be45cc4-e710-4bc1-9177-4bebd2971e07.JPG)

 >   * Case 6 : URI : 'http://localhost:8080/romannumeral?query=4000'
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
       !![4000](https://user-images.githubusercontent.com/65324839/136106518-5a838c79-e7d8-4a44-9c10-225ba482facf.JPG)

 >   * Case 7 : URI : 'http://localhost:8080/romannumeral?query=1.2'
       Expected Output : Invalid Request. Input should be in the range of 1-3999 numbers
       ![1 2](https://user-images.githubusercontent.com/65324839/136106185-c0aae7a2-2bd3-4c88-8900-617ee41aeefb.JPG)

     
