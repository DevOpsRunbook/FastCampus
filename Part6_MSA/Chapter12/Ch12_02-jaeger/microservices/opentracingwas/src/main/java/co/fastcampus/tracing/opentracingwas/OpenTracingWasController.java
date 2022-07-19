package co.fastcampus.tracing.opentracingwas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenTracingWasController {
	
	@Autowired 
	private OpenTracingWasService service;
	
    @GetMapping("service")
    public String helloControllerMethod() {
        return service.method();
    }
}