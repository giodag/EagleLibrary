package com.univaq.eaglelibrary.hanlder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.univaq.eaglelibrary.converter.ConvertPages;
import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.model.LiteraryWork;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;
import com.univaq.eaglelibrary.repository.PageRepository;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;
/**
 * Handler che gestisce tutta la logica computazionale dell'area dati che interessa pagine di un'opera.
 *
 */
@Component
public class PageHandler {

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;
	
	@Autowired
	private TranscriptionRepository transcriptionRepository;

	@Autowired
	private ConvertPages convertPages;
	
	@Autowired
	private ConvertTranscription convertTranscription;

	public PageDTO createUpdatePage(PageDTO pageDTO) {
		Page pageAlreadyExist = null;
		if (pageDTO != null && pageDTO.getIdLiteraryWork() != null) {
			pageAlreadyExist = readPageEntity(pageDTO);
			if (pageAlreadyExist == null) {
				pageAlreadyExist = new Page();
			}
			pageAlreadyExist.setChapter(pageDTO.getChapter() != null ? pageDTO.getChapter() : pageAlreadyExist.getChapter());
			pageAlreadyExist.setPageNumber(pageDTO.getPageNumber() != null ? pageDTO.getPageNumber() : pageAlreadyExist.getPageNumber());
			pageAlreadyExist.setId(pageDTO.getId() != null ? pageDTO.getId() : pageAlreadyExist.getId());
			pageAlreadyExist.setTranscription((pageDTO.getTranscriptionDTO() != null ? convertTranscription.convert(pageDTO.getTranscriptionDTO())
					: pageAlreadyExist.getTranscription()));

			LiteraryWork literaryWork = literaryWorkRepository.findOne(pageDTO.getIdLiteraryWork());
			pageAlreadyExist.setLiteraryWorkPage(literaryWork);
			pageAlreadyExist = pageRepository.save(pageAlreadyExist);
			pageDTO = convertPages.convert(pageAlreadyExist);
		}
		return pageDTO;
	}

	private Page readPageEntity(PageDTO pageDTO) {
		Page page = null;
		if (pageDTO != null) {
			if (pageDTO.getId() != null) {
				page = pageRepository.findOne(pageDTO.getId());
			} else {
				page = pageRepository.finaPageByFilter(pageDTO.getPageNumber(), pageDTO.getChapter(), pageDTO.getId(),
						pageDTO.getIdLiteraryWork());
			}
		}
		return page;
	}

	@Transactional
	public PageDTO readPage(PageDTO pageDTO) {
		if (pageDTO != null) {
				pageDTO = convertPages.convert(readPageEntity(pageDTO));
			}
		return pageDTO;
	}

	public void deletePage(PageDTO pageDTO) {
		if (pageDTO != null) {
			Page page = readPageEntity(pageDTO);
			if (page != null) {
				pageRepository.delete(page);
			}
		}
	}
}
