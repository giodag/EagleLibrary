package com.univaq.eaglelibrary.dto;

import java.util.List;

import com.univaq.eaglelibrary.utility.Permission;
import com.univaq.eaglelibrary.view.TranscriptionTable;

public class UserDTO {
	
	private Long id;
	private Boolean activated;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Enum<Permission> permission;
	private Integer level;
	private List<TranscriptionDTO> transcriptionList;
	private String password;
	private TranscriptionTable transcriptionTable;
	
	//-- Getter and Setter --//

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public TranscriptionTable getTranscriptionTable() {
		return transcriptionTable;
	}
	public void setTranscriptionTable(TranscriptionTable transcriptionTable) {
		this.transcriptionTable = transcriptionTable;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getActivated() {
		return activated;
	}
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	public Enum<Permission> getPermission() {
		return permission;
	}
	public void setPermission(Enum<Permission> permission) {
		this.permission = permission;
	}
	public List<TranscriptionDTO> getTranscriptionList() {
		return transcriptionList;
	}
	public void setTranscriptionList(List<TranscriptionDTO> transcriptionList) {
		this.transcriptionList = transcriptionList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String emailAddress) {
		this.email = emailAddress;
	}
}
