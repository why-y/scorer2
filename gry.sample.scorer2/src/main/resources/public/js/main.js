var api = 'http://localhost:8080/api/';


function getScore() {
    console.log("getScore()");
    axios.get(api + 'match').then(function (response) {
        console.log(response.data);
        showScore(response.data);
    }).catch(function (reason) {
        console.error(reason);
    });
}

function startMatch() {
    console.log("startMatch()");
    axios.post(api + 'match', {}).then(function (value) {
        console.log(value);
        getScore();
    }).catch(function (reason) {
        console.error(reason);
    });
}

function score(player) {
    console.log('score(' + player + ')');
    axios.post(api + 'match/rally', {'Player': player}).then(function (value) {
        console.log(value);
        getScore();
    }).catch(function (reason) {
        console.error(reason);
    });
}

function showScore(value) {
    document.getElementById("score").innerHTML = value;
}