
### Project Overview

This project is for converting Integer to Roman numeral value. Following endpoint 'http://localhost:8080/romannumeral?query={integer}' is made available for taking integer as an input and convert into Roman numeral value and return JSON response with input and output.

## Contents

* [Packaging Layout/Dependency Attribution](#packaging-layout-/-dependency-attribution)
* [Tools/Technologies Used](#tools-/-technologies-used)
* [Building and Running Project](#building-and-running-project)
    * [Pre-steps](#pre-steps)
    * [Automated Script: steps to run the project](#automated-script-steps-to-run-the-project)
* [Integrate Grafana with Prometheus metrics](#integrate-grafana-with-prometheus-metrics)
* [Monitoring Application metrics queries](#monitoring-application-metrics-queries)
* [Alerting](#alerting)
* [Testing Methodology](#testing-methodology)
* [Engineering Methodology](#engineering-methodology)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)

## Packaging Layout / Dependency Attribution:

This section covers the various **modules** used in the project:

![image](https://user-images.githubusercontent.com/65324839/136126343-9e34d8de-1405-4516-8070-e4f938108f4a.png)

* **controller**
> Class : IntegerToRomanConvController
>  
> Handles REST requests. I have coded GET request for romannumeral URI with one query param. Following is the URI supported by this controller class, 'http://localhost:8080/romannumeral?query={integer}', which takes input as integer and returns JSON response.
> To delegate the request to IntegerToRomanConverterService class, we are instantiating service object here in the constructor.

* **service**
> Class : IntegerToRomanConverterService
> 
>This has the business logic for the Integer to Roman numeral conversion. From controller, we receive the input as an integer and here we check and convert it to Roman numeral and store the result in RomanNumeral object by setting both input and converted values as strings and return it back to controller. Incase if the input is out of range, method throws InvalidInputException.

* **model**
> Class : RomanNumeral
> 
>Here we have RomanNumeral model object used as part of Romannumeral conversion service class for returning response with input and converted value.
* **configuration**
> Class : RomanNumeralConverterConfig
> 
> We are maintaining configuration classes for binding the properties defined in application.properties file.

* **common**
> Class : RomanNumeralConstants
> 
> We are having constant class where we are defining error messages.

* **exception**
> Class : InvalidInputException
> 
> This holds the customized exception classes

* **handler**
> Class : RomanNumeralConverterExceptionHandler
>  
> In this we are having classes to handle the exceptions thrown by controller classes.

## Tools/Technologies Used:

![CD](https://user-images.githubusercontent.com/65324839/136108524-7a83e5c3-7130-4636-851a-17f65f910cf3.JPG)

- **Kubernetes**

## Building and Running Project:

This process presumes that your system has the following software's pre-installed: git, docker, kubernetes.

### Pre-Steps:
>1. Create a Docker image using the Dockerfile available in the GitHub Project 'https://github.com/sannihithatummala23/DevOps' by executing the command:

``` bash
docker build --build-arg url=https://github.com/sannihithatummala23/RomanNumeralConverter.git\
  --build-arg project=RomanNumeralConverter\
  -t sannihithatummala/a-project - < Dockerfile
```

The above Dockerfile which is a **multi-stage build**, in the first stage, clones the project from the GitHub url 'https://github.com/sannihithatummala23/RomanNumeralConverter/'. In the second stage, it copies the required files from the first stage and helps to build the final image required to run the spring-boot application using the **embedded Tomcat server**.

>2. Then we upload the created docker image that can be found in ($docker image ls) to hub.docker.com by executing the command:

``` bash
docker push sannihithatummala/a-project:latest
```

### Automated Script: steps to run the project:
Run the 'automation-script' shell script available at 'https://github.com/sannihithatummala23/DevOps' which executes the below steps:

>1. Create a directory called 'project' and clone 'https://github.com/sannihithatummala23/DevOps' into it.

>2. Create a K8 nameSpace called 'monitoring' and execute the k8 deployment and service files to create a deployment and service for our application:

``` bash
kubectl create namespace monitoring
```

``` bash
kubectl create -f deployment.yml -n monitoring
```

``` bash
kubectl create -f service.yml -n monitoring
```

This ends up creating a pod and the service for the RomanNumeralConverter application.

>3. In order to access the application 'http://localhost:8080/romannumeral?query=999', we need to execute **k8 port-forwarding** command that runs as backround process:

``` bash
kubectl port-forward service/romannumeralconverter-svc -n monitoring 8080:8080 &
```
![999](https://user-images.githubusercontent.com/65324839/136109998-7e4df3fa-43af-4685-b3ef-fa92998d998c.JPG)

We can also check the health of the application by hitting 'http://localhost:8080/actuator/health'
![health](https://user-images.githubusercontent.com/65324839/136110196-142fe24c-f86a-4d11-9bf9-4514dfcb5ca7.JPG)

>4. Deploy **Grafana** by executing the docker commands:

``` bash
docker run -d --name grafana -p 3000:3000 grafana/grafana
```
And navigate to 'http://localhost:3000' to explore the Grafana.

>5. Use the 'prometheus.yml' file available in the GitHub Project 'https://github.com/sannihithatummala23/DevOps' to configure the prometheus scrape_configs: 'spring-actuator' targets:[xx:xx:xx:xx:8080] with the Cluster-IP address we retrive from the k8 Service 'romannumeralconverter-svc', so that it retrives the metrics data from Spring Boot Actuator /prometheus endpoint 'http://localhost:8080/actuator/prometheus'. Then deploy prometheus by executing the docker command:

``` bash
docker run -d --name prometheus -p 9090:9090 -v /{path to file}/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```

##### Prometheus Metrics scraped from the application:

![metrics](https://user-images.githubusercontent.com/65324839/136110222-9bf65975-b69f-4e4b-b575-f48bddfe45ee.JPG)

Navigate to 'http://localhost:9090' to explore the **Prometheus** dashboard.

![Prometheus UI](https://user-images.githubusercontent.com/65324839/136108956-60f75b19-77e1-4d06-bb28-6e092b1477f6.JPG)

## Integrate Grafana with Prometheus metrics:

Login to Grafana and click on "Add Data Source" and select "Prometheus", then update HTTP URL(Cluster-IP address we retrive from the k8 Service 'romannumeralconverter-svc') as defined in the prometheus.yml file.
![Prometheus Config](https://user-images.githubusercontent.com/65324839/136107905-93bf6d84-3902-4cd3-a298-3d869e4cff66.JPG)



##### Create a dashboard to visualize Prometheus metrics:

![Image 10-4-21 at 10 49 PM](https://user-images.githubusercontent.com/65324839/136107968-028f4042-139e-4df8-8391-c30c2a626172.JPG)

## Monitoring Application metrics queries:


![Exception query](https://user-images.githubusercontent.com/65324839/136108309-77b9798e-1feb-4b3e-98d7-391f9cdfc3a0.JPG)


![status 400 query](https://user-images.githubusercontent.com/65324839/136108283-9ebc000c-b928-47d0-b8ca-f4568913a018.JPG)


![process uptime](https://user-images.githubusercontent.com/65324839/136108231-e65aae5e-d363-41bb-8ba9-fd08a9713069.JPG)


![cpu usage query](https://user-images.githubusercontent.com/65324839/136108195-ba97f53d-3470-4102-b3d4-d79a7e8aa80f.JPG)


## Alerting:

Additionally, we can also configure alerting in Grafana and Prometheus(AlertManager). Below is an example of the alerting mechanism in which, grafana evaluated the set conditions, and if the condition is met, it can notify us using the various plugins available like Email, Slack, Teams, PagerDuty....

![Image 10-4-21 at 9 41 PM](https://user-images.githubusercontent.com/65324839/136130381-87ef0ed4-9faf-4a0f-afb4-726577bade54.JPG)


## Testing Methodology:

 >1. JUNIT test cases are already included as part of project to test positive(i.e giving integer as input) and negative case(i.e giving string as input).
 >2. Once application is up we can test following scenarios:
 >   * Case 1:
     URI : 'http://localhost:8080/romannumeral?query=3999'
     Expected Output : 
     {
     "input": "3999",
     "output": "MMMCMXCIX"
     }
     ![3999](https://user-images.githubusercontent.com/65324839/136107067-1404f8bb-6646-4cb4-8c98-fc9e61e1058f.JPG)


 >   * Case 2: URI : 'http://localhost:8080/romannumeral?query=0'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       ![0](https://user-images.githubusercontent.com/65324839/136107018-75805066-bcb6-46ac-898e-a2b780d133bd.JPG)


 >   * Case 3 : URI : 'http://localhost:8080/romannumeral?query=sanni'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       ![sanni](https://user-images.githubusercontent.com/65324839/136106941-649e8c99-71c3-41d4-80c7-5283a01fe41c.JPG)


 >   * Case 4 : URI : 'http://localhost:8080/romannumeral?query=null'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       ![null](https://user-images.githubusercontent.com/65324839/136106702-1d10feed-2a32-41a8-ae3d-51ef35fc4c71.JPG)


 >   * Case 5 : URI : 'http://localhost:8080/romannumeral'
       Expected O/P : URI is not supported. Please make a request to : 'http://localhost:8080/romannumeral?query={integer}' for getting integer converted to Roman numeral
       ![romannumeral](https://user-images.githubusercontent.com/65324839/136106673-7be45cc4-e710-4bc1-9177-4bebd2971e07.JPG)


 >   * Case 6 : URI : 'http://localhost:8080/romannumeral?query=4000'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       !![4000](https://user-images.githubusercontent.com/65324839/136106518-5a838c79-e7d8-4a44-9c10-225ba482facf.JPG)


 >   * Case 7 : URI : 'http://localhost:8080/romannumeral?query=1.2'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       ![1 2](https://user-images.githubusercontent.com/65324839/136106185-c0aae7a2-2bd3-4c88-8900-617ee41aeefb.JPG)


 >   * Case 8 : URI : 'http://localhost:8080/romannumeral?query=-23'
       Expected O/P : Invalid Request. Input should be in the range of 1-3999 numbers
       ![-23](https://user-images.githubusercontent.com/65324839/136108472-6a1918c9-181c-4a17-b0c4-c2d222e8b767.JPG)

## Engineering Methodology:
- DevOps Agile (Requirement Gathering > Planning > Developing > Testing > Releasing > Monitoring)

## Room for Improvement:
- Can automate the whole process using Jenkins/GitLab pipeline.
- Modify README concisely.

## Contact:
- Developed by **Sannihitha Tummala**
- E-Mail: sannihithatummala@gmail.com

