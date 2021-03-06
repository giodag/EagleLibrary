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
import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.model.LiteraryWork;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;
/**
 * Handler che gestisce tutta la logica computazionale dell'area dati che interessa le opere.
 *
 */
@Component
public class LiteraryWorkHanlder {

	private static final String PAGES = "Pages";
	private static final String TITLE = "Title";
	private static final String CATEGORY = "Category";
	private static final String AUTHOR = "Author";
	private static final String ALL = "All";

	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;

	@Autowired
	private TranscriptionRepository transcriptionRepository;

	@Autowired
	private ConvertLiteraryWork convertLiteraryWork;

	@Autowired
	private ConvertTranscription convertTranscription;

	private final Logger LOGGER = LoggerFactory.getLogger(LiteraryWorkHanlder.class);
	private static final String MISSED_PARAMETER = "Missed parameter : ";

	@Transactional
	public LiteraryWorkDTO readLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {

		LiteraryWork literaryWork = null;
		if (literaryWorkDTO.getId() != null) {
			literaryWork = literaryWorkRepository.findOne(literaryWorkDTO.getId());
		} else {
			literaryWork = literaryWorkRepository.findLiteraryWorkByFilter(literaryWorkDTO.getId(),
					literaryWorkDTO.getCategory(), literaryWorkDTO.getTitle(), literaryWorkDTO.getYear(),
					literaryWorkDTO.getAuthor());
		}

		return convertLiteraryWork.convert(literaryWork);
	}

	@Transactional
	public LiteraryWorkDTO readLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWorkDTO literaryWorkDTORead = readLiteraryWork(literaryWorkDTO);
		if (literaryWorkDTORead != null && literaryWorkDTORead.getPageList() != null
				&& !literaryWorkDTORead.getPageList().isEmpty()) {
			for (PageDTO pageDTO : literaryWorkDTORead.getPageList()) {
				Transcription transcription = transcriptionRepository.findTranscriptionByPage(pageDTO.getId());
				pageDTO.setTranscriptionDTO(convertTranscription.convert(transcription));
			}
		}
		return literaryWorkDTORead;
	}

	@Transactional
	public LiteraryWorkListDTO readLiteraryWorkList(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		LiteraryWorkListDTO literaryWorkListDTO = null;
		List<LiteraryWork> literaryWorksFiltered = null;
		if (literaryWorkListFilterDTO != null) {
			//--Filter by part of text
			if (StringUtils.isNotEmpty(literaryWorkListFilterDTO.getPartOfText())) {
				List<Transcription> transcriptionList = transcriptionRepository
						.findByTranscriptionLike(literaryWorkListFilterDTO.getPartOfText());
				if (transcriptionList != null && !transcriptionList.isEmpty()) {
					literaryWorksFiltered = new ArrayList<LiteraryWork>();
					for (Transcription transcription : transcriptionList) {
						if (transcription.getPage() != null && transcription.getPage().getLiteraryWorkPage() != null
								&& !literaryWorksFiltered.contains(transcription.getPage().getLiteraryWorkPage())) {
							literaryWorksFiltered.add(transcription.getPage().getLiteraryWorkPage());
						}
					}
				}
			} else {
				literaryWorksFiltered = literaryWorkRepository.findListLiteraryWorkByFilter(
						literaryWorkListFilterDTO.getIdList(), literaryWorkListFilterDTO.getCategory(),
						literaryWorkListFilterDTO.getTitle(), literaryWorkListFilterDTO.getYear(),
						literaryWorkListFilterDTO.getAuthor());
			}
			if (literaryWorksFiltered != null && !literaryWorksFiltered.isEmpty()) {
				literaryWorkListDTO = new LiteraryWorkListDTO();
				literaryWorkListDTO.setLiteraryWorkList(convertLiteraryWork.convert(literaryWorksFiltered));
			}
		}
		return literaryWorkListDTO;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) throws MandatoryFieldException {
		ResultDTO resultDTO = null;
		LiteraryWork literaryWork = null;
		LiteraryWorkDTO literaryWorkRead = readLiteraryWork(literaryWorkDTO);
		
		if(literaryWorkRead != null) {
			literaryWorkRead.setAuthor(literaryWorkDTO.getAuthor() != null ? literaryWorkDTO.getAuthor() : literaryWorkRead.getAuthor());
			literaryWorkRead.setCategory(literaryWorkDTO.getCategory() != null ? literaryWorkDTO.getCategory() : literaryWorkRead.getCategory());
			literaryWorkRead.setId(literaryWorkDTO.getId() != null ? literaryWorkDTO.getId() : literaryWorkRead.getId());
			literaryWorkRead.setPageList(literaryWorkDTO.getPageList() != null ? literaryWorkDTO.getPageList() : literaryWorkRead.getPageList());
			literaryWorkRead.setTitle(literaryWorkDTO.getTitle() != null ? literaryWorkDTO.getTitle() : literaryWorkRead.getTitle());
			literaryWorkRead.setYear(literaryWorkDTO.getYear() != null ? literaryWorkDTO.getYear() : literaryWorkRead.getYear());
			literaryWork = convertLiteraryWork.convert(literaryWorkRead);
		}else {
			literaryWork = convertLiteraryWork.convert(literaryWorkDTO);
			if (literaryWork != null && literaryWork.getPageList() != null) {
				for (Page page : literaryWork.getPageList()) {
					page.setLiteraryWorkPage(literaryWork);
				}
			}
		}
		checkMandatory(literaryWork);
		LiteraryWork result = literaryWorkRepository.save(literaryWork);
		if (result != null) {
			resultDTO = new ResultDTO();
			resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		}
		return resultDTO;
	}

	private void checkMandatory(LiteraryWork literaryWork) throws MandatoryFieldException {
		if(literaryWork == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, ALL);
		}else if(literaryWork.getAuthor() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, AUTHOR);
		}else if(literaryWork.getCategory() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, CATEGORY);
		}else if(literaryWork.getTitle() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, TITLE);
		}else if(literaryWork.getPageList() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, PAGES);
		}
	}

}
