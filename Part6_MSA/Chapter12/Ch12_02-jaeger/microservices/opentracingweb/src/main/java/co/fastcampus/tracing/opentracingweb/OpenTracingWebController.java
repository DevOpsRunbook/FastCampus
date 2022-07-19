package co.fastcampus.tracing.opentracingweb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;

@RestController
public class OpenTracingWebController {
    
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test-tracing")
    public String entryPointController() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	String response=restTemplate.exchange("http://opentracingwas.trace:8081/service", HttpMethod.GET, entity, String.class).getBody();
    	
    	
        Tracer tracer = GlobalTracer.get();
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("CustomSpan")
                .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER);
 
        Span span = spanBuilder.start();
        Tags.COMPONENT.set(span, "OpenTracingWebController");
        span.setTag("testtag", "test");
        span.finish();
    	
        return "Remote server said: "+response;
    }
}