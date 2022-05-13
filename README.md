# UHNSpringDemo
Spring REST API demo application built for UHN

#**Introduction**

1. The application uses an H2 database to host the patient repository. 
2. Application runs on 8080 port.
3. Browser operations are done on the context-path /demo
4. Postman or CURL operations can be performed on path /api
5. Logging is done on the console instead of a file.


Steps to build and run the application.

1. Clone the repository onto a folder on the machine.
2. Step inside UHNSpringDemo folder.
3. Invoke git bash.
4. Run mvn clean install. This will build the code base and create target folder.
5. After the process finishes, navigate to the target folder.
6. Run java -jar demo-0.0.1-SNAPSHOT.jar 


Supported APIs and operations.

1	GET localhost:8080/api/start	:	Welcome to the UHN Spring Demo!

2	GET localhost:8080/api/getAllPatients	
Returns a list of all the patients that exists within the repository.

3	GET localhost:8080/api/getPatient/{id}
Performs a search of patient by Id.

4	GET localhost:8080/api/searchPatients/{keyword}  
Performs a search by keyword matching on the firstName or lastName

5	POST localhost:8080/api/addNewPatient	     	     
Adds new Patient within the repository and returns the data of the patient just created with its id.

6	POST localhost:8080/api/bulkAddPatients          
Adds a List of patients within the repository and returns the data of patients just created with their respective Ids.

7	DELETE localhost:8080/api/clear       	       
Deletes all Patients within the repository.
