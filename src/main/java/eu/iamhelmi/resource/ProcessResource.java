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

@Path("process")
public class ProcessResource {

	@Inject
    Template process; 
	
	@GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name, @QueryParam("app") String app, @QueryParam("folder") String folder) {
		//System.out.println("resource process object: "+process);
		String searchOrNot = "";
		if (app!=null && app.length()>0) {
			searchOrNot = "*"+app+"*";
		}
        return process.data("now", java.time.LocalDateTime.now())
        		.data("process", OperatingSystemUtility.showProcessGrep(name))
        		.data("software", OperatingSystemUtility.runScript("apt list "+searchOrNot))
        		.data("name", name)
        		.data("app",app)
        		.data("folder", folder)
        		.data("list", OperatingSystemUtility.listOfFile(folder))
        		//.data("software", "disabled")
        		;  
    }
	
}
