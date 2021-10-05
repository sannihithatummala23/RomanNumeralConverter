### RomanNumeralConverter project documentation
#### Project Overview

This project is for converting Integer to Roman numeral value. Following endpoint'http://localhost:8080/romannumeral?query={integer}'  is made available for taking integer as input and convert into Roman numeral value and return JSON response with input and output. 

### Modules

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

##### DevOps CI/CD Process:

This process presumes that your system has the following software's pre-installed: git, docker, kubernetes.

>1. Create a Docker image using the Dockerfile available in the GitHub Project(https://github.com/sannihithatummala23/DevOps) by executing the command:
$docker build --build-arg url=https://github.com/sannihithatummala23/RomanNumeralConverter.git\
  --build-arg project=RomanNumeralConverter\
  -t sannihithatummala/a-project - < Dockerfile
The above Dockerfile which is a multi-stage build, in the first stage, clones the project from the GitHub url. In the second stage, it copies the required files from the first stage and helps to build the final image required to run the spring-boot application using the embedded Tomcat server.

>2. Then we upload the created docker image to hub.docker.com executing the command:
$docker push sannihithatummala/a-project:1.0 (Download image that can be found in $docker image ls)

>3. Execute the k8 deployment and service files available in the GitHub Project(https://github.com/sannihithatummala23/DevOps).
$kubectl create -f deployment.yml
$kubectl create -f service.yml
This ends up creating a pod and the service for the RomanNumeralConverter application. Application can be available at http://localhost:8080/romannumeral?query=100

>4. Use the prometheus.yml file available in the GitHub Project(https://github.com/sannihithatummala23/DevOps) to configure the prometheus scrape configurations so that it retrives the metrics data from Spring Boot Actuator /prometheus endpoint(http://localhost:8080/actuator/prometheus). Then deploy prometheus by executing the docker command:
$docker pull prom/prometheus
$docker run -d --name prometheus -p 9090:9090 -v /{path to file}/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
Navigate to http://localhost:9090 to explore the Prometheus dashboard.

>5. Deploy Grafana by executing the docker commands:
$docker run -d --name grafana -p 3000:3000 grafana/grafana
Navigate to http://localhost:3000 to explore the Grafana dashboard.

>6. Integrate Grafana with Prometheus metrics: Login and click on "Add Data Source" and select "Prometheus", then add HTTP URL as you defined in prometheus.yml file. Create a dashboard to visualize Prometheus metrics.

##### Steps to run the project

>1. Run the 'automation-script' shell script available in the GitHub Project(https://github.com/sannihithatummala23/DevOps) to automate the above steps.
>2. Deploy prometheus by executing the docker command:
$docker run -d --name prometheus -p 9090:9090 -v {path_to_file}/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
Note: The above step needs to be executed after the shell script, as the CLuster-IP of the roman-numeral-deploy pod needs to be updated at:
  - job_name: 'spring-actuator'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s
    static_configs:
    - targets: ['<Cluster-IP>:8080']

##### Testing methodology
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
     
