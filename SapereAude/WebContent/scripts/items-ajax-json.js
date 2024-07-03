function itemsFunction() {
    var url = '../ItemsServlet';
    //loadAjaxDoc(url, handleCAP);
    loadAjaxDoc(url, "GET", printItems);
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

function loadAjaxDoc(url, method, cFuction) {
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
			request.open("GET", url, true);
			request.setRequestHeader("Connection", "close");
	        request.send(null);
	        
		} else {
				request.open("POST", url, true);
				request.setRequestHeader("Connection", "close");
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(null);
		}
		
	}
}

function printItems(request){
	var response = JSON.parse(request.responseText);
	for(var i = 0; i < response.length; i++){
		var root = document.getElementById("hi");
		
		var div = document.createElement("span");
		div.classList.add("list");
		
		div.innerHTML += response[i].ISBN + "<br>";
		
		div.innerHTML += response[i].Nome + "<br>";
		
		div.innerHTML += response[i].CasaEditrice + "<br>";
		
		div.innerHTML += response[i].Categoria + "<br>";
		
		div.innerHTML += response[i].MediaVoti + "<br>";
		
		div.innerHTML += response[i].Autore + "<br>";
		
		root.append(div);
	}
}
