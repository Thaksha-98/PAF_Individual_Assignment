$(document).ready(function()
{ 
	if ($("#alertSuccess").text().trim() == "") 
	{ 
		$("#alertSuccess").hide(); 
	} 
	$("#alertError").hide(); 
});

$(document).on("click", "#btnSubmit", function(event) 
{ 
		// Clear alerts---------------------
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide(); 6
			
	// Form validation-------------------
	var status = validateProjectForm(); 
	if (status != true) 
	{ 
		$("#alertError").text(status); 
		$("#alertError").show(); 
		return; 
	} 
				
	// If valid------------------------
	var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT"; 
	$.ajax( 
	{ 
		url : "ProjectAPI", 
		type : type, 
		data : $("#formProject").serialize(), 
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onProjectSaveComplete(response.responseText, status); 
		} 
	}); 
});

//CLIENT-MODEL================================================================
function validateProjectForm() 
{ 
	if ($("#title").val().trim() == "") 
	{ 
		return "Insert Project title."; 
	} 
	
	if ($("#description").val().trim() == "") 
	{ 
		return "Insert Project Description."; 
	} 

	return true; 
}

function onProjectSaveComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully saved."); 
			$("#alertSuccess").show(); 
			$("#divProjectGrid").html(resultSet.data); 
		} 
		else if (resultSet.status.trim() == "error") 
		{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} 
	else if (status == "error") 
	{ 
		$("#alertError").text("Error while saving."); 
		$("#alertError").show(); 
	} 
	else
	{ 
		$("#alertError").text("Unknown error while saving.."); 
		$("#alertError").show(); 
	}
	
	$("#hidProjectIDSave").val(""); 
	$("#formProject")[0].reset(); 
}

$(document).on("click", ".btnUpdate", function(event) 
		{ 
		 $("#hidProjectIDSave").val($(this).data("projectid")); 
		 $("#title").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#description").val($(this).closest("tr").find('td:eq(1)').text()); 
 
});

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		url : "ProjectAPI", 
		type : "DELETE", 
		data : "projectID=" + $(this).data("proid"),
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onProjectDeleteComplete(response.responseText, status); 
		} 
	}); 
});

function onProjectDeleteComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully deleted."); 
			$("#alertSuccess").show(); 
			$("#divProjectGrid").html(resultSet.data); 
		} 
		else if (resultSet.status.trim() == "error") 
		{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} 
	else if (status == "error") 
	{ 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	}
	else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	}
}




