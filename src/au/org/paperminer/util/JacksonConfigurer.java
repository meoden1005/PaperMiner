package au.org.paperminer.util;

//import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
 
@Component
public class JacksonConfigurer {
    private AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter;
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
   
    @Autowired
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
		return requestMappingHandlerAdapter;
	}

    @Autowired
	public void setRequestMappingHandlerAdapter(
			RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
	}

	private RunningAsRootObjectMapper objectMapper;
 
    @PostConstruct
    public void init() {
    	
        //List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        HttpMessageConverter<?>[] messageConverters = annotationMethodHandlerAdapter.getMessageConverters();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
                m.setObjectMapper(objectMapper);
            }
        }
    }
 
    @Autowired
    public void setAnnotationMethodHandlerAdapter(AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter) {
        this.annotationMethodHandlerAdapter  = annotationMethodHandlerAdapter;
    }
 
    @Autowired
    public void setObjectMapper(RunningAsRootObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}