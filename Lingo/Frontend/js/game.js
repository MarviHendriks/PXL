let wordLetters = document.getElementById("wordLetters");
let submitButton = document.getElementById("submitAnswerButton");
let wordlength;
let guessletters = document.getElementById("guessLetters");
let wordGuess = document.getElementById("wordGuess");
let userControls = document.getElementById("userControls");
let userturn = document.getElementById("userTurnInfo");
let wrong = document.querySelector("#wrong");
let wrongGuess = false;
let h1 = document.querySelector("#goodLuck");
let lingoCardNumbersList = document.getElementById("lingoCardNumber");
let ballPitContainer = document.getElementById("ballPitContainer");
let ballPitButton = document.getElementById("grabBallButton");
let closePitContainerButton = document.getElementById("closeButton");
let hasLingo = document.querySelector("#hasLingo");
let ballInfo = document.querySelector("#ballInfo");
let waitingroomButton = document.querySelector("#returnButton");
let loser = document.querySelector("#loser");
let countBalTrekking = 0;
let finishText;
let lingoCounter = 0;
let lingoCounter1 = 0;
let player1Score = 0;
let player2Score = 0;
let ballNumber = document.getElementById("ballNumber");

function startLiveUpdate() {
	setInterval(function () {
		loadGame();
	}, 100);
}

const requestOptions1 = {
	method: "GET",
	headers: {
		Accept: "application/json",
		"Content-Type": "application/json",
		Authorization: "Bearer " + sessionStorage.getItem("userToken"),
	},
	redirect: "follow",
};

function submitAnswer() {
	wrong.textContent = "";
	const requestOptions2 = {
		method: "POST",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			Authorization: "Bearer " + sessionStorage.getItem("userToken"),
		},
		body: JSON.stringify({
			answer: wordGuess.value,
		}),
		redirect: "follow",
	};
	wordGuess.value = "";
	fetch(
		"https://localhost:5001/api/Game/" +
			sessionStorage.getItem("gameId") +
			"/submit-answer",
		requestOptions2
	)
		.then(response => {
			return response.json();
		})
		.catch(error => console.log(error))
		.finally(loadGame);
}

function loadGame() {
	fetch(
		"https://localhost:5001/api/Game/" + sessionStorage.getItem("gameId"),
		requestOptions1
	)
		.then(response => {
			return response.json();
		})
		.then(data => {
			if (data.currentPuzzle.guesses.length > 0) {
				console.log(data.currentPuzzle.guesses[0].solution);
			}
			sessionStorage.setItem("lingo", data.player1.card.hasLingo);

			//sessionStorage.setItem("", data.finished);

			//ballpit
			if (sessionStorage.getItem("userId") === data.player1.id) {
				if (data.player1.canGrabBallFromBallPit === false) {
					ballPitButton.style.visibility = "hidden";

					closePitContainerButton.style.visibility = "inherit";
				} else {
					ballPitContainer.style.visibility = "visible";

					closePitContainerButton.style.visibility = "hidden";
					ballPitButton.style.visibility = "visible";
				}
			} else {
				if (data.player2.canGrabBallFromBallPit === false) {
					ballPitButton.style.visibility = "hidden";

					closePitContainerButton.style.visibility = "inherit";
				} else {
					ballPitContainer.style.visibility = "visible";

					closePitContainerButton.style.visibility = "hidden";
					ballPitButton.style.visibility = "visible";
				}
			}

			//SCOREBORD

			wordLetters.innerHTML = "";
			if (sessionStorage.getItem("userId") === data.player1.id) {
				h1.innerHTML = `Good luck, ${data.player1.name}`;
				if (player1Score + 100 == data.player1.score) {
					lingoContainer.style.visibility = "visible";
					lingoContainerText.style.visibility = "visible";
					lingoContainerText1.style.visibility = "visible";
					closeButton2.style.visibility = "visible";
					closePitContainerButton.style.visibility = "hidden";
					lingoCounter++;
				} else if (player2Score + 100 == data.player2.score) {
					lingoCounter1++;
				}
				player1Score = data.player1.score;
				player2Score = data.player2.score;
				playerScore.innerHTML = data.player1.score;
				opponentScore.innerHTML = data.player2.score;
				playerName.innerHTML = data.player1.name;
				opponentName.innerHTML = data.player2.name;
			} else {
				h1.innerHTML = `Good luck, ${data.player2.name}`;
				if (player2Score + 100 == data.player2.score) {
					lingoContainer.style.visibility = "visible";
					lingoContainerText.style.visibility = "visible";
					lingoContainerText1.style.visibility = "visible";
					closeButton2.style.visibility = "visible";
					closePitContainerButton.style.visibility = "hidden";
					lingoCounter++;
				} else if (player1Score + 100 == data.player1.score) {
					lingoCounter1++;
				}
				player1Score = data.player1.score;
				player2Score = data.player2.score;
				playerScore.innerHTML = data.player2.score;
				opponentScore.innerHTML = data.player1.score;
				playerName.innerHTML = data.player2.name;
				opponentName.innerHTML = data.player1.name;
			}

			if (data.finished === false) {
				if (data.playerToPlayId === sessionStorage.getItem("userId")) {
					wrong.textContent = "It's your turn!";
					userControls.style.visibility = "visible";
					userturn.style.visibility = "hidden";
					wrongGuess = true;
				} else {
					wrong.textContent = "Please wait for your turn!";
					userControls.style.visibility = "hidden";
					userturn.style.visibility = "visible";
					if (wrongGuess && data.currentPuzzle.guesses.length <= 5) {
						userturn.innerHTML =
							"This word does not exist! You lost your turn.";
					} else if (!wrongGuess && data.currentPuzzle.guesses.length != 5) {
						userturn.innerHTML = "";
					}
					if (data.currentPuzzle.guesses.length == 5) {
						userturn.innerHTML = "To many guesses, you lost your turn.";
					}
				}
			} else if (data.finished) {
				wordGuess.style.visibility = "hidden";
				guessletters.style.visibility = "hidden";
				wordLetters.style.visibility = "hidden";
				waitingroomButton.style.visibility = "visible";
			}

			if (data.currentPuzzle.guesses.length == 6) {
				wrong.textContent = `Puzzle not solved. \n The correct answer was:
					${data.currentPuzzle.guesses[0].solution}`;
				userControls.style.visibility = "hidden";
				userturn.style.visibility = "hidden";
			}

			wordlength = data.currentPuzzle.wordLength;
			for (let i = 0; i < wordlength; i++) {
				let li = document.createElement("li");
				li.style.width = "calc(90%/" + data.currentPuzzle.wordLength;
				li.style.margin = "auto calc(10%/" + data.currentPuzzle.wordLength * 2;
				let letter = document.createTextNode(
					data.currentPuzzle.revealedLetters[i]
				);
				li.appendChild(letter);
				wordLetters.appendChild(li);
			}

			lingoCardNumbersList.innerHTML = "";
			if (sessionStorage.getItem("userId") === data.player1.id) {
				for (let i = 0; i < 5; i++) {
					//console.log(data.player1.card.cardNumbers[i])
					let tr = document.createElement("tr");

					for (let j = 0; j < 5; j++) {
						let span = document.createElement("span");
						let td = document.createElement("td");
						//let li = document.createElement("li");
						let numberText = document.createTextNode(
							data.player1.card.cardNumbers[i][j].Value
						);
						td.classList.add("numberCircle");
						if (data.player1.card.cardNumbers[i][j].CrossedOut) {
							td.style.backgroundColor = "white";
							td.style.color = "lightgray";
						}

						//console.log(data.player1.card.cardNumbers[i][j].Value)
						span.appendChild(td);
						td.appendChild(numberText);
						tr.appendChild(span);
						lingoCardNumbersList.appendChild(tr);
					}
				}
			} else {
				for (let i = 0; i < 5; i++) {
					let tr = document.createElement("tr");

					for (let j = 0; j < 5; j++) {
						let span = document.createElement("span");
						let td = document.createElement("td");
						//let li = document.createElement("li");
						let numberText = document.createTextNode(
							data.player2.card.cardNumbers[i][j].Value
						);
						td.classList.add("numberCircle");
						if (data.player2.card.cardNumbers[i][j].CrossedOut) {
							td.style.backgroundColor = "white";
							td.style.color = "lightgray";
						}

						//console.log(data.player1.card.cardNumbers[i][j].Value)
						span.appendChild(td);
						td.appendChild(numberText);
						tr.appendChild(span);
						lingoCardNumbersList.appendChild(tr);
					}
				}
			}
			guessletters.innerHTML = "";
			let count = 0;
			for (let j = 0; j < data.currentPuzzle.guesses.length; j++) {
				count = 0;
				for (let i = 0; i < wordlength; i++) {
					let li = document.createElement("li");
					li.style.width = "calc(90%/" + data.currentPuzzle.wordLength;
					li.style.margin =
						"auto calc(10%/" + data.currentPuzzle.wordLength * 2;
					let letter = document.createTextNode(
						data.currentPuzzle.guesses[j].word[i]
					);
					if (data.currentPuzzle.guesses[j].letterMatches[i] === 1) {
						li.style.color = "green";
						count++;
					} else if (data.currentPuzzle.guesses[j].letterMatches[i] === 0) {
						li.style.color = "orange";
					} else {
						li.style.color = "red";
					}
					li.appendChild(letter);
					guessletters.appendChild(li);
				}
			}

			if (count == 5) {
				userControls.style.visibility = "hidden";
				userturn.style.visibility = "hidden";
			}

			if (
				data.finished &&
				data.player1.canGrabBallFromBallPit === false &&
				data.player2.canGrabBallFromBallPit === false
			) {
				if (data.player1.score > data.player2.score) {
					h1.innerHTML = ` ${data.player1.name} won this game with ${data.player1.score} points!`;
					loser.innerHTML = `${data.player2.name} lost this game with ${data.player2.score} points!`;
					wrong.textContent = "";
				} else if (data.player1.score === data.player2.score) {
					h1.innerHTML = `It's a draw!`;
					wrong.textContent = "";
					loser.innerHTML = "";
				} else {
					h1.innerHTML = `${data.player2.name} won this game with ${data.player2.score} points!`;
					loser.innerHTML = `${data.player1.name} lost this game with ${data.player1.score} points!`;
					wrong.textContent = "";
				}
			}
		})
		.catch(error => console.log(error));
}

let rarecount = 0;

function grabBall() {
	const requestOptions = {
		method: "POST",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			Authorization: "Bearer " + sessionStorage.getItem("userToken"),
		},
		body: JSON.stringify({
			value: 0,
			type: 0,
		}),
		redirect: "follow",
	};
	wordGuess.value = "";
	fetch(
		"https://localhost:5001/api/Game/" +
			sessionStorage.getItem("gameId") +
			"/grab-ball",
		requestOptions
	)
		.then(response => {
			return response.json();
		})

		.then(data => {
			console.log(data);
			userturn.innerHTML = "";
			console.log(sessionStorage.getItem("lingo"));
			if (sessionStorage.getItem("lingo") === true) {
				lingoCounter++;
			}
			ballInfo.style.visibility = "visible";

			if (data.type == 0 && countBalTrekking == 0) {
				ballNumber.innerHTML = data.value;
				hasLingo.innerHTML = `You grabbed ball number ${data.value}. It is now crossed out on your card.`;
				ballInfo.style.backgroundColor = "rgb(13, 69, 70)";
				ballPitButton.textContent = "Grab again!";
				countBalTrekking++;
			} else if (data.type == 0) {
				ballNumber.innerHTML = data.value;
				hasLingo.innerHTML = `You grabbed ball number ${data.value}, you can start the next puzzle. ${data.value} is now crossed out on your card.`;
				ballInfo.style.backgroundColor = "rgb(13, 69, 70)";
				closePitContainerButton.textContent = "Close ballpit";
				countBalTrekking = 0;
				if (sessionStorage.getItem("finished") == true) {
					closePitContainerButton.textContent = "Finish Game";
				}
			} else if (data.type == 1) {
				ballNumber.innerHTML = "";
				hasLingo.innerHTML = "You grabbed a red ball!, your turn is lost.";
				ballInfo.style.backgroundColor = "rgb(150, 17, 17)";
				closePitContainerButton.textContent = "Turn Lost!";
				countBalTrekking = 0;
				if (sessionStorage.getItem("finished") == true) {
					closePitContainerButton.textContent = "Finish Game";
				}
			}
		});
}

function waitingroom() {
	fetch(
		"https://localhost:5001/api/Game/" + sessionStorage.getItem("gameId"),
		requestOptions1
	)
		.then(response => {
			return response.json();
		})
		.then(data => {
			console.log(data);
			//sessionStorage.setItem("", data.finished);

			//ballpit
			if (sessionStorage.getItem("userId") === data.player1.id) {
				window.open("./waitingroom.html", "_self");
			} else if (sessionStorage.getItem("userId") === data.player2.id) {
				window.open("./waitingroom.html", "_self");
			}
		})
		.catch(error => console.log(error));
}

//eventhandlers
submitButton.addEventListener("click", submitAnswer);
wordGuess.addEventListener("keypress", function (event) {
	if (event.key === "Enter") {
		event.preventDefault();
		submitButton.click();
	}
});

ballPitButton.addEventListener("click", () => {
	userturn.innerHTML = "";
	grabBall();
});

closePitContainerButton.addEventListener("click", () => {
	ballPitContainer.style.visibility = "hidden";
	ballInfo.style.visibility = "hidden";
	hasLingo.innerHTML = `You guessed the correct word! You can grab a ball and get 25 points!`;
	ballPitButton.textContent = "Grab ball";
	userturn.innerHTML = "";
});

let closeButton2 = document.querySelector("#closeButton2");
let lingoContainer = document.querySelector("#lingoContainer");
let lingoContainerText = document.querySelector("#lingoContainerText");
let lingoContainerText1 = document.querySelector("#lingoContainerText1");
closeButton2.addEventListener("click", () => {
	lingoContainer.style.visibility = "hidden";
	lingoContainerText.style.visibility = "hidden";
	lingoContainerText1.style.visibility = "hidden";
	closeButton2.style.visibility = "hidden";
});

waitingroomButton.addEventListener("click", waitingroom);
//Userinfo on page

let playerName = document.querySelector("#playerName");
let playerScore = document.querySelector("#playerScore");

let opponentName = document.querySelector("#opponentName");
let opponentScore = document.querySelector("#opponentScore");

let loginDetail = document.getElementById("fixed");

startLiveUpdate();
