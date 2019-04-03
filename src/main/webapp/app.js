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
				console.log(xhr.getAllResponseHeaders());
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
				// console.log("ready state");


				$('#firstname').html(user.firstname);
				$('#lastname').html(user.lastname);
				$('#role').html(user.role);
				$('#authorfirst').val(user.firstname);
				$('#authorlast').val(user.lastname);
				$('#request').on('load', stageUser(user));
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

function storeUser(user) {

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
		resolver_id : 2, // default resolver, overwritten when resolved
		status_id : 1, // pending
		type_id: selectedType
	};
	var reimbObj = JSON.stringify(reimbursement);
	console.log("Reimbursement Object: " + reimbObj);
	
	//stage for user in overwriting parameters and submitting to DB
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
 * Function to retrieve staged user on click and send HTTP POST request
 */
function sendReimb() {
	var selectedType = $('#type').children("option:selected").val();
	var reimbdesc = $('#description').val();
	var reimbamount = $('#amount').val();
	console.log("AMOUNT TO SAVE: " + amount);
	
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
	
	//JSON prepared for DB
	reimbRequest = JSON.stringify(reimbParse);
	console.log("OUT OF JSON: " + reimbRequest);
	

	if (isValidJson(reimbRequest)){

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