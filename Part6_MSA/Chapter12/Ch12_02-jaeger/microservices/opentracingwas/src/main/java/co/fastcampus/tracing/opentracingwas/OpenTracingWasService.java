package co.fastcampus.tracing.opentracingwas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenTracingWasService {
	
	@Autowired
    private OpenTracingDBRepository repository;
	
	public String method() {
    	repository.findAll();
		return "Hello world";
	}
}
