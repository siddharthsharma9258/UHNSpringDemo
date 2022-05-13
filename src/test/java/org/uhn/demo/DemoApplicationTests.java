package org.uhn.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.uhn.exceptions.ExceptionMessages;
import org.uhn.exceptions.RepositoryMasterExceptions;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void startAPITest() throws Exception {
		this.mockMvc.perform(get("/api/start"))
					//.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content()
					.string(containsString("Welcome to the UHN Spring Demo!")));
	}
	
	@Test
	public void noAPIExistTest() throws Exception {
		this.mockMvc.perform(get("/api/somedummyAPI"))
					.andExpect(status()
					.isNotFound());
	}
	
	@Test
	public void repositoryWithDefaultSeededPatientTest() throws Exception {
		this.mockMvc.perform(get("/api/getAllPatients"))
					.andExpect(status()
					.isOk())
	//				.andDo(print())
					.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Marco"));
	}
	
	@Test
	public void getPatientWithId() throws Exception {
		this.mockMvc.perform(get("/api/getPatient/1"))
					.andExpect(status()
					.isOk())
	//				.andDo(print())
					.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Marco"));
	}
	
	
	@Test
	public void getPatientWithFalseId() throws Exception {
		this.mockMvc.perform(get("/api/getPatient/123"))
					.andExpect(status()
					.is4xxClientError());				
	}
	
	@Test
	public void getPatientWithFalseIdExceptionMessage() throws Exception {
		this.mockMvc.perform(get("/api/getPatient/123"))
					.andExpect(status()
					.is4xxClientError())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
					.andExpect(content().string(ExceptionMessages.REPM00002));
	}
	
	
	@Test
	public void searchPatientThatAlreadyExist() throws Exception {
		this.mockMvc.perform(get("/api/searchPatients/Marco"))
					.andExpect(status()
					.isOk())
					//.andDo(print())
					.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Marco"));
	}
	
	@Test
	public void searchPatientThatDoesNotExist() throws Exception {
		this.mockMvc.perform(get("/api/searchPatients/Jason"))
					.andExpect(status()
					.is4xxClientError())
	//				.andDo(print())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
					.andExpect(content().string(ExceptionMessages.REPM00003));
	}
	
	
	@Test
	public void addValidPatient() throws Exception {
		String body = "{\"firstName\": \"Marco\",\"lastName\":\"Polo\",\"dateOfBirth\":\"2022-05-12T20:48:31.535+00:00\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().isOk());
		
		// Call get all patients API to see if the repo has one more patient than the default one.
		this.mockMvc.perform(get("/api/getAllPatients"))
				.andExpect(status()
				.isOk())
			//	.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}
	
	
	@Test
	public void addPatient_NoFirstName() throws Exception {
		String body = "{\"lastName\":\"Polo\",\"dateOfBirth\":\"2022-05-12T20:48:31.535+00:00\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
				.andExpect(content().string(ExceptionMessages.REPM00004));	
	}
	
	@Test
	public void addPatient_FirstNameIsEmpty() throws Exception {
		String body = "{\"firstName\": \"\",\"lastName\":\"Polo\",\"dateOfBirth\":\"2022-05-12T20:48:31.535+00:00\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
				.andExpect(content().string(ExceptionMessages.REPM00007));	
	}
	
	@Test
	public void addPatient_NoLastName() throws Exception {
		String body = "{\"firstName\": \"Marco\",\"dateOfBirth\":\"2022-05-12T20:48:31.535+00:00\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
				.andExpect(content().string(ExceptionMessages.REPM00005));	
	}
	
	@Test
	public void addPatient_LastNameIsEmpty() throws Exception {
		String body = "{\"firstName\": \"Marco\",\"lastName\":\"\",\"dateOfBirth\":\"2022-05-12T20:48:31.535+00:00\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
				.andExpect(content().string(ExceptionMessages.REPM00008));	
	}
	
	@Test
	public void addPatient_NoDOB() throws Exception {
		String body = "{\"firstName\": \"Marco\",\"lastName\":\"Polo\"}";
		this.mockMvc.perform(post("/api/addNewPatient")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(body)
			    .characterEncoding("utf-8"))
			    .andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof  RepositoryMasterExceptions))
				.andExpect(content().string(ExceptionMessages.REPM00006));	
	}
}
