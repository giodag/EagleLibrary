package com.univaq.eaglelibrary.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.univaq.eaglelibrary.controllerImpl.UserControllerImpl;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.utility.Permission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegistrationControllerGUI {
	
	@FXML
    private Button registration;

    @FXML
    private TextField name;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @FXML
    void registration(ActionEvent event) {
    	
    	ApplicationContext context = getContext();
    	UserControllerImpl userControllerImpl = (UserControllerImpl)context.getBean("userControllerImpl");
    	//TODO inserire messaggi dialog
    	UserDTO userDTO = decorateUserDTO();
    	try {
			userControllerImpl.registration(userDTO);
			Alert alertOK = new Alert(AlertType.CONFIRMATION);
			alertOK.setHeaderText("Registration confirmed");
			alertOK.showAndWait();
        	Stage stage = (Stage) registration.getScene().getWindow();
            stage.close();
			LoginView loginView = (LoginView)context.getBean("loginView");
			loginView.startupLogin();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }

	private UserDTO decorateUserDTO() {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setFirstName(name.getText());
		userDTO.setLastName(lastname.getText());
		userDTO.setEmail(email.getText());
		userDTO.setUsername(username.getText());
		userDTO.setPassword(password.getText());
		userDTO.setPermission(Permission.USER);
		
		return userDTO;
	}
	
	private ApplicationContext getContext() {
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		return context;
    }
}
