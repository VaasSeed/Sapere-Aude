const isbnErrorMessage = "Inserire isbn, un stringa numerica di lunghezza 13";
const categoriaErrorMessage = "I nomi di categorie contengono solo caratteri";
const emptyFieldErrorMessage = "Questo campo non deve essere vuoto";
const pageErrorMessage = "Inserire un numero intero";
const linguaErrorMessage = "Inserire una lingua valida";
const durataErrorMessage = "Esprimere la durata dell'audiolibro nella forma hh:mm:ss";
const priceErrorMessage = "Inserire il costo con i centesimi, ad esempio 00.00";

function validateFormElem(formElem, span, errorMessage) {
	if(formElem.checkValidity()){
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
	let form = document.getElementById("itemForm");
	
	let spanISBN = document.getElementById("errorISBN");
	if(!validateFormElem(form.ISBN, spanISBN, isbnErrorMessage)){
		valid = false;
	} 
	let spanCategoria = document.getElementById("errorCategoria");
	if (!validateFormElem(form.categoria, spanCategoria, categoriaErrorMessage)){
		valid = false;
	}
	let spanPage = document.getElementById("errorPage");
	if (!validateFormElem(form.pages, spanPage, pageErrorMessage)){
		valid = false;
	}
	let spanLinguaB = document.getElementById("errorLinguaB");
	if (!validateFormElem(form.linguaB, spanLinguaB, linguaErrorMessage)){
		valid = false;
	}
	let spanLinguaA = document.getElementById("errorLinguaA");
	if (!validateFormElem(form.linguaA, spanLinguaA, linguaErrorMessage)){
		valid = false;
	}
	let spanaPriceB = document.getElementById("erroraPriceB");
	if (!validateFormElem(form.aPriceB, spanaPriceB, priceErrorMessage)){
		valid = false;
	}
	let spannPriceB = document.getElementById("errornPriceB");
	if (!validateFormElem(form.nPriceB, spannPriceB, priceErrorMessage)){
		valid = false;
	}
	let spanaPriceA = document.getElementById("erroraPriceA");
	if (!validateFormElem(form.aPriceA, spanaPriceA, priceErrorMessage)){
		valid = false;
	}
	let spannPriceA = document.getElementById("errornPriceA");
	if (!validateFormElem(form.nPriceA, spannPriceA, priceErrorMessage)){
		valid = false;
	}
	
	return valid;
}