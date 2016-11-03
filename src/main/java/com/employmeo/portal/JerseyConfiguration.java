package com.employmeo.portal;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.employmeo.portal.resources.ChangePassword;
import com.employmeo.portal.resources.FortgotPassword;
import com.employmeo.portal.resources.GetAssessmentList;
import com.employmeo.portal.resources.GetLastTenRespondants;
import com.employmeo.portal.resources.GetLocationList;
import com.employmeo.portal.resources.GetPositionList;
import com.employmeo.portal.resources.GetRespondants;
import com.employmeo.portal.resources.InviteApplicant;
import com.employmeo.portal.resources.Login;
import com.employmeo.portal.resources.Logout;
import com.employmeo.portal.resources.UpdateAccount;
import com.employmeo.portal.resources.UpdateDash;
import com.employmeo.portal.resources.AccountResource;
import com.employmeo.portal.resources.CorefactorResource;
import com.employmeo.portal.resources.PartnerResource;
import com.employmeo.portal.resources.PersonResource;
import com.employmeo.portal.resources.PredictionConfigurationResource;
import com.employmeo.portal.resources.QuestionResource;
import com.employmeo.portal.resources.RespondantResource;
import com.employmeo.portal.resources.SurveyResource;
import com.employmeo.portal.resources.UserResource;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;


@Component
@ApplicationPath("${spring.jersey.application-path:/portal}")
public class JerseyConfiguration extends ResourceConfig {
	private static final Logger log = LoggerFactory.getLogger(JerseyConfiguration.class);

	 @Value("${spring.jersey.application-path:/portal}")
	 private String apiPath;
	 
	public JerseyConfiguration() {
        registerEndpoints();
        register(RolesAllowedDynamicFeature.class);
	}
	
	@PostConstruct
    public void init() {
		log.info("Initializing swagger");
        configureSwagger();
        log.info("Swagger initialized");
    }	

	private void registerEndpoints() {
		
//need redesign resources
		register(ChangePassword.class);
		register(FortgotPassword.class);
		register(GetAssessmentList.class);
		register(GetLastTenRespondants.class);
		register(GetLocationList.class);
		register(GetPositionList.class);
		register(GetRespondants.class);
		register(InviteApplicant.class);
		register(Login.class);
		register(Logout.class);
		register(UpdateAccount.class);
		register(UpdateDash.class);

// properly designed resources		
		register(AccountResource.class);
		register(CorefactorResource.class);
		register(PartnerResource.class);
		register(UserResource.class);
		register(PredictionConfigurationResource.class);
		register(PersonResource.class);
		register(QuestionResource.class);
		register(RespondantResource.class);
		register(SurveyResource.class);
		
		register(WadlResource.class);
	}

	
	private void configureSwagger() {
	     this.register(ApiListingResource.class);
	     this.register(SwaggerSerializers.class);

	     BeanConfig config = new BeanConfig();
	     config.setConfigId("com.employmeo.portal");
	     config.setTitle("Talytica Client Portal APIs");
	     config.setVersion("v1");
	     config.setContact("info@talytica.com");
	     config.setSchemes(new String[] { "http", "https" });
	     config.setBasePath(apiPath);
	     config.setResourcePackage("com.employmeo.portal.resources");
	     config.setPrettyPrint(true);
	     config.setScan(true);
	   }
}
