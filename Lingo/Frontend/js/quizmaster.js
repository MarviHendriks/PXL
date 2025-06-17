const list = document.createDocumentFragment();
let table = document.getElementById("playerList");
let rows = table.getElementsByTagName("tr");

const filterButton = document.getElementById("filterButton");
const clearButton = document.getElementById("clearButton");
const resetButton = document.querySelector("#resetPlayers");
const startButton = document.querySelector("#startGame");

let createdGame = document.querySelector("#createdGame");

let userMatch = [];
let player1 = document.querySelector("#player1");
let player2 = document.querySelector("#player2");
let filterValue = document.getElementById("userSearch");

let id1, id2;
let playerList = [];
let dataCell = null;

const requestOptions = {
	method: "GET",
	headers: {
		Accept: "application/json",
		"Content-Type": "application/json",
		Authorization: "Bearer " + sessionStorage.getItem("userToken"),
	},
	redirect: "follow",
};

//START GAME FUNCTION

function startGame() {
	if (player1.innerHTML == "" || player2.innerHTML == "") {
		alert("Please select 2 players");
	} else if (userMatch[0] === userMatch[1]) {
		alert("Please select 2 different players!");
	} else {
		console.log(player1.innerHTML + player2.innerHTML);
		let raw = JSON.stringify({
			user1Id: id1,
			user2Id: id2,
			settings: {
				numberOfStandardWordPuzzles: 4,
				minimumWordLength: 5,
				maximumWordLength: 5,
			},
		});

		let requestOptionsCreateGame = {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",

				Authorization: "Bearer " + sessionStorage.getItem("userToken"),
			},
			body: raw,
			redirect: "follow",
		};
		console.log(id1);

		let url = "https://localhost:5001/api/Quizmaster/create-game";
		fetch(url, requestOptionsCreateGame)
			.then(response => {
				createdGame.innerHTML =
					"Match created for " + userMatch[0] + " and " + userMatch[1];
				createdGame.style.border = "1px solid black";
				createdGame.style.backgroundColor = "rgb(8, 93, 94)";
				createdGame.style.color = "rgb(211, 206, 206)";

				resetClick();

				return response.json();
			})

			.then(result => {})
			.catch(error => console.log(error));
	}
}

// LOAD PLAYERLIST
function loadplayerlist() {
	let url =
		"https://localhost:5001/api/Quizmaster/users?filter=" + filterValue.value;
	fetch(url, requestOptions)
		.then(response => {
			return response.json();
		})
		.then(data => {
			playerList = [];

			for (let user of data) {
				playerList.push(user);
			}
			if (playerList.length == 0) {
				table.innerHTML = "";
				let tr = document.createElement("tr");
				let td = document.createElement("td");
				let emptySearch = document.createTextNode("No matches found!");
				td.appendChild(emptySearch);
				tr.appendChild(td);
				td.style.backgroundColor = "rgb(8, 93, 94)";
				td.style.Color = "rgb(211, 206, 206)";
				table.appendChild(tr);
			} else {
				showPlayers();
				selectUser();
			}
		})
		.catch(error => console.log(error));
}

// SELECT USERS

function selectUser() {
	for (let i = 0; i < rows.length; i++) {
		dataCell = rows[i].getElementsByTagName("td");
		for (let j = 0; j < dataCell.length; j += 2) {
			dataCell[j].style.cursor = "pointer";
			dataCell[j].onclick = function () {
				if (player1.innerHTML === "") {
					player1.innerHTML = this.innerHTML;
					test1 = this.innerHTML;
					userMatch.push(player1.innerHTML);
					player1.innerHTML += " VS&nbsp";
					rows[i].style.backgroundColor = "rgb(9, 93, 94)";
				} else if (
					player2.innerHTML === "" &&
					rows[i].style.backgroundColor !== "rgb(9, 93, 94)"
				) {
					player2.innerHTML = this.innerHTML;
					userMatch.push(player2.innerHTML);
					rows[i].style.backgroundColor = "rgb(9, 93, 94)";
				}
				if (userMatch.length == 2) {
					for (let i = 0; i < playerList.length; i++) {
						if (playerList[i].nickName == userMatch[0]) {
							id1 = playerList[i].id;
						}
						if (playerList[i].nickName == userMatch[1]) {
							id2 = playerList[i].id;
						}
					}
				}
			};
		}
	}
}

// SHOW PLAYERS
function showPlayers() {
	table.innerHTML = "";

	// Table Headers
	let tr = document.createElement("tr");
	let th = document.createElement("th");
	let th2 = document.createElement("th");
	let userName = document.createTextNode("Username");
	let userEmail = document.createTextNode("Email");
	th.appendChild(userName);
	th2.appendChild(userEmail);
	tr.appendChild(th);
	tr.appendChild(th2);
	table.appendChild(tr);

	// Table Data
	for (let user of playerList) {
		let tr = document.createElement("tr");
		let td = document.createElement("td");
		let td2 = document.createElement("td");
		let name = document.createTextNode(user.nickName);
		let email = document.createTextNode(user.email);
		td.appendChild(name);
		td2.appendChild(email);
		tr.appendChild(td);
		tr.appendChild(td2);
		table.appendChild(tr);
	}
}

//RESET SELECTED PLAYERS
function resetClick() {
	for (let i = 0; i < rows.length; i++) {
		rows[i].style.backgroundColor = "";
	}
	player1.innerHTML = "";
	player2.innerHTML = "";
	userMatch = [];
}

// EVENTLISTENERS
filterButton.addEventListener("click", () => {
	loadplayerlist();
});

clearButton.addEventListener("click", () => {
	filterValue.value = "";
	loadplayerlist();
});
resetButton.addEventListener("click", resetClick);

startButton.addEventListener("click", startGame);

filterValue.addEventListener("keypress", function (event) {
	if (event.key === "Enter") {
		event.preventDefault();
		filterButton.click();
	}
});

loadplayerlist();
