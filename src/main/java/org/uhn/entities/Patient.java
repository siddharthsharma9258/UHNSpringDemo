package org.uhn.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Patient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3237282021340082092L;
	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private Timestamp dateOfBirth;

	public Patient() {}

	public Patient(String firstName, String lastName,Timestamp dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Patient patient = (Patient) o;
		return Objects.equals(id, patient.id) &&
			Objects.equals(firstName, patient.firstName) &&
			Objects.equals(lastName, patient.lastName) &&
			Objects.equals(dateOfBirth, patient.dateOfBirth);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}