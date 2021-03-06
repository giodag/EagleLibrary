package com.univaq.eaglelibrary.hanlder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.univaq.eaglelibrary.converter.ConvertLiteraryWork;
import com.univaq.eaglelibrary.converter.ConvertPages;
import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.converter.ConvertUser;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.repository.PageRepository;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;
import com.univaq.eaglelibrary.repository.UserRepository;
/**
 * Handler che gestisce tutta la logica computazionale dell'area dati che interessa le trascrizioni delle pagine.
 *
 */
@Component
public class TranscriptionHanlder {

	@Autowired
	private TranscriptionRepository transcriptionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private ConvertTranscription convertTranscription;
	
	@Autowired
	private ConvertLiteraryWork convertLiteraryWork;
	
	@Autowired
	private ConvertPages convertPages;
	
	@Autowired
	private ConvertUser convertUser;

	private final Logger LOGGER = LoggerFactory.getLogger(TranscriptionHanlder.class);
	
	
	@Transactional
	public TranscriptionDTO createUpdateTranscription(TranscriptionDTO transcriptionDTO) {
		if(transcriptionDTO != null) {
			Transcription transcription = getTranscriptionEntityId(transcriptionDTO);
			Page page = pageRepository.findOne(transcriptionDTO.getPage().getId());
			if(transcription == null) {
				transcription = new Transcription();
				
				
			}
			transcription.setId(transcriptionDTO.getId() != null ? transcriptionDTO.getId() : transcription.getId());
			transcription.setPage(transcriptionDTO.getPage() != null ? convertPages.convertToModel(transcriptionDTO.getPage()) : transcription.getPage());
			//--TODO riga che ho aggiunto per vedere se risolve il problema detach;
//			transcription.getPage().setTranscription(transcription);
			page.setTranscription(transcription);
			transcription.setStatus(StringUtils.isNotEmpty(transcriptionDTO.getStatus()) ? transcriptionDTO.getStatus() : transcription.getStatus());
			transcription.setUsersWorkTranscription(transcriptionDTO.getUserList() != null && !transcriptionDTO.getUserList().isEmpty() ? convertUser.convertToModel(transcriptionDTO.getUserList()) : transcription.getUsersWorkTranscription());
			transcription.setTranscription(StringUtils.isNotEmpty(transcriptionDTO.getTranscription()) ? transcriptionDTO.getTranscription() : transcription.getTranscription());
			transcription.setLockByUser(transcriptionDTO.getLockedByuser() != null ? transcriptionDTO.getLockedByuser() : transcription.getLockByUser());
			//transcription = transcriptionRepository.save(transcription);
			//transcription=transcriptionRepository.assignTranscription(transcription.getStatus(), transcription.getLockByUser(), transcription.getTranscription(), page.getId());
			page.setTranscription(transcription);
			pageRepository.save(page);
			
			transcriptionDTO = convertTranscription.convert(transcription);
		}
		return transcriptionDTO;
	}

	@Transactional
	public TranscriptionDTO readTranscription(TranscriptionDTO transcriptionDTO) {
		TranscriptionDTO transcriptionDTORead = null;
		if(transcriptionDTO != null) {
			Transcription transcription = getTranscriptionEntity(transcriptionDTO);
			transcriptionDTORead = convertTranscription.convert(transcription);
		}
		return transcriptionDTORead;
	}
	
	private Transcription getTranscriptionEntity(TranscriptionDTO transcriptionDTO) {
		Transcription transcription = null;
		if (transcriptionDTO != null) {
			if (transcriptionDTO.getId() != null) {
				transcription = transcriptionRepository.findOne(transcriptionDTO.getId());
			} else {
				transcription = transcriptionRepository.findByFilter(transcriptionDTO.getTranscription(), transcriptionDTO.getStatus(),
						transcriptionDTO.getPage() != null ? transcriptionDTO.getPage().getId() : null,
								transcriptionDTO.getLockedByuser() != null ? transcriptionDTO.getLockedByuser() : null);
			}
		}
		return transcription;
	}
	
	private Transcription getTranscriptionEntityId(TranscriptionDTO transcriptionDTO) {
		Transcription transcription = null;
		if (transcriptionDTO != null) {
			if (transcriptionDTO.getId() != null) {
				transcription = transcriptionRepository.findOne(transcriptionDTO.getId());
			} else {
				if(transcriptionDTO.getPage() != null) {
					transcription = transcriptionRepository.findByIdPage(transcriptionDTO.getPage() != null ? transcriptionDTO.getPage().getId() : null);
				}
			}
		}
		return transcription;
	}

	@Transactional
	public List<TranscriptionDTO> readTranscriptionList(TranscriptionDTO transcriptionDTO) {
		List<TranscriptionDTO> transcriptionListRead = null;
		TranscriptionDTO transcriptionReadDTO = null;
		if(transcriptionDTO != null && transcriptionDTO.getUsername() != null) {
			transcriptionListRead = new ArrayList<TranscriptionDTO>();
			User user = userRepository.findByUsername(transcriptionDTO.getUsername());
			for (Transcription transcriptionEntityRead : user.getListTranscription()) {
				transcriptionReadDTO = convertTranscription.convert(transcriptionEntityRead);
				Page pageRead = transcriptionEntityRead.getPage();
				if(pageRead != null) {
					transcriptionReadDTO.setPage(convertPages.convert(pageRead));
					transcriptionReadDTO.setLiteraryWork(convertLiteraryWork.convert(pageRead.getLiteraryWorkPage()));
				}
				transcriptionListRead.add(transcriptionReadDTO);
			}
		}
		return transcriptionListRead;
	}
}
