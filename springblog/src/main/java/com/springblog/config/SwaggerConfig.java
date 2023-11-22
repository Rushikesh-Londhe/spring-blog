package com.springblog.config;



import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	public static final String AUTHORIZATION_HEARDER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEARDER, "header");
	}
	
	/*
	 * private List<SecurityContext> securityContext(){ return
	 * Arrays.asList(SecurityContext.builder().securityReferences(sf()).build()); }
	 * 
	 * private List<SecurityReference> sf(){ AuthorizationScope scope=new
	 * AuthorizationScope("globsl","access Everything"); return Arrays.asList(new
	 * SecurityReference("JWT",new AuthorizationScope[] {scope})); }
	 */
	@Bean
	public Docket api() {
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				//.securityContexts(securityContext())
				//.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
				.build();
						
					
	}

	private ApiInfo getInfo() {
		
		/*
		 * return new
		 * ApiInfo("Blog Api","this project is developed using sprig boot","5.7",
		 * "terms of service",new
		 * Contact("Rushi","rushi@123","rushikeshlondhe3385@gmail.com"),
		 * "Licence of apis","API_LICENCE", Map<String, Object> vendorExtensions =
		 * Map.of( "x-vendor-info", "This is a custom vendor extension",
		 * "x-custom-meta", "Additional metadata goes here" ));
		 */
		return new ApiInfo("Blogging Application : Backend Course",
				"This project is developed by Rushi", "1.0", "Terms of Service",
				"License of APIS", "API license URL","Collections.emptyList()");
	}
}
