package eu.iamhelmi.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;

@ApplicationScoped
public class JaxRsService {

	private  Client client;
    private  WebTarget target;
    //private  String url;

    public JaxRsService() {
    	//this.url = url;
        
    }
    
  
    public String get(String url) {
    	this.client = ClientBuilder.newClient();
        this.target = client.target(url);
        return target
          .request()
          .get(new GenericType<String>() {});
    }
}
