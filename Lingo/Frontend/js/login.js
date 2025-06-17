const email = sessionStorage.getItem("email");
const emailfield = document.querySelector("#email");
const password = document.querySelector("#password");
const loginButton = document.querySelector("#loginButton");
const errorSpan = document.querySelector("#errorSpan");

emailfield.value = email;

function submit(event) {
	event.preventDefault();
	const raw = JSON.stringify({
		email: emailfield.value,
		password: password.value,
	});

	const requestOptions = {
		method: "POST",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
		},
		body: raw,
		redirect: "follow",
	};

	fetch("https://localhost:5001/api/Authentication/token", requestOptions)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else if (emailfield.value == 0) {
				errorSpan.innerHTML = "Please fill in the correct email.";
			} else if (password.value == 0) {
				errorSpan.innerHTML = "Please fill in the correct password.";
			} else {
				errorSpan.innerHTML = "Email and password do not match!";
			}
		})
		.then(data => {
			sessionStorage.setItem("userToken", data.token);
			sessionStorage.setItem("userId", data.user.id);
			console.log(data);
			sessionStorage.setItem("userName", data.user.nickName);
		})
		.then(() => {
			if (emailfield.value === "quizmaster@pxl.be") {
				window.open("./quizmaster.html", "_self");
			} else {
				window.open("./waitingroom.html", "_self");
			}
		})
		.catch(error => console.log("error", error));
}

//EVENTHANDELERS
loginButton.addEventListener("click", submit);

password.addEventListener("keypress", function (event) {
	if (event.key === "Enter") {
		event.preventDefault();
		loginButton.click();
	}
});
