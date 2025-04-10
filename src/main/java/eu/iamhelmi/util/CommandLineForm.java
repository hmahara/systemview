package eu.iamhelmi.util;

import org.jboss.resteasy.reactive.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public class CommandLineForm {

	public @FormParam("commandline") @PartType(MediaType.TEXT_PLAIN) String commandline;
    

    public String convertToString() {
        
        return commandline;
    }

}
