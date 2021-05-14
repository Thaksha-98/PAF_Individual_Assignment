package com;
import model.Project;

import javax.servlet.http.Part;
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*;
import com.mysql.cj.jdbc.Blob;

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Project")
public class ProjectService {

	Project projectObj = new Project();
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readProjects() { 
		 return projectObj.readProjects(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertProject(@FormParam("title") String title, @FormParam("description") String description) { 
		String output = projectObj.insertProject(title, description); 
		return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateProject(String projectData) { 
		//Convert the input string to a JSON object 
		JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject();
	 
		//Read the values from the JSON object
		String projectID = projectObject.get("prId").getAsString(); 
		String title = projectObject.get("tit").getAsString(); 
		String description = projectObject.get("desc").getAsString(); 
	 
		String output = projectObj.updateProject(projectID, title, description); 
		return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteProject(String projectData) { 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(projectData, "", Parser.xmlParser()); 
	 
		//Read the value from the element <itemID>
		String projectID = doc.select("projectID").text(); 
		String output = projectObj.deleteProject(projectID); 
		return output; 
	}

}
