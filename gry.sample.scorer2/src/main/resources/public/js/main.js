var api = 'http://localhost:8080/api/';

function getScore() {
    console.log("getScore()");
    axios.get(api + 'match').then(function (response) {
        var matchData = response.data;
        showScore(matchData);
    }).catch(function (reason) {
        console.error(reason);
    });
}

function majorReset() {
    console.log("majorReset()");
    hideScoreBoard();
    resetMatchSetup();
    activateKeyListener();
}

function activateKeyListener() {
    $(document).keypress(function (event) {
        var key = event.key.toUpperCase();
        console.log("keypress(e):", key);
        if(matchIsRunning() && (key=='A' || key=='B')) {
            score(key);
        }
    });
}

function matchIsRunning() {
    return $("#score-board").is(':visible');
}

function startNewMatch() {
    location.reload();
    $("#player-a").focus();
}

function startMatch() {
    var matchSetup = getMatchSetup();
    console.log("startMatch()", matchSetup);
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
    if(matchData.over == true) {
        showMatchOver(matchData);
    }
    else {
        var sets = matchData.terminatedSets;
        sets.push(matchData.currentSet);
        showSetScores(sets);
        showCurrentScoreUnit(matchData.currentSet.currentScoreUnit);
    }
}

function showSetScores(sets) {
    sets.forEach(showScoreOfSet);
}

function showScoreOfSet(set, index) {
    var setNo = index+1;
    $("#set" + setNo + "-score-a").text(set.scoreA);
    $("#set" + setNo + "-score-b").text(set.scoreB);
    if(isTiebreak(getLastScoringUnit(set))) {
        showTiebreakScore(set, setNo);
    }
}

function showCurrentScoreUnit(currentScoreUnit) {
    console.log("showCurrentScoreUnit():", currentScoreUnit);
    $("#scoreUnitLabel").text(currentScoreUnit.tiebreak ? "Tiebreak" : "Game");
    $("#current-game-score-a").text(currentScoreUnit.scoreA);
    $("#current-game-score-b").text(currentScoreUnit.scoreB);
}


function showTiebreakScore(set, setNo) {
    var lastScoringUnit = getLastScoringUnit(set);
    var statusCell = $("#status-set"+ setNo);
    statusCell.text(isTiebreak(lastScoringUnit) ?
        lastScoringUnit.scoreA + "-" + lastScoringUnit.scoreB:
        "");
    console.log("showTiebreak()", statusCell);
    statusCell.attr("class", "col bg-score-game seg-14-sm border");
}

function resetScoreBoard() {
    var setNumbers = [1, 2, 3, 4, 5];
    setNumbers.forEach(function(setNo) {
        $("#set" + setNo + "-score-a").text("");
        $("#set" + setNo + "-score-b").text("");
        $("#status-set"+ setNo).text("");
        $("#status-set"+ setNo).attr("class", "col");
    });
    resetGameScore();
    $("#status-game").attr("class", "col-3");
    resetScoreButtons();
}

function resetGameScore() {
    $("#current-game-score-a").text("");
    $("#current-game-score-b").text("");
}

function getMatchSetup() {
    var setup = {};
    setup.matchMode = getMatchMode();
    setup.tiebreakMode = getTiebreakMode();
    setup.playerA = $("#player-a").val();
    setup.playerB = $("#player-b").val();
    return setup;
}

function resetMatchSetup() {
    console.log("resetMatchSetup()");
    $("#player-a").val("");
    $("#player-b").val("");
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
    $("#score-btn-a").text(playerA=="" ? "Player A" : playerA);
    $("#score-btn-b").text(playerB=="" ? "Player B" : playerB);
}

function getMatchMode() {
    return $('input:radio[name=match-mode]:checked').val();
}

function getTiebreakMode() {
    return $('input:radio[name=tiebreak-mode]:checked').val();
}

function showMatchOver(matchData) {
    console.log("showMatchOver()", matchData);
    showSetScores(matchData.terminatedSets);
    resetGameScore();
    showWinner(matchData.winner);
    disableScoreButtons();
}

function showWinner(winner) {
    $("#current-game-score-" + winner.toLowerCase()).text("WINNER");
    if(winner == "A") {
        $("#score-btn-b").attr("class", "btn-lg btn-warning w-100");
    }
    else {
        $("#score-btn-a").attr("class", "btn-lg btn-warning w-100");
    }
}

function disableScoreButtons() {
    $("#score-btn-a").prop('disabled', true);
    $("#score-btn-b").prop('disabled', true);
}

function resetScoreButtons() {
    $("#score-btn-a").prop('disabled', false);
    $("#score-btn-b").prop('disabled', false);
    $("#score-btn-a").attr("class", "btn-lg btn-info w-100");
    $("#score-btn-b").attr("class", "btn-lg btn-info w-100");
}

function getNameOfPlayer(aOrB) {
    return (aOrB == "A") ? $("#score-btn-a").text() : $("#score-btn-b").text();
}

function showScoreBoard() {
    console.log("showScoreBoard()");
    $("#score-board").show();
    $("#terminate-bar").show();
}

function hideScoreBoard() {
    console.log("hideScoreBoard()");
    $("#score-board").hide();
    $("#terminate-bar").hide();
}

function hideMatchSetupPanel() {
    $("#match-setup").hide();
}
