/**
 * Amir-Hossein Khademi
 */

function validate() {
	var ok = true;

	var value=document.getElementById("principal").value;
	var el = document.getElementById("principal-error");
	if (value == "" || isNaN(vlaue) || value <= 0) { 
		alert("Invalid. Principal must be greater than 0");
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

	value=document.getElementById("rate").value;
	el = document.getElementById("rate-error");
	if (value == "" || isNaN(value) || value <= 0){
		alert("Ivalid. Rate must be greater than 0");
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

	return ok;
}

function doSimpleAjax(address) {
	//var ok = validate();
	//if (ok == false) {
	//	return false;
	//}
	var request = new XMLHttpRequest();
	var data = '';
	/* add your code here to grab all parameters from form*/
	data += "principal=" + document.getElementById("principal").value
	+ "&";
	data += "rate=" + document.getElementById("rate").value +
	"&";
	data += "period=" + document.getElementById("period").value +
	"&";
	data += "grace=" + document.getElementById("grace").value + "&";
	data += "ajax=true";
	var adr = address + "?" + data
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

