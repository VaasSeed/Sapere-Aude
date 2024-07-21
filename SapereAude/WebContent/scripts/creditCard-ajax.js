function deleteCard(num){
   var params = "num="+num;
   var url = '../RemoveCreditCardServlet';
   loadAjaxDoc(url, params, "POST", goBack);
}


function createXMLHttpRequest() {
	var request;
	try {
		// Firefox 1+, Chrome 1+, Opera 8+, Safari 1.2+, Edge 12+, Internet Explorer 7+
		request = new XMLHttpRequest();
	} catch (e) {
		// past versions of Internet Explorer 
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");  
		} catch (e) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Il browser non supporta AJAX");
				return null;
			}
		}
	}
	return request;
}

function loadAjaxDoc(url, params, method, cFuction) {
	var request = createXMLHttpRequest();
	if(request){
		request.onreadystatechange = function() {
			if (this.readyState == 4) {
				if (this.status == 200) {
				    cFuction(this);
				} else {				
					if(this.status == 0){ // When aborting the request
						alert("Problemi nell'esecuzione della richiesta: nessuna risposta ricevuta nel tempo limite");
					} else { // Any other situation
						alert("Problemi nell'esecuzione della richiesta:\n" + this.status);
					}
					return null;
				}
		    }
		};
		
		setTimeout(function () {     // to abort after 15 sec
        	if (request.readyState < 4) {
            	request.abort();
        	}
    	}, 15000); 
		
		if(method.toLowerCase() == "get"){
			if(params){
				request.open("GET", url + "?" + params, true);
			} else {
				request.open("GET", url, true);
			}
			request.setRequestHeader("Connection", "close");
		    request.send(null);
		    
		} else {
			
			if(params){
				request.open("POST", url, true);
				request.setRequestHeader("Connection", "close");
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    	request.send(params);
			} else {
				console.log("Usa GET se non ci sono parametri!");
				return null;
			}
		}
		
	}
}

function goBack(){
	location.reload();
}


const numeroErrorMessage = "Numero carta non valido, inserire una sequenza numerica di lunghezza 16";
const codiceErrorMessage = "Codice di sicurezza non valido";
const emptyFieldErrorMessage = "Questo campo non deve essere vuoto";
const scadenzaErrorMessage = "Formato non valido, inserire la scadenza nella forma MM-YY";
const ownerErrorMessage = "Inserire correttamente l'intestatario";
const scadutaErrorMessage = "La carta di credito \u00E8 scaduta"

function validateFormElem(formElem, span, errorMessage) {
	if(formElem.checkValidity()){
		if(formElem.id == "scadenza"){
			var today = new Date();
			var scadenza = new Date("20"+formElem.value.substring(3,5)+"-"+formElem.value.substring(0,2)+"-21");
			var y = today.getFullYear() - scadenza.getFullYear();
			var m = today.getMonth() - scadenza.getMonth();
			if (y > 0) {
				span.style.color = "red";
				span.innerHTML = scadutaErrorMessage;
				return false;
			}
			else if(y == 0){
				if(m > 0){
					span.style.color = "red";
					span.innerHTML = scadutaErrorMessage;
					return false;	
				}
			}
		}
		span.style.color = "black";
		span.innerHTML = "";
		return true;
	}
	span.style.color = "red";
	if (formElem.validity.valueMissing){
		span.innerHTML = emptyFieldErrorMessage;
	} else {
		span.innerHTML = errorMessage;
	}
	return false;
}


function validate() {
	let valid = true;	
	let form = document.getElementById("cardForm");
	
	let spanNumero = document.getElementById("errorNumero");
	if(!validateFormElem(form.numero, spanNumero, numeroErrorMessage)){
		valid = false;
	} 
	let spanCodice = document.getElementById("errorCodice");
	if (!validateFormElem(form.code, spanCodice, codiceErrorMessage)){
		valid = false;
	}
	let spanScadenza = document.getElementById("errorScadenza");
	if (!validateFormElem(form.scadenza, spanScadenza, scadenzaErrorMessage)){
		valid = false;
	}
	let spanOwner = document.getElementById("errorOwner");
	if (!validateFormElem(form.owner, spanOwner, ownerErrorMessage)){
		valid = false;
	}
	
	return valid;
}