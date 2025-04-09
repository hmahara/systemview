package eu.iamhelmi.resource;

import eu.iamhelmi.util.OperatingSystemUtility;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/interservice")
public class InterserviceResource {

	@Path("/version")
	@GET 
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@QueryParam("name") String name, @QueryParam("app") String app, @QueryParam("folder") String folder) {
		//System.out.println("resource process object: "+process);
		String searchOrNot = "";
		if (app!=null && app.length()>0) {
			searchOrNot = "*"+app+"*";
		}
		//return OperatingSystemUtility.getDebianSoftareInstalled("google");
        return "{\"version\": \"2.0.14\"}";  
    }
	
}
