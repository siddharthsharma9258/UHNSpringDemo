package org.uhn.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.uhn.entities.Patient;
import org.uhn.exceptions.ExceptionList;
import org.uhn.exceptions.RepositoryMasterExceptions;
import org.uhn.repository.PatientRepository;

@RestController
public class BaseController {

	  private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	  private final PatientRepository repository;

	  BaseController(PatientRepository repository) {
	    this.repository = repository;
	  }
	  
	  
	  @GetMapping("/api/start")
	  String start() {
		  return "Welcome to the UHN Spring Demo!";
	  }
	  
	  
	  @GetMapping("/api/getAllPatients")
	  List<Patient> getAllPatients() throws Exception {
		  if(repository.findAll().size()==0) {
			  throw new RepositoryMasterExceptions(ExceptionList.REP00001);
		  } else {
			  logger.info("Found " + repository.findAll().size() +  " patients in the repository.");
			  return repository.findAll();
		  }
	  }
	  
	  @GetMapping("/api/getPatient/{id}")
	  Patient getPatientById(@PathVariable Long id) throws Exception {
		  logger.info("getPatient API invoked with query  for Id:" + id);
		  Patient resolved = repository.findById(id).orElse(null);
		  if(resolved != null) {
			  logger.info("Found patient with Id " + id + " in the repository.");
			  return resolved;
		  } else {
			  logger.info("No Patient found with Id " + id + " in the repository.");
			  throw new RepositoryMasterExceptions(ExceptionList.REP00002);
		  }
	  }
	  
	  @GetMapping("/api/searchPatients/{keyword}")
	  List<Patient> searchByName(@PathVariable String keyword) throws Exception {
		  logger.info("searchPatient API invoked with query for Keyword: " + keyword);
		//will search for patients with either matching a firstname or lastname
		List<Patient> foundList = new ArrayList<>();
		Predicate<Patient> p1 = p -> p.getFirstName().equalsIgnoreCase(keyword);
		Predicate<Patient> p2 = p -> p.getLastName().equalsIgnoreCase(keyword);
		foundList = repository.findAll().stream().filter(p1.or(p2)).collect(Collectors.toList());
	    if(foundList != null && foundList.size() == 0) {
	    	logger.info("No Patient found with keyword " + keyword + " in the repository.");
	    	throw new RepositoryMasterExceptions(ExceptionList.REP00003);
	    } else {
	    	logger.info("Found " + foundList.size() + " patients for search keyword "  + keyword + " in the repository.");
	    	return foundList;
	    }
	  }
	  
	  @PostMapping("/api/addNewPatient")
	  Patient addNewPatient(@RequestBody Patient patient) throws Exception {
		try { 
			scanInputFields(patient);
			return repository.save(patient);
		} catch(Exception ex) {
			throw ex;
		}
	  }
	  
	  private  static void scanInputFields(Patient patient) throws Exception {
		  //Method that scans and validates input fields.
		  if(patient.getFirstName() == null) {
			  logger.info("First Name is not present in the request. Aborting.");
			  throw new RepositoryMasterExceptions(ExceptionList.REP00004);
		  }
		  if(patient.getLastName() == null) {
			  logger.info("Last Name is not present in the request.Aborting.");
			  throw new RepositoryMasterExceptions(ExceptionList.REP00005);
		  }
		  if(patient.getDateOfBirth() == null) {
			  logger.info("Date Of Birth is not present in the request.Aborting.");
			  throw new RepositoryMasterExceptions(ExceptionList.REP00006);
		  }
		  if(patient.getFirstName() != null && patient.getFirstName().length() == 0) {
			  throw new RepositoryMasterExceptions(ExceptionList.REP00007);
		  }
		  if(patient.getLastName() != null && patient.getLastName().length() == 0) {
			  throw new RepositoryMasterExceptions(ExceptionList.REP00008);
		  }
	  }

	  @PostMapping("/api/bulkAddPatients")
	  List<Patient> bulkAddPatients(@RequestBody List<Patient> patients) throws Exception {
		  //bulk addition of patients.
		  
		  List<Patient> addedPatients = new ArrayList<>();
		  for(Patient p:patients) {
			  try {
				scanInputFields(p);
				addedPatients.add(repository.save(p));
			} catch (Exception e) {
				throw e;
			}
		  }
		  
		  return addedPatients;
	  }
	  
	  @DeleteMapping("/api/clear")
	  void deleteAllPatients() {
		  //will clean all the patients in the repository.
		  logger.info("Clearing Patient Repository");
		  repository.deleteAll();
	  }
}   