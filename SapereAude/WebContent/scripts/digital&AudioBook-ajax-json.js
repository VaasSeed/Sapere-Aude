function showBook() {
	var isbn = sessionStorage.getItem("ISBN");
	var params = "ISBN="+isbn;
    var url = '../DigitalBookServlet';
    loadAjaxDoc(url, params, "GET", printDigitalBook);
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
						alert("Problemi nell'esecuzione della richiesta Book:\n" + this.status);
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

function printDigitalBook(request){
	var response = JSON.parse(request.responseText);
		var root = document.getElementById("content");
		while (root.firstChild) {
		    root.removeChild(root.firstChild);
		}
		
		var head = document.getElementById("head");
		head.style = "text-align : center";
		head.innerHTML = "LIBRO" + "<br>" + "<br>";
		
		var text = document.createElement("span");
		text.className = "center";
		
		var name = sessionStorage.getItem("Name");
		var category = sessionStorage.getItem("Category");
		var value = sessionStorage.getItem("Value");
		var author = sessionStorage.getItem("Author");
		var src = sessionStorage.getItem("Foto");
		var costoAcquisto = response.CostoAcquisto;
		var costoNoleggio = response.CostoNoleggio;
		sessionStorage.setItem("CostoAcquisto", costoAcquisto);
		sessionStorage.setItem("CostoNoleggio", costoNoleggio);
		sessionStorage.setItem("Type", "libro");

		var photo = document.createElement("img");
		photo.src = src;
		root.append(photo); 
		
		text.innerHTML += "<br>Nome: " + name + "<br>";
		
		text.innerHTML += "Categoria: " + category + "<br>";
		
		text.innerHTML += "Valutazione: " + value + "<br>";
		
		text.innerHTML += "Autore: " + author + "<br>";
		
		text.innerHTML += "Pagine: " + response.NumPagine + "<br>";
		
		text.innerHTML += "Lingua: " + response.Lingua + "<br>";
		
		text.innerHTML += "Prezzo acquisto: " + costoAcquisto + "&#8364 <br>";
		
		text.innerHTML += "Prezzo noleggio: " + costoNoleggio + "&#8364 <br>";

		root.append(text);
}

function showAudioBook() {
	var isbn = sessionStorage.getItem("ISBN");
	
	var params = "ISBN="+isbn;
    var url = '../AudioBookServlet';
    loadAjaxDoc(url, params, "GET", printAudioBook);
}

function printAudioBook(request){
	var response = JSON.parse(request.responseText);
		var root = document.getElementById("content");
		while (root.firstChild) {
		    root.removeChild(root.firstChild);
		}
		
		var head = document.getElementById("head");
		head.style = "text-align : center";
		head.innerHTML = "AUDIOLIBRO" + "<br>" + "<br>";
		
		var text = document.createElement("span");
		
		var name = sessionStorage.getItem("Name");
		var category = sessionStorage.getItem("Category");
		var value = sessionStorage.getItem("Value");
		var author = sessionStorage.getItem("Author");
		var src = sessionStorage.getItem("Foto");
		var costoAcquisto = response.CostoAcquisto;
		var costoNoleggio = response.CostoNoleggio;
		sessionStorage.setItem("CostoAcquisto", costoAcquisto);
		sessionStorage.setItem("CostoNoleggio", costoNoleggio);
		sessionStorage.setItem("Type", "audiolibro");

		var photo = document.createElement("img");
		photo.src = src;
		root.append(photo); 

		text.innerHTML += "<br>Nome: " + name + "<br>";

		text.innerHTML += "Categoria: " + category + "<br>";

		text.innerHTML += "Valutazione: " + value + "<br>";

		text.innerHTML += "Autore: " + author + "<br>";
		
		text.innerHTML += "Durata: " + response.Durata + "<br>";
		
		text.innerHTML += "Lingua: " + response.Lingua + "<br>";
		
		text.innerHTML += "Prezzo acquisto: " + costoAcquisto + "&#8364 <br>";
		
		text.innerHTML += "Prezzo noleggio: " + costoNoleggio + "&#8364 <br>";
		
		root.append(text);		
}

function addToCart(x) {
	var isbn = sessionStorage.getItem("ISBN");
	var name = sessionStorage.getItem("Name");
	var type = sessionStorage.getItem("Type");
	var op = x;
	var rPrice = sessionStorage.getItem("CostoNoleggio");
	var pPrice = sessionStorage.getItem("CostoAcquisto");
	
	if(!op.localeCompare("noleggio")){
		var price = rPrice;
	}
	else if(!op.localeCompare("acquisto"))
		var price = pPrice;
			
	var params = "ISBN="+isbn+"&name="+name+"&type="+type+"&operation="+op+"&price="+price;
    var url = '../AddToCart';
    loadAjaxDoc(url, params, "POST", msg);
}

function msg(){
	var my_popup = document.getElementById("my-popup");
	my_popup.style.display="block";
	setTimeout(hide_msg, 4000);
}

function hide_msg(){
	var my_popup = document.getElementById("my-popup");
	my_popup.style.display="none";
}
