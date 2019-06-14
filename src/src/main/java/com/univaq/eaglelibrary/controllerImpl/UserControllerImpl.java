package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.hanlder.UserHanlder;
import com.univaq.eaglelibrary.persistence.PersistenceService;

public class UserControllerImpl implements UserController {
	
	private UserHanlder userHanlder;
	private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	public UserControllerImpl(PersistenceService persistenceService) {
		this.userHanlder = new UserHanlder(persistenceService);
	}

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO registration(UserDTO userDTO) {
		this.userHanlder.registration(userDTO);
		logger.debug("Start registration");
		return this.userHanlder.registration(userDTO);
	}

	public ResultDTO logout(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}