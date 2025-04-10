package eu.iamhelmi.resource;

import org.jboss.resteasy.reactive.MultipartForm;

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

@Path("commandline")
public class CommandLineResource {

	@Inject
    Template commandline; 
	
	private String output = "";
	
	
	
	@GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name, @QueryParam("app") String app, @QueryParam("folder") String folder) {
		//System.out.println("resource process object: "+process);
		String searchOrNot = "";
		if (app!=null && app.length()>0) {
			searchOrNot = "*"+app+"*";
		}
        return commandline.data("now", java.time.LocalDateTime.now())
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

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	@Path("/execute")
    public TemplateInstance create(@MultipartForm CommandLineForm commandLineForm) {
        Log.info("Executing command line: "+ commandLineForm.convertToString());
        String output = OperatingSystemUtility.runScript(commandLineForm.convertToString());
        ;
        //this.output = output;
        //commandline.data("output", output);
        
        Log.info("Output: "+output+ " this.output: "+this.output);
        String formatted = output.replace(System.lineSeparator(), "<p/>");
        return commandline.instance().data("output", output);
    }
			
	
}
