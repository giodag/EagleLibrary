package com.univaq.eaglelibrary.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
    @GeneratedValue
	private Long id;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "activated")
	private Boolean activated;
	
	@Column(name = "permission")
	private String permission;
	
	@Column(name = "level")
	private Integer level;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			  name = "user_transcription", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "transcription_id"))
	private List<Transcription> listTranscription;
	
	// -- Getter and Setter --//
	
	public List<Transcription> getListTranscription() {
		return listTranscription;
	}
	public void setListTranscription(List<Transcription> listTranscription) {
		this.listTranscription = listTranscription;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean isActivated() {
		return activated;
	}
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}
