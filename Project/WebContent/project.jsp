<%@page import="model.Project"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Project.js"></script>

<style>
.avatar {
  vertical-align: middle;
  width: 65px;
  height: 50px;
  border-radius: 50%;
  }
.tbs{
    width: 100%;
    height:350px;
  	overflow: auto; 
  }
</style>
</head>
<body>

<div class="container">
	<div class="row">
 		<div class="col">
 		<br><br><br><br><br>
 		
		<h1>Project Management</h1><br>

		<form id='formProject' method='post' action='project.jsp'>
		
			Title : 
			<input id='title' name='title' type='text' class='form-control'><br>
			
			Description : 
			<textarea id='description' name='description' type='text' class='form-control' row='10'></textarea><br>
			
			<input id='btnSubmit' name='btnSubmit' type='button' value='Save' class='btn btn-primary'>
			<input type='hidden' id='hidProjectIDSave' name='hidProjectIDSave' value=''> 
		</form>
		
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
	<br>
	
	<div id="divProjectGrid" class="tbs">
	<%
 		Project projectObj = new Project();
 		out.print(projectObj.readProjects());
	%>
	</div>
		</div>
	</div>
</div>
</body>
</html>