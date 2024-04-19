# Pretranslation services

Two microservices working together in order to reduce costs of translation services.

## Description

### Machine Translation Service
Machine translation service is an external, third-party service. It provides an API with three endpoints: 
* GET /languages
  * returns a list of the supported language codes
  * ["en-US", "en-GB", "fr-FR", "de-DE"]
* GET /domains
  * returns the list of the supported content domains, which describes the content and provides beWer translation
  * ["academic", "business", "general", "casual"]
* POST /translate
  * translating provided content
  * request body:
    * source_language (required)
    * target_language (required)
    * domain (required)
    * content (required)

Service is charged per request whether valid or not so we want to create an adapter service Company MT Service.
Service is pretty much hardcoded since the **assumption** from the assignment is to create Company MT Service.
This service is needed to provide some mocked data to Company MT Service.

### Company MT Service
Developed adapter service to the Machine Translation Service which has two main jobs:
* to get supported languages and domains on a daily basis from MTService
* to receive requests and validate them before forwarding them to MTService
It provides one endpoint:
* POST /validated-translate
  * used to receive request, validate and if validation is OK send the request to MTService
  * request body:
    * source_language (required)
    * target_language (required)
    * domain (required)
    * content (required)
  * validation requirements are:
    * all parameters in request body are required to be sent
    * source_language and target_language are language codes and they must be in the list of available language codes received from MTService
    * domain must also be found in the list of available domains received from MTService
    * content must be less then 30 words
  * if validation is not met response code 400 is returned with apropriate message
  * if MTService is not available response code 503 Service Unavailable is returned by Company MT Service with the apropriate message

## Getting Started

Project is currently for local use. Two microservices are created to separate two different use cases. 
One being external third-party Machine Translation Service and another being inhouse Company MT Service.
In the project structure we can find the following (among other things):
* pretranslation-services - root of the project
  * services - where two microservices are stored
    * company-mt - the inhouse service
      *... CompanyMTServiceApplication.java - the starting point of the service 
    * machine-translation-service - the third-party service
      *... MachineTranslationServiceApplication.java - the starting point of the service

### Dependencies

* Local development environment
* Java 17
* maven
* Postman (or other similar tool)
* IntelliJ (or other IDE)

### Installing

* Clone the repository
* Build the project with maven eather running `mvn clean install` in terminal for both services or by UI

### Executing program

* Run the application
  * Suggestion: First run MachineTranslationServiceApplication
    * Endpoints become available on localhost:8082
    * URIs:
      * /api/mt/languages
      * /api/mt/domains
      * /api/mt/translate
  * Second run CompanyMTServiceApplicaiton
    * on initialization endpoints GET /languages and GET /domains from MTService are called to have needed data
    * if this service is ran first, the data will be empty since it doesn't have from where to get it
    * every day on midnight those two endpoints get called to refresh the data (as per requirement in the assignment)
    * we can chage that by changing the @Scheduled annotation in files `DomainServiceImpl.java` and `LanguageServiceImpl.java`
    * available are every minute, every ten minutes or every day at midnight, though you can add your own.
    * Endpoint becomes available on localhost:8080
    * URI:
      * /api/cmt/validated-translate
* Import provided postman collection into postman
  * you can call any of the available methods and in the POST /validated-translate play around with the data for validation

## To production ready 

* pass the code trough some linter services, SAST, DAST tools etc. Sonar qube, Zap scans, Burp scans, Coverity ... other
  * **DONE (sonar lint, sonar qube and default intelliJ linter)**
* write unit and integration tests (not enough time for that now since "it is expected that **a maximum of a few hours** will be spent working on the project")
* Dockerize the project
* deploy to dev/test/int/pro integrating the service with other non mock ones
* create a pipeline in Jenkins to automate all of this
* create webhooks to run all the tests and checks on PR merge or similar

## Authors

Contributors names and contact info

Jovan Jerotijevic [@LinkedIn](https://www.linkedin.com/in/jerot/)
