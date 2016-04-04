package at.archkb.server.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	public MvcConfig() {

	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/index.html").addResourceLocations("/static/index.html");
		registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/");
		registry.addResourceHandler("/app/**").addResourceLocations("/static/app/");
	}
	


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }
    
	private MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate5Module to support lazy objects
        mapper.registerModule(new Hibernate5Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }
	
}
