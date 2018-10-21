"use strict";
let eventBus = (function () {
    class EventBus {
        constructor() {
            this.events = new Map();
        }

        addEvent(event, fn) {
            let eventActions = this.events.get(event) || [];
            eventActions.push(fn);
            this.events.set(event, eventActions);
        }

        removeEvent(event, fn) {
            let eventActions = this.events.get(event) || [];
            this.events.set(event, eventActions.filter((action) => {
                return action != fn
            }));
        }

        fire(event, args) {
            let eventActions = this.events.get(event) || [];
            eventActions.forEach((action) => {
                action && action(args);
            })
        }
    }

    return new EventBus();
})();

let GameEvents = {
    ShowChar: "showChar",
    ShowResult: "showResult",
    GameStart: "gameStart",
    GameComplete: "gameComplete"
};

let GameSystem = (function (eventBus) {
    let SymbolCharInput = {
        MISSING: 'miss',
        INPUT_CORRECT: 'inputCorrect',
        CORRECT: 'correct',
        WRONG: 'wrong'
    };

    class GameSystem {
        constructor() {
            this.setupSettings();
        }

        setupSettings(settings) {
            this._gameData = new GameData(settings);
            this.reset();
            this.gameController = new GameController(this._gameData);
        }

        getSettings() {
            return this._gameData.settings;
        }

        reset() {
            this.gameController && this.gameController.reset();
            this._gameData.generateSymbol();
        }

        input() {
            this.gameController.input();
        }

        play() {
            this.gameController.play();
        }

        replay() {
            this.reset();
            this.play();
        }
    }

    class GameData {
        constructor(settings) {
            settings = settings || {};
            settings.swtichDelay = 200; //display each char delay time, unit millisecond.

            settings.block_n = settings.block_n || 2;
            settings.delay = settings.delay || 3000;//the display time, unit millisecond.
            settings.symbolLength = settings.symbolLength || 20; //the length of random symbol

            this._settings = settings;
            this._minBlockFactor = 1 / 5;
            this._maxBlockFactor = 1 / 3;
            this.inputSet = new Set();

            this.inputResultMap = new Map();

            this.generateSymbol();
        }

        get settings() {
            return this._settings;
        }

        set settings(_settings) {
            this._settings = _settings;
        }

        generateSymbol() {
            let count = this._settings.symbolLength;
            let blockN = this._settings.block_n;
            let minBlockCount = Math.floor(count * this._minBlockFactor);
            let maxBlockCount = Math.ceil(count * this._maxBlockFactor);
            let blockCount = random(minBlockCount, maxBlockCount);
            let blockSet = new Set();
            while (blockCount > 0) {
                let nextBlock = random(blockN, count);
                if (!blockSet.has(nextBlock)) {
                    blockSet.add(nextBlock);
                    blockCount--;
                }
            }
            let symbol = "";
            for (let idx = 0; idx < count; idx++) {
                if (idx < blockN || !blockSet.has(idx)) {
                    symbol += randomChar();
                } else {
                    symbol += symbol.charAt(idx - blockN);
                }
            }
            this.symbol = {code: symbol, index: -1};
            return symbol;

            function randomChar() {
                //A-Z: 65-90
                return String.fromCharCode(65 + random(0, 26));
            }

            //[min, max)
            function random(min, max) {
                return min + Math.floor(Math.random() * (max - min))
            }
        }

        hasNextChar() {
            return this._settings.symbolLength > this.symbol.index;
        }

        readChar(index) {
            if (this._settings.symbolLength < index) {
                return;
            }
            this.symbol.index = index;
            return this.symbol.code.charAt(index);
        }

        input() {
            let index = this.symbol.index;
            this.inputSet.add(index);

            this.inputResultMap.set(index, this.isN_Block(index));//todo
        }

        isN_Block(index) {
            let code = this.symbol.code;
            let settings = this._settings;
            if (settings.block_n > index) {
                return false;
            }
            return code.charAt(index) === code.charAt(index - settings.block_n);
        }

        showResult() {
            let symbolInputMap = this.calcSymbolInput();
            let result = [this.showGameResult(symbolInputMap)];
            result.push(this.showInput(symbolInputMap));
            result.push(this.showCorrectResult());
            return result.join("<br><br>");
        }

        calcSymbolInput() {
            let inputSet = this.inputSet;
            let code = this.symbol.code;
            let symbolInputMap = new Map();
            for (let idx = 0; idx < code.length; idx++) {
                let isBlock = this.isN_Block(idx);
                let result = SymbolCharInput.WRONG;
                let hasInput = inputSet.has(idx);
                if (isBlock && !hasInput) {
                    result = SymbolCharInput.MISSING;
                } else if (isBlock && hasInput) {
                    result = SymbolCharInput.INPUT_CORRECT;
                } else if (!isBlock && !hasInput) {
                    result = SymbolCharInput.CORRECT;
                }
                symbolInputMap.set(idx, result);
            }
            return symbolInputMap;
        }

        showInput(symbolInputMap) {
            let result = ["显示结果：<br>"];
            let code = this.symbol.code;
            for (let idx = 0; idx < code.length; idx++) {
                let inputResult = symbolInputMap.get(idx);
                switch (inputResult) {
                    case SymbolCharInput.CORRECT:
                        result.push(code.charAt(idx));
                        break
                    case SymbolCharInput.INPUT_CORRECT:
                        result.push(`<B>${code.charAt(idx)}</B>`);
                        break;
                    default:
                        result.push(`<E>${code.charAt(idx)}</E>`);
                        break;
                }
            }
            return result.join(" ");
        }

        showCorrectResult() {
            let result = ["显示正确答案：<br>"];
            let code = this.symbol.code;
            for (let i = 0; i < code.length; i++) {
                result.push(this.isN_Block(i) ? `<B>${code.charAt(i)}</B>` : code.charAt(i));
            }
            return result.join(" ");
        }

        showGameResult(symbolInputMap) {
            let code = this.symbol.code;
            let errorArray = Array.from(symbolInputMap).filter((array) => {
                return array[1] !== SymbolCharInput.CORRECT && array[1] !== SymbolCharInput.INPUT_CORRECT;
            });
            if (errorArray.length > 0) {
                let result = [];
                errorArray.forEach((array) => {
                    let idx = array[0];
                    result.push(code.charAt(idx));
                });
                return "以下字符输入有误：<br>" + result.join(", ");
            }

            return "恭喜全部答对了!"
        }
    }

    class GameController {
        constructor(gameData) {
            this._gameData = gameData;
            this._timer = null;
        }

        input() {
            this._gameData.input();
        }

        isGameOver() {
            return false;
        }

        _stepPlay() {
            let gameData = this._gameData;
            let symbol = gameData.symbol;
            symbol.index++;
            if (this.isGameOver() || !gameData.hasNextChar()) {
                eventBus.fire(GameEvents.ShowResult, gameData.showResult());
                clearInterval(this._timer);
                return;
            }
            if (gameData.hasNextChar()) {
                let char = gameData.readChar(symbol.index);
                eventBus.fire(GameEvents.ShowChar, char);
            }
        }

        play() {
            eventBus.fire(GameEvents.GameStart);

            let gameData = this._gameData;
            let settings = gameData.settings;
            getReady(()=>{
                this._stepPlay();
                this._timer = setInterval(() => {
                    this._stepPlay();
                }, settings.delay);
            });

            function getReady(timerFunc) {
                let readyStr = ["3", "2", "1"];
                let idx = 1;
                let size = readyStr.length;
                eventBus.fire(GameEvents.ShowChar, getMessage(readyStr[0]));
                let timer = setInterval(()=> {
                    if(size <= idx) {
                        clearInterval(timer);
                        timerFunc();
                        return;
                    }
                    eventBus.fire(GameEvents.ShowChar, getMessage(readyStr[idx]));
                    idx++;
                }, 1000);

                function getMessage(msg) {
                    return `<span style="color: red;">${msg}</span>`;
                }
            }
        }

        reset() {
            clearInterval(this._timer);
            this._timer = null;
        }
    }

    return GameSystem;
})(eventBus);