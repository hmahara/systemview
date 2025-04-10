package eu.iamhelmi.resource;

import org.jboss.resteasy.reactive.MultipartForm;

import eu.iamhelmi.service.JaxRsService;
import eu.iamhelmi.util.CommandLineForm;
import eu.iamhelmi.util.OperatingSystemUtility;
import io.quarkus.logging.Log;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("restclient")
public class RestClientResource {

	@Inject
    Template restclient; 
	
	private String output = "";
	
	@Inject
	JaxRsService jaxRsService;
	
	
	
	@GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("url") String url) {
		if (url != null) {
			output = jaxRsService.get(url);
		}
        return restclient.data("now", java.time.LocalDateTime.now())
        		.data("output", output)
        		//.data("process", OperatingSystemUtility.showProcessGrep(name))
        		//.data("software", OperatingSystemUtility.runScript("apt list "+searchOrNot))
        		//.data("name", name)
        		//.data("app",app)
        		//.data("folder", folder)
        		//.data("list", OperatingSystemUtility.listOfFile(folder))
        		//.data("software", "disabled")
        		;  
        
    }
			
	
}
