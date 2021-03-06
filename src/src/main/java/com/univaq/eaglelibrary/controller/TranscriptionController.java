package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
/**
 * Controller per le trascrizione, funge da canale tra la parte view e la parte che fa logica computazionale.
 */
public interface TranscriptionController {
	
	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException;
	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO);
	public AssignTranscriptionResponseDTO assignTrascription(AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) throws MandatoryFieldException;
	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO);
	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO);
	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException;
	public TranscriptionDTO saveTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException;
	public UnassignTranscriptionResponseDTO unassignTranscription(UnassignTranscriptionRequestDTO unassignTranscriptionRequestDTO) throws MandatoryFieldException;
	public LockTranscriptionResponseDTO unlockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO);
	
}
