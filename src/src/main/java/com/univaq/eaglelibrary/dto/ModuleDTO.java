package com.univaq.eaglelibrary.dto;

public class ModuleDTO {
	
	private Long id;
	private String username;
	private String status;
	
	//-- Getter and Setter --//

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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
