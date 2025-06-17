// LOCATE register, input fields, spans
const register = document.querySelector("#register");
const reset = document.querySelector("#reset");
const password = document.querySelector("#password");
const passwordCheck = document.querySelector("#passwordCheck");
const email = document.querySelector("#email");
const username = document.querySelector("#username");
const message = document.querySelector("#message");
const strength = document.querySelector("#strength");
const passwordVisibility = document.querySelector("#checkboxPassword");
const passwordCheckVisibility = document.querySelector(
	"#checkboxPasswordCheck"
);

//Show password
function showPassword() {
	if (password.type === "password") {
		password.type = "text";
	} else {
		password.type = "password";
	}
}

//Show password
function showPasswordCheck() {
	if (passwordCheck.type === "password") {
		passwordCheck.type = "text";
	} else {
		passwordCheck.type = "password";
	}
}

// SUBMIT function
function submit(event) {
	sessionStorage.setItem("email", email.value);
	if (
		passwordCheck.value == password.value &&
		password.value.length != 0 &&
		password.value.length > 5 &&
		passwordCheck.value.length > 5
	) {
		event.preventDefault();
		const myHeaders = new Headers();
		myHeaders.append("Content-Type", "application/json");

		const raw = JSON.stringify({
			email: email.value,
			password: password.value,
			nickName: username.value,
		});

		const requestOptions = {
			method: "POST",
			headers: myHeaders,
			body: raw,
			redirect: "follow",
		};

		let statusCode;

		fetch("https://localhost:5001/api/Authentication/register", requestOptions)
			.then(response => {
				if (response) {
					statusCode = response.status;
					response.json();
				}
			})
			.then(result => {
				if (result && result.message) {
					alert(result.message);
				} else {
					if (statusCode == 200) {
						window.open("./login.html", "_self");
					} else {
						alert("Something went wrong with the server");
					}
				}
			})
			.catch(error => console.log("error", error));
	}
}

// CHECK passwords
function checkPasswords() {
	message.style.fontWeight = "bold";
	if (password.value === passwordCheck.value) {
		message.style.color = "lightgreen";
		message.innerHTML = "matching";
		passwordCheck.setCustomValidity("");
		if (password.value == 0) {
			message.innerHTML = "";
		}
	} else {
		message.style.color = "red";
		message.innerHTML = "not matching";
		passwordCheck.setCustomValidity("Passwords do not match");
		if (passwordCheck.value == 0) {
			message.innerHTML = "";
		}
	}
}

// SPECIAL characters
function containsSpecialChars(str) {
	const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
	return specialChars.test(str);
}

// STRENGTH of password
function passwordStrength() {
	let count = 0;
	let bool = true;
	let bool1 = true;
	let bool2 = true;
	for (let i = 0; i <= password.value.length; i++) {
		if (containsSpecialChars(password.value[i]) && bool == true) {
			count += 1;
			bool = false;
		}
		if (password.value[i] >= "A" && password.value[i] <= "Z" && bool1 == true) {
			count += 1;
			bool1 = false;
		}
		if (password.value[i] >= 0 && password.value[i] <= 9 && bool2 == true) {
			count += 1;
			bool2 = false;
		}

		if (count == 1 || password.value.length < 8) {
			strength.style.color = "red";
			strength.innerHTML = "Weak password";
		} else if (count == 2 && password.value.length > 10) {
			strength.style.color = "orange";
			strength.innerHTML = "Medium password";
		} else if (count == 3 && password.value.length > 12) {
			strength.style.color = "lightgreen";
			strength.innerHTML = "Strong password";
		}
		if (password.value.length == 0) {
			count = 0;
			strength.innerHTML = "";
		}
	}
}

// RESET button
function resetClick() {
	message.innerHTML = "";
	strength.innerHTML = "";
}

// EVENTHANDLERS
password.addEventListener("keyup", passwordStrength);
password.addEventListener("keyup", checkPasswords);
passwordCheck.addEventListener("keyup", checkPasswords);
reset.addEventListener("click", resetClick);
register.addEventListener("click", submit);
passwordVisibility.addEventListener("click", showPassword);
passwordCheckVisibility.addEventListener("click", showPasswordCheck);

password.addEventListener("keypress", function (event) {
	if (event.key === "Enter") {
		event.preventDefault();
		register.click();
	}
});
