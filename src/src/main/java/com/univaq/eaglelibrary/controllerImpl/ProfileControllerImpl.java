package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.ProfileController;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.hanlder.ProfileHandler;
/**
 * L'implementazione dell'interfaccia controller, orchestra le chiamate verso il core computazionale 
 * del sistema minimizzando cos� gli impatti tra la parte view e la parte logica nel caso di change requests.
 *
 */
@Service
public class ProfileControllerImpl implements ProfileController {
	
	@Autowired
	private ProfileHandler profileHandler;
	
	private final Logger logger = LoggerFactory.getLogger(ProfileControllerImpl.class);

	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException {
		logger.debug("start createUpdateProfile");
		ProfileDTO profileDTOCreated = this.profileHandler.createUpdateProfile(profileDTO);
		logger.debug("finish createUpdateProfile");
		return profileDTOCreated;
	}

	public ProfileDTO getProfile(ProfileDTO profileDTO) {
		logger.debug("start getProfile");
		ProfileDTO profileDTORead = this.profileHandler.readProfile(profileDTO);
		logger.debug("finish getProfile");
		return profileDTORead;
	}
}
