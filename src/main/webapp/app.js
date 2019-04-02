/*
 * Intialize our partial landingview 
 */
window.onload = function(){
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
function loadLandingView(){
	var xhr = new XMLHttpRequest();       // AJAX caps breaking all the rules
	xhr.onreadystatechange = function(){
		// manipulate response whenever we get it
		if(xhr.readyState==4){ // success
			console.log('response received');
			if(xhr.status ==200){
				// success; we know our response will be html
				$('#view').html(xhr.responseText); // get element of id view
													// and populate with
													// responseText
				// ADD EVENT LISTENERS TO HTML //responseText is the html that
				// we got back from our servlet
			
				// changed view
				$('#view').html(xhr.responseText);
				
				// do things with that view
				$('#login').on('click', loginUser); // don't put parenth in
													// event listener - call
													// when invoked
			}										
			if(xhr.status >399){
				// some sort of error
			}
		}
	}
	
	xhr.open("GET", "landing.view"); // loadview is our servlet for our
										// partial
									// landing.html as specified in our doGet in
									// servlet
									// changed to landing.vew by generalizing
									// servlet
	xhr.send(); 	// sent to index.html where our app.js is called
	
}

function loginUser(){
	var name = $('#username').val();
	var pw = $('#password').val();
	
	if(validateString(name) || validateString(password)){
		//non-null string or password
	var user = {
			username : name,
			password :pw
	};
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		// get response body and console.login
		if(xhr.readyState==4 && xhr.status == 200){
			var user = JSON.parse(xhr.responseText); // turn user JSON to
														// object
			console.log(xhr.getAllResponseHeaders());
			if(user==null){
				// not logged in -- inform about invalid credentials
				$('#message').html('Sorry! Invalid credentials');
			}else{
				// logged in successfully
				console.log(user);
				//loadHomeView(user);
				
			}
		}
	}
	
	xhr.open("POST", "login"); // request to log-in servlet
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(user));
	}else{
		// user entered null or empty string; avoid sending empty data server
		$('#message').html('Please enter valid username and password!');
		
	}
}

function validateString(str){
	if(str == null || str == ''){ // an invalid string
		return false;
	}else{
		return true;
	}
	
}
