const nameOrSurnameErrorMessage = "Questo campo deve contenere solo lettere";
const emailErrorMessage = "Il formato dell'email deve essere xxxxx@domain.xx";
const emptyFieldErrorMessage = "Questo campo non deve essere vuoto";
const passwordErrorMessage = "La password deve contenere almeno 8 caratteri, almeno una lettera maiuscola ed almeno un numero";
const dateErrorMessage = "Inserire la data nel formato: gg-mm-aaaa ";
const toYoungErrorMessage = "Non \u00E8 consentita l'iscrizione ai minori di 14 anni"

function validateFormElem(formElem, span, errorMessage) {
	if(formElem.checkValidity()){
		if(formElem.id == "bDate"){
			var today = new Date();
			var birthDate = new Date(formElem.value);
			var age = today.getFullYear() - birthDate.getFullYear();
			var m = today.getMonth() - birthDate.getMonth();
			if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
			    age--;
			}
			if(age < 14){
				span.style.color = "red";
				span.innerHTML = toYoungErrorMessage;
				return false;
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
	let form = document.getElementById("regForm");
	
	let spanName = document.getElementById("errorName");
	if(!validateFormElem(form.name, spanName, nameOrSurnameErrorMessage)){
		valid = false;
	} 
	let spanLastname = document.getElementById("errorSurname");
	if (!validateFormElem(form.surname, spanLastname, nameOrSurnameErrorMessage)){
		valid = false;
	}
	let spanEmail = document.getElementById("errorEmail");
	if (!validateFormElem(form.email, spanEmail, emailErrorMessage)){
		valid = false;
	}
	let spanPassword = document.getElementById("errorPassword");
	if (!validateFormElem(form.pswd, spanPassword, passwordErrorMessage)){
		valid = false;
	}
	let spanDate = document.getElementById("errorDate");
	if (!validateFormElem(form.bDate, spanDate, dateErrorMessage)){
		valid = false;
	}
	
	return valid;
}

