"use strict";

let game = new GameSystem("message", "result");

window.onload = function () {
    let settings = game.getSettings();
    eventBus.addEvent(GameEvents.GameStart, () => {
        switchGameButtonGroup(true);
        document.getElementById("result").classList.remove("show");
    });
    eventBus.addEvent(GameEvents.ShowChar, (char) => {
        let charElement = document.getElementById("message");
        charElement.innerText = "";
        setTimeout(() => {
            charElement.innerText = char;
        }, settings.swtichDelay);
    });
    eventBus.addEvent(GameEvents.ShowResult, (result) => {
        switchGameButtonGroup(false);
        let resultElement = document.getElementById("result");
        resultElement.classList.add("show");
        resultElement.innerHTML = result;
    });
    document.onkeypress = game.input.bind(game);

    function switchGameButtonGroup(isPlaying) {
        triggerClassByClass("playingStatus", "show", isPlaying);
        triggerClassByClass("resultStatus", "show", !isPlaying);
    }
};

let triggerClassByClass = function (eleClass, targetClass, trigger) {
    Array.from(document.getElementsByClassName(eleClass)).forEach((ele) => {
        if (trigger) {
            ele.classList.add(targetClass);
        } else {
            ele.classList.remove(targetClass);
        }
    });
};

let rePlay = function () {
    let charElement = document.getElementById("message");
    charElement.innerText = "";
    game.replay();
};

let showSettings = function () {
    triggerClassByClass("result", "show", false);
    triggerClassByClass("settings", "show", true);
    triggerClassByClass("resultStatus", "show", false);
};

let submitSettings = function () {
    let settings = game.getSettings();
    let symbolLength = getValueById("symbolLength") || settings.symbolLength;
    let blockN = getValueById("blockN") || settings.block_n;
    let delay = getValueById("delay") || settings.delay;
    game.setupSettings({
        symbolLength: parseInt(symbolLength),
        block_n: parseInt(blockN),
        delay: parseInt(delay)
    });

    triggerClassByClass("settings", "show", false);
    triggerClassByClass("resultStatus", "show", true);

    function getValueById(eleId) {
        return document.getElementById(eleId).value;
    };
};
