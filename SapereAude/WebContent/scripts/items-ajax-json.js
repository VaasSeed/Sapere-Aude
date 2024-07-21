function home(){
	var currentLocation = window.location.href;
	window.location.assign(currentLocation.substring(0, 33) + "common/Home.jsp");
	return false;
}

function itemsFunction() {
    var url = '../ItemsServlet';
    loadAjaxDoc(url, "GET", printItems);
}


function userItemsFunction() {
    var url = '../UserItems';
    loadAjaxDoc(url, "GET", printUserItems);
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

function printUserItems(request){
	var response = JSON.parse(request.responseText);
	
	var root = document.getElementById("hi");
	while (root.firstChild) {
	    root.removeChild(root.firstChild);
	}
	
	for(var i = 0; i < response.length; i++){
		
		var ph = response[i].Foto;
		var isbn = response[i].ISBN;
		var name = response[i].Nome;
		var author = response[i].Autore;
		var rentEnd = response[i].FineNoleggio
		var tipo = response[i].Tipo;
		var operazione = response[i].Operazione;
		
		var div = document.createElement("button");
		div.classList.add("list");
		div.setAttribute("type", "button");
		
		var photo = document.createElement("img");
		photo.classList.add("image");
		var src = "data:image/png;base64," + ph;
		photo.src = src;
		
		div.appendChild(photo);
		
		div.innerHTML += "<br>" + name + "<br>";
		
		div.innerHTML += author + "<br>";
		
		div.innerHTML += tipo + "<br>";
		
		div.innerHTML += "Opera " + operazione + "<br>";
		
		if(rentEnd != "no"){
			div.innerHTML += "Data fine noleggio: " + rentEnd + "<br>";
		}
		
		
		var button = document.createElement('a');
		
		if(tipo == "libro"){
			button.setAttribute("href", "ReadBook.jsp?isbn="+isbn);
			button.classList.add("a-button");
			button.innerHTML = "LEGGI";
		}
		else{
			button.setAttribute("href", "ListenBook.jsp?isbn="+isbn);
			button.classList.add("a-button");
			button.innerHTML = "ASCOLTA";
		}
	
		div.append(button);	
		root.append(div);
		
	}
}

function printItems(request){
	var response = JSON.parse(request.responseText);
	
	var root = document.getElementById("hi");
	while (root.firstChild) {
	    root.removeChild(root.firstChild);
	}
	
	for(var i = 0; i < response.length; i++){
		
		var isbn = response[i].ISBN;
		var name = response[i].Nome;
		var category = response[i].Categoria
		var author = response[i].Autore
		var ph = response[i].Foto;
	
		
		var div = document.createElement("button");
		div.classList.add("list");
		div.setAttribute("type", "button");
		
		var photo = document.createElement("img");
		photo.classList.add("image");
		var src = "data:image/png;base64," + ph;
		photo.src = src;
		
		
		div.appendChild(photo);
		
		div.innerHTML += "<br>" + name + "<br>";
		
		div.innerHTML += category + "<br>";
		
		div.innerHTML += author+ "<br>";
		
		var button = document.createElement('a');
		button.classList.add("a-button");
		button.setAttribute("href", "DigitalBook.jsp?isbn="+isbn);
		button.innerHTML = "APRI";
		
		root.append(div);
		div.append(button);	
	}
}
