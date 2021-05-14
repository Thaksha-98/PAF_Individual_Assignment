package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

import javax.servlet.http.Part;

public class Project {

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project","root", "");
			
			//for testing
			System.out.print("Successfully connected"); 
		}catch(Exception e) {
			 e.printStackTrace(); 
		}
		//System.out.print("done");
		return con;	
	}
	
	public String insertProject(String title, String description) {
		String output = "";


		try {
			Connection con = connect();

			if (con == null)
			{
				return "Error while connecting to the database";
			}
		
			// create a prepared statement
			String query = " insert into projects(`projectID`,`title`,`description`)" + " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, title);
			preparedStmt.setString(3, description);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newProjects = readProjects(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
					newProjects + "\"}"; 
			
		}catch(Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			 System.err.println(e.getMessage());
		} 
		return output;
		
	}
	
	public String readProjects(){
		String result = "";
		
		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
				
			result = "<br><table class='table table-striped table-dark'><thead><tr>"
					+ "<th scope='col'>Project Title</th><th scope='col'>Description</th>"
					+ "<th scope='col'>Update</th><th scope='col'>Remove</th></tr></thead>";
				
				String query = "select * from projects";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				result += "<tbody>";
				while (rs.next()){
					String projectID = Integer.toString(rs.getInt("projectID"));
					String title = rs.getString("title");
					String description = rs.getString("description");

					// Add a row into the html table
					result += "<tr><td>" + title + "</td>";
					result += "<td>" + description + "</td>";
					
					// buttons
					result +="<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-projectid='" + projectID + "'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btn btn-danger' data-proid='" + projectID + "'>" + "</td></tr>"; 
				}
				result +="</tbody>";

				con.close();
				// Complete the html table
				result += "</table>";

			
		}catch (Exception e){
			result = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	public String getProject(String projectID) {
		String result = "";		
		
		try {
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
			
			String query = " select * from projects where projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.parseInt(projectID));
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				result = "<form method='post' action='project.jsp'>"
						+ "Title : <input name='tit' type='text' value = '"+rs.getString("title")+"' class='form-control'><br>"
						+ "Description : <input name='desc' type='text' value = '"+rs.getString("description")+"' class='form-control' row='10'><br>"
						+ "<form method='post' action='project.jsp'>"
						+ "<input name='btnUpdate' type='submit' value='Update' class='btn btn-info'>"
						+ "<input name='prId' type='hidden' " + " value='" + projectID + "'>" + "</form><br>";

			}
			
			con.close();
		}catch(Exception e) {
			result = "Error while geting the projects.";
			System.err.println(e.getMessage());
		}
		return result;		
	}
	
	public String updateProject(String projectID, String title, String description) {
		String result = "";
		
		try {
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
			
			String query = " update projects set title=?, description=? where projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setString(1, title);
			preparedStmt.setString(2, description);
			preparedStmt.setInt(3, Integer.parseInt(projectID));
			
			preparedStmt.execute();
			con.close();
			
			String newProjects = readProjects(); 
			result = "{\"status\":\"success\", \"data\": \"" + 
					newProjects + "\"}"; 
			
		}catch(Exception e) {
			result = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}"; 
			System.err.println(e.getMessage());
		}		
		
		return result;
	}
	
	public String deleteProject(String ID) {
		String result = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			String query = " delete from projects where projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.parseInt(ID));
			preparedStmt.execute();
			
			con.close();
			
			String newProjects = readProjects(); 
			result = "{\"status\":\"success\", \"data\": \"" + 
					newProjects + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			result = "{\"status\":\"error\", \"data\": \"Error while deleting the project.\"}";
			System.err.println(e.getMessage());
		}
		
		return result;	
	}
}
