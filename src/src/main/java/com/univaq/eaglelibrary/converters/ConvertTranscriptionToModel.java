package com.univaq.eaglelibrary.converters;

import java.util.List;

import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Transcription;

@Component
public class ConvertTranscriptionToModel {

	public Transcription convert(TranscriptionDTO transcriptionDTO) {
		return null;
		
	}
	
	public List<Transcription> convert(List<TranscriptionDTO> transcriptionDTOs) {
		return null;
	}
}
