var viewCounter = 1;

/*
 * Intialize our partial landingview 
 */
window.onload = function() {
	console.log("app loading...");
	loadLandingView();

}

/*
 * Use AJAX to send a request to LoadViewServlet, which will return our landing
 * page partial html
 * 
 * Then, use response (the html partial) as the inner HTML of our view.
 * 
 */
function loadLandingView() {
	var xhr = new XMLHttpRequest(); // AJAX caps breaking all the rules
	xhr.onreadystatechange = function() {
		// manipulate response whenever we get it
		if (xhr.readyState == 4) { // success
			console.log('response received');
			if (xhr.status == 200) {

				$('#view').html(xhr.responseText);

				// ADD EVENT LISTENERS TO HTML

				// changed view
				$('#view').html(xhr.responseText);

				// do things with that view
				$('#login').on('click', loginUser);

			}
			if (xhr.status > 399) {
				// some sort of error
			}
		}
	}

	xhr.open("GET", "landing.view");
	console.log("Landing XHR open"); // landing.html as specified in our
	// doGet in
	xhr.send(); // sent to index.html where our app.js is called

}

/*
 * Function to attempt logging in the individual with the given credentials.
 */
function loginUser() {
	var name = $('#username').val();
	var pw = $('#password').val();

	if (validateString(name) || validateString(password)) {
		// non-null string or password
		var user = {
			username : name,
			password : pw
		};

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			// get response body and console.login
			if (xhr.readyState == 4 && xhr.status == 200) {
				var user = JSON.parse(xhr.responseText); // turn user JSON to
				// object
				console.log("LOGIN RESPONSE HEADERS"
						+ xhr.getAllResponseHeaders());
				if (user == null) {
					// not logged in -- inform about invalid credentials
					$('#message').html('Sorry! Invalid credentials');
				} else {
					// logged in successfully
					console.log("User generated from LogIn");
					loadEmployeeView(user);

				}
			}
		}

		xhr.open("POST", "login"); // request to Login Servlet
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(JSON.stringify(user));
	} else {
		// user entered null or empty string; avoid sending empty data server
		$('#message').html('Please enter valid username and password!');

	}
}

/*
 * Validate if the input credentials are a string or not
 */
function validateString(str) {
	if (str == null || str == '') { // an invalid string
		return false;
	} else {
		return true;
	}
}

/*
 * Generate Employee View upon successful log-in when an employee.
 */
function loadEmployeeView(user) {
	var xhr = new XMLHttpRequest();
	console.log("in loadEmployeeView function");
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {

			if (xhr.status == 200) {
				$('#view').html(xhr.responseText);

				// Binding the below to the html loaded through the response
				// text

				$('#firstname').html(user.firstname);
				$('#lastname').html(user.lastname);
				$('#role').html(user.role);
				$('#authorfirst').val(user.firstname);
				$('#authorlast').val(user.lastname);
				$('#request').on('load', stageUser(user));

				loadUserTable(user);
			} else if (xhr.status == 404) {
				console.error(xhr.responseText);
			}
		}
	}
	if (user.role == "Employee") {
		xhr.open("GET", "employee.view");
		xhr.send();
	} else if (user.role == "Manager") {
		xhr.open("GET", "manager.view");
		xhr.send();
	} else {
		xhr.open("GET", "landing.view");
		xhr.send();
	}
}

/*
 * Gather field data, format into Reimbursement request, add request to DB
 */
function stageUser(user) {
	console.log("In SubmitRequest");
	var id = user.id;
	console.log("USER ID: " + id);
	var selectedType = $('#type').children("option:selected").val();
	var authorfirst = $('#authorfirst').val();
	var authorlast = $('#authorlast').val();
	var reimbamount = $('#amount').val();
	var reimbdesc = $('#description').val();

	// cast reimbursement type to DB foreign key relationship
	if (selectedType == "Lodging") {
		selectedType = 1;
	} else if (selectedType == "Travel") {
		selectedType = 2;
	} else if (selectedType == "Food") {
		selectedType = 3;
	} else {
		selectedType = 4;
	}

	// create object to be added to DB
	var reimbursement = {
		amount : reimbamount,
		description : reimbdesc,
		author_id : id,
		resolver_id : 50, // default resolver, overwritten when resolved
		status_id : 1, // pending
		type_id : selectedType
	};
	var reimbObj = JSON.stringify(reimbursement);
	console.log("Reimbursement Object: " + reimbObj);

	// stage for user in overwriting parameters and submitting to DB
	sessionStorage.setItem('userReimb', reimbObj);
}

/*
 * Validate JSON
 */
function isValidJson(json) {
	try {
		JSON.parse(json);
		return true;
	} catch (e) {
		return false;
	}
}

/*
 * Function to retrieve staged reimbursement request on click and send HTTP POST
 * request
 */
function sendReimb() {
	var selectedType = $('#type').children("option:selected").val();
	var reimbdesc = $('#description').val();
	var reimbamount = $('#amount').val();
	// console.log("AMOUNT TO SAVE: " + amount);

	// cast reimbursement type to DB foreign key relationship
	if (selectedType == "Lodging") {
		selectedType = 1;
	} else if (selectedType == "Travel") {
		selectedType = 2;
	} else if (selectedType == "Food") {
		selectedType = 3;
	} else {
		selectedType = 4;
	}

	var reimbRequest = sessionStorage.getItem('userReimb');
	var reimbParse = JSON.parse(reimbRequest);

	reimbParse.type_id = selectedType;
	reimbParse.amount = reimbamount;
	reimbParse.description = reimbdesc;

	// JSON prepared for DB
	reimbRequest = JSON.stringify(reimbParse);
	console.log("OUT OF JSON: " + reimbRequest);

	if (isValidJson(reimbRequest)) {

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			// get response body and console.login
			if (xhr.readyState == 4 && xhr.status == 200) {
			}
		}
		xhr.open("POST", "sendreimb"); // request to SendReimb Servlet
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(reimbRequest);
	} else {
		$('#message').html('Please enter valid form data');
	}
}

function loadUserTable(user) {
	JSONUser = JSON.stringify(user);
	let parseUser = JSON.parse(JSONUser);
	console.log("TESTUSER: " + JSONUser);
	let userRole = user.role;
	let userID = user.id;
	console.log("LOADING TABLE FOR USER ID: " + userID);

	if (userRole == "Employee") {
		if (isValidJson(JSONUser)) {
			console.log("IN EMPLOYEE");
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {

					var parseReimbs = JSON.parse(xhr.responseText);
					//console.log("TABLE REIMB AMOUNT: " + parseReimbs[1].amount);
					if(viewCounter==1){
						drawTable(parseReimbs, userRole);
						++viewCounter;
					}

					// Send Request / Validate Form / Reload Table Element
					$('#request').on('click', function() {
						if(formValid() == true){
							sendReimb();
							$('#request').hide();
							$("#userTable").empty();
							setTimeout(loadUserTable(user), 1000);
						}
					});
					$('#logout').on('click', function() {
						logOut();
					});
					$('#refresh').on('click', function() {
						$("#userTable").empty();
						viewCounter=1; 
						setTimeout(loadUserTable(user), 1000);
					});
					
					
				}
			}
			xhr.open("POST", "empreimb"); // request to SendReimb Servlet
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.send(JSONUser);
		} else {
			console.log("INVALID USER type")
			return null;
		}

	} else {
		console.log("IN MANAGER");
		if (isValidJson(JSONUser)) {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {

					var parseReimbs = JSON.parse(xhr.responseText);
					console.log("TABLE REIMB ID: " + parseReimbs[1].id);
					if(viewCounter==1){
						drawTable(parseReimbs, userRole);
						++viewCounter;
					}
					$('button[name=process]').on('click', function() {
						let buttonReimbID = $(this).attr('id'); // Corresponds
																// to User ID
																// for that
																// field
						let buttonType = $(this).attr('buttonType');
						processReimb(buttonReimbID, buttonType);
						$("#userTable").empty();
						setTimeout(loadUserTable(user), 1000);
					});
					$('#logout').on('click', function() {
						logOut();
					});
					$('#refresh').on('click', function() {
						$("#userTable").empty();
						viewCounter=1; 
						setTimeout(loadUserTable(user), 1000);
					});

				}
			}
			xhr.open("POST", "empreimb"); // request to SendReimb Servlet
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.send(JSONUser);
		} else {
			console.log("INVALID USER type")
			return null;
		}

	}

}

/*
 * Process Reimbursement Request by User
 */
function processReimb(buttonReimbID, buttonType) {
	// grouping necessary data as string for servlet
	let requestBody = buttonReimbID + buttonType
	console.log(requestBody);

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
		}
	}
	xhr.open("POST", "processreimb"); // request to SendReimb Servlet
	xhr.setRequestHeader("Content-type", "text-plain");
	xhr.send(requestBody);
}

/*
 * Send a request to log out the user and redirect back to the landing view
 */
function logOut() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {
			console.log('Log Out response received');
			if (xhr.status == 200) {

				$('#view').html(xhr.responseText);

				$('#login').on('click', loginUser);

			}
			if (xhr.status > 399) {
				// some sort of error
			}
		}
	}

	xhr.open("GET", "landing.view");
	console.log("Landing XHR open");

	xhr.send();
}

/*
 * Form Validation for submit request
 */
function formValid() {
	var num = Number(document.getElementById("amount").value);
	console.log("INPUT NUM IS: " + num + " TYPE: " + typeof num);

	if (num > 10 && typeof (num) == 'number' && num < 10001) {
		console.log("VALID AMOUNT");
		return true;
	} else {
		document.getElementById("message").innerHTML = "Amount is Invalid: Number Between $10 and $10000";
		return false;
	}
}

/*
 * Pair functions to generate Reimbursement Tables from JSON
 */
function drawTable(data, userRole) {
	console.log("DRAWING TABLE");
	if (userRole == "Employee") {
		drawEmpTableHeader();
		for (var i = 0; i < data.length; i++) {
			console.log("DATA[i]: " + data[i]);
			drawEmployeeRow(data[i]);
		}
	} else {
		drawManagerTableHeader();
		for (var i = 0; i < data.length; i++) {
			console.log("DATA[i]: " + data[i]);
			drawManagerRow(data[i]);
		}
	}
}
function drawEmpTableHeader() {
	var header = $("<thead class='thead-dark'><tr>"
			+ "<td><b>Author</b></td><td><b>Type</b></td><td><b>Description</b></td>"
			+ "<td><b>Amount</b></td><td><b>Resolver</b></td><td><b>Sbumitted Time</b></td>"
			+ "<td><b>Status</b></td>" + "</tr></thead>")
	$("#userTable").append(header);
}

function drawManagerTableHeader() {
	var header = $("<thead class='thead-dark'><tr>"
			+ "<td><b>Author</b></td><td><b>Type</b></td><td><b>Description</b></td>"
			+ "<td><b>Amount</b></td><td><b>Resolver</b></td><td><b>Status</b></td>"
			+ "<td><b>Resolved Time</b></td>" + "<td><b>Selection</b></td>"
			+ "</tr></thead>")
	$("#userTable").append(header);
}

function drawEmployeeRow(rowData) {
	var row = $("<tr />")
	$("#userTable").append(row); // this will append tr element to table...
	// keep its reference for a while since we
	// will add cels into it
	row.append($("<td>" + rowData.author + "</td>"));
	row.append($("<td>" + rowData.type + "</td>"));
	row.append($("<td>" + rowData.description + "</td>"));
	row.append($("<td>" + rowData.amount + "</td>"));
	row.append($("<td>" + rowData.resolver + "</td>"));
	row.append($("<td>" + rowData.submittedTime + "</td>"));
	row.append($("<td>" + rowData.status + "</td>"));
}

function drawManagerRow(rowData) {
	// console.log("MANAGER TABLE DRAW");
	var row = $("<tr />")
	$("#userTable").append(row); // this will append tr element to table...
	// keep its reference for a while since we
	// will add cells into it
	row.append($("<td>" + rowData.author + "</td>"));
	row.append($("<td>" + rowData.type + "</td>"));
	row.append($("<td>" + rowData.description + "</td>"));
	row.append($("<td>" + rowData.amount + "</td>"));
	row.append($("<td>" + rowData.resolver + "</td>"));
	row.append($("<td>" + rowData.status + "</td>"));
	row.append($("<td>" + rowData.resolvedTime + "</td>"));

	row.append($("<td>"
					+ "<button class='btn btn-success' name = 'process' id='"
					+ rowData.id
					+ "' buttonType = 'approve' onclick='this.disabled=true;'>Approve</button>"
					+ "<button class='btn btn-danger' name = 'process' id='"
					+ rowData.id
					+ "' buttonType = 'deny' onclick='this.disabled=true;'>Deny</button></td>"));
}
