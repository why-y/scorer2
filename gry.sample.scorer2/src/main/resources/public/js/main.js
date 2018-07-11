var api = 'http://localhost:8080/api/';

var matchSetup = {
    matchMode: "best-of-3",
    tiebreakMode: "in-all-sets"
};

function getScore() {
    console.log("getScore()");
    axios.get(api + 'match').then(function (response) {
        var matchData = response.data;
        showScore(matchData);
    }).catch(function (reason) {
        console.error(reason);
    });
}

function startNewMatch() {
    location.reload();
}

function startMatch() {
    var matchSetup = getMatchSetup();
    setPlayerNames(matchSetup.playerA, matchSetup.playerB);
    console.log("matchSetup: ", matchSetup);
    axios.post(api + 'match', matchSetup).then(function (value) {
        resetScoreBoard();
        hideMatchSetupPanel();
        showScoreBoard();
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

function showScore(matchData) {
    console.log('showScore()', matchData, matchData.terminatedSets);
    var sets = matchData.terminatedSets;
    if(matchData.over == true) {
        console.log("##### GAME OVER #####");
        resetGameScore();
        disableScoreButtons();
    }
    else {
        sets.push(matchData.currentSet);
        showCurrentScoreUnit(matchData.currentSet.currentScoreUnit);
    }
    showSetScores(sets);
}

function showSetScores(sets) {
    sets.forEach(showScoreOfSet);
}

function showScoreOfSet(set, index) {
    var setNo = index+1;
    document.getElementById("set" + setNo + "-score-a").innerHTML = set.scoreA;
    document.getElementById("set" + setNo + "-score-b").innerHTML = set.scoreB;
    if(isTiebreak(getLastScoringUnit(set))) {
        showTiebreakScore(set, setNo);
    }
}

function showCurrentScoreUnit(currentScoreUnit) {
    console.log("showCurrentScoreUnit():", currentScoreUnit);
    document.getElementById("scoreUnitLabel").innerHTML = currentScoreUnit.tiebreak ? "Tiebreak" : "Game";
    document.getElementById("current-game-score-a").innerHTML = currentScoreUnit.scoreA;
    document.getElementById("current-game-score-b").innerHTML = currentScoreUnit.scoreB;
}


function showTiebreakScore(set, setNo) {
    var lastScoringUnit = getLastScoringUnit(set);
    var statusCell = document.getElementById("status-set"+ setNo);
    statusCell.innerHTML = isTiebreak(lastScoringUnit) ?
        "(" +lastScoringUnit.scoreA + ":" + lastScoringUnit.scoreB + ")":
        "";
    console.log("showTiebreak()", statusCell);
    statusCell.setAttribute("class", "col bg-success border");
}

function resetScoreBoard() {
    var setNumbers = [1, 2, 3, 4, 5];
    setNumbers.forEach(function(setNo) {
        document.getElementById("set" + setNo + "-score-a").innerHTML = "";
        document.getElementById("set" + setNo + "-score-b").innerHTML = "";
        document.getElementById("status-set"+ setNo).setAttribute("class", "col");
    });
    resetGameScore();
    document.getElementById("status-game").setAttribute("class", "col-2");
    enableScoreButtons();
}

function resetGameScore() {
    $("#current-game-score-a").html("");
    $("#current-game-score-b").html("");
}

function getMatchSetup() {
    var setup = {};
    setup.matchMode = getMatchMode();
    setup.tiebreakMode = getTiebreakMode();
    setup.playerA = document.getElementById("player-a").value;
    setup.playerB = document.getElementById("player-b").value;
    return setup;
}

function showScoreBoard() {
    document.getElementById("score-board").removeAttribute("hidden");
    document.getElementById("terminate-bar").removeAttribute("hidden");
}

function hideScoreBoard() {
    document.getElementById("score-board").setAttribute("hidden", true);
    document.getElementById("terminate-bar").setAttribute("hidden", true);
}

function hideMatchSetupPanel() {
    document.getElementById("match-setup").setAttribute("hidden", true);
}

function isTiebreak(scoringUnit) {
    console.log("isTiebreak():" + scoringUnit, scoringUnit!=null);
    return scoringUnit!=null ? scoringUnit.tiebreak : false;
}

function getLastScoringUnit(set) {
    console.log("getLastScoreUnit():", set);
    var scoringUnits = set.terminatedScoreUnits;
    console.log(" --- terminatedScoreUnits:", scoringUnits, scoringUnits.length, scoringUnits[scoringUnits.length-1]);
    return scoringUnits.length==0 ? null : scoringUnits[scoringUnits.length-1];
}

function setPlayerNames(playerA, playerB) {
    $("#score-btn-a").text(playerA);
    $("#score-btn-b").text(playerB);
}

function getMatchMode() {
    var matchModeRadios = document.getElementsByName("match-mode");
    var result = "N/A";
    matchModeRadios.forEach(function (radio) {
        if(radio.checked) {
            result = radio.value;
        }
    });
    return result;
}

function getTiebreakMode() {
    var tiebreakModeRadios = document.getElementsByName("tiebreak-mode");
    var result = "N/A";
    tiebreakModeRadios.forEach(function (radio) {
        if(radio.checked) {
            result = radio.value;
        }
    });
    return result;
}

function disableScoreButtons() {
    $("#score-btn-a").prop('disabled', true);
    $("#score-btn-b").prop('disabled', true);
}

function enableScoreButtons() {
    $("#score-btn-a").prop('disabled', false);
    $("#score-btn-b").prop('disabled', false);
}
