# API Automation using RESTAssured (JAVA).

## ➡️ Introduction :
This project is the outcome of exploring the API Testing Automation framework - Rest-assured (java). I am writing a basic assertion for validating API response.

## ➡️ Repo includes :
This repo contains examples of API Tests using Rest-Assured and that TestNG is used for assertions. 

## ➡️ Mainly used :
1. Rest-Assured
2. Maven 
3. TestNG

 ## ➡️ Added assertions :
1. Verified where the status code is 200 or not.
2. Verified where status as Success.
3. Stored rate.aed value.
4. Verify rates have 162 values.
5. Verified stored rate.aed value is between 3.6 to 3.7.
6. Varified below steps for JSON validation:
	6.1 Store schema from URL.
	6.2 Store API response as String.
	6.3 Utilize API response string as JSON node.
	6.4 Utilize JSON node as schema.
	6.5 Assertion for matching schema from URL and schema from the API response. If it matches 100%, it will return null.
