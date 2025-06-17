let table = document.getElementById("playerList");
let buttons = [];
let gameDetailsContainer = document.getElementById("gameDetailsContainer");
let gameDetailsLabel = document.getElementById("gameDetaillLabel");
let gameDetail = document.getElementById("gameDetail");
let joinGameButton = document.getElementById("joinGameButton");
let selectedGameId;
let loginDetail = document.getElementById("fixed");
let h1 = document.querySelector("#h1");

h1.innerHTML =
	"Welcome to the waitingroom " + sessionStorage.getItem("userName") + ".";

const requestOptions = {
	method: "GET",
	headers: {
		Accept: "application/json",
		"Content-Type": "application/json",
		Authorization: "Bearer " + sessionStorage.getItem("userToken"),
	},
	redirect: "follow",
};

function loadSelectedGame() {
	let url = "https://localhost:5001/api/Game/my-scheduled-games";
	fetch(url, requestOptions)
		.then(response => {
			return response.json();
		})
		.then(data => {
			table.innerHTML = "";
			for (let i = 0; i < data.length; i++) {
				let button = document.createElement("button");
				let tr = document.createElement("tr");
				let td = document.createElement("td");
				button.textContent = "Game " + (i + 1);
				button.setAttribute("id", data[i].id);

				buttons.push(button);
				td.appendChild(button);
				tr.appendChild(td);
				table.appendChild(tr);
			}
			for (let i = 0; i < buttons.length; i++) {
				buttons[i].addEventListener("click", () => {
					selectGame(buttons[i].id);
				});
			}
		})
		.catch(error => console.log(error));
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

function selectGame(thisId) {
	fetch("https://localhost:5001/api/Game/" + thisId, requestOptions1)
		.then(response => {
			return response.json();
		})
		.then(data => {
			gameDetailsLabel.innerHTML = "";
			gameDetail.innerHTML = "";

			let e = document.createElement("li");
			let ee = document.createTextNode("Game ID");
			e.appendChild(ee);
			gameDetailsLabel.appendChild(e);

			let f = document.createElement("li");
			let ff = document.createTextNode("Player 1");
			f.appendChild(ff);
			gameDetailsLabel.appendChild(f);

			let g = document.createElement("li");
			let gg = document.createTextNode("Player 2");
			g.appendChild(gg);
			gameDetailsLabel.appendChild(g);

			let h = document.createElement("li");
			let hh = document.createTextNode("Player to play ID");
			h.appendChild(hh);
			gameDetailsLabel.appendChild(h);

			let i = document.createElement("li");
			let ii = document.createTextNode("Current puzzle ID");
			i.appendChild(ii);
			gameDetailsLabel.appendChild(i);

			let j = document.createElement("li");
			let jj = document.createTextNode("Is the puzzle Finished?");
			j.appendChild(jj);
			gameDetailsLabel.appendChild(j);

			selectedGameId = data.id;

			let gameId = document.createElement("li");
			let gameIdContent = document.createTextNode(data.id);
			gameId.appendChild(gameIdContent);
			gameDetail.appendChild(gameId);

			let player1Id = document.createElement("li");
			let player1IdContent = document.createTextNode(data.player1.name);
			player1Id.appendChild(player1IdContent);
			gameDetail.appendChild(player1Id);

			let player2Id = document.createElement("li");
			let player2IdContent = document.createTextNode(data.player2.name);
			player2Id.appendChild(player2IdContent);
			gameDetail.appendChild(player2Id);

			let playerToPlayId = document.createElement("li");
			let playerToPlayIdContent = document.createTextNode(data.playerToPlayId);
			playerToPlayId.appendChild(playerToPlayIdContent);
			gameDetail.appendChild(playerToPlayId);

			let currentPuzzle = document.createElement("li");
			let currentPuzzleContent = document.createTextNode(data.currentPuzzle.id);
			currentPuzzle.appendChild(currentPuzzleContent);
			gameDetail.appendChild(currentPuzzle);

			let finished = document.createElement("li");
			let bool = data.finished;
			let yes = document.createTextNode("YES");
			let no = document.createTextNode("NO");
			if (bool) {
				finished.appendChild(yes);
			} else {
				finished.appendChild(no);
			}

			gameDetail.appendChild(finished);
		})
		.catch(error => console.log(error));

	gameDetailsContainer.style.visibility = "visible";
}

function startGameClick() {
	sessionStorage.setItem("gameId", selectedGameId);
	window.open("./game.html", "_self");
}

//Eventhandlers
joinGameButton.addEventListener("click", startGameClick);

function startLiveUpdate() {
	setInterval(function () {
		loadSelectedGame();
	}, 1000);
}

startLiveUpdate();
