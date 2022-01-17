/**
 * Amir-Hossein Khademi
 */

function validate() {
	var ok = true;

	var value=document.getElementById("prefix").value;
	if (value == "" || isNaN(vlaue)) { 
		ok = false;
	}
	else {
	}

	value=document.getElementById("credit").value;
	if (value == "" || isNaN(value) || value <= 0){
		ok = false;
	}
	else {
	}

/*	
	value = document.getElementById("period").value;
	el = document.getElementById("period-error");
	if (value == "" || isNan(value) || value <= 0){
		alert("Invalid. Period must be greater than 0");
		if (el != null ) {
			el.style.display ="inline";
			el.style.color = "red";
		}
		ok = false;
	}
	else {
		if (el != null ) {
			el.style.display ="none";
		}
	}
*/
	return ok;
}

function reportAjax(address) {
	// using ajax
	var request = new XMLHttpRequest();
	var data = '';
	/* add your code here to grab all parameters from form*/
	data += "prefix=" + document.getElementById("prefix").value
	+ "&";
	data += "credit=" + document.getElementById("credit").value;
	var adr = address + "?" + data;
	request.open("POST", adr, true);
	request.onreadystatechange = function() {
		handler(request);
	}
	request.send();
}

function handler(request){
	if ((request.readyState == 4) && (request.status == 200)) {
		var target = document.getElementById("ajaxTarget");
		target.innerHTML = request.responseText;
	}
}

