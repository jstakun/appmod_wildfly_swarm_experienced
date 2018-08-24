package com.redhat.coolstore.inventory.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import com.redhat.coolstore.inventory.model.Inventory;
import com.redhat.coolstore.inventory.service.InventoryService;

@Path("/inventory")
@ApplicationScoped
public class InventoryResource {

	  @Inject
	  private InventoryService inventoryService;
	  
	  @Inject
	  @ConfigProperty(name = "env", defaultValue="unknown")
	 private String env;
		
	  @Inject
	  @Metric(name = "requestCount", description = "All JAX-RS request made to the SessionResource", displayName = "SessionResource#requestCount")
	  private Counter requestCount; 
	  
	    @GET
	    @Path("/{itemId}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Inventory getInventory(@PathParam("itemId") String itemId) {

	    	System.out.println("Searching for item " +  itemId + " in env " + env);
	    	
	    	requestCount.inc();
	    	
	        // get the inventory object from the inventoryService. Use the given itemId.
	    		Inventory inventory = inventoryService.getInventory(itemId);

	        // if the inventory object that you retrieved is null then
	        // throw a NotFoundException
	        // else return the inventory object
	    		
	    		if (inventory == null) {
	    			throw new NotFoundException("Item id not found: " + itemId + " in env " + env);
	    		} else {
	    			return inventory;
	    		}	
	    }
	    
	    @Timeout(1000)
	    @GET
	    @Path("/test")
	    @Produces(MediaType.APPLICATION_JSON)
	    public String testTimeout() {
	    	try {
	    	 Thread.sleep(2000);
	    	} catch (Exception e) {
	    	} 
	    	return "hello";
	    }
}