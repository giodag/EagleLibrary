package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;

public interface LiteraryWork {
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO);
	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO);
	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO);
	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO);
}
