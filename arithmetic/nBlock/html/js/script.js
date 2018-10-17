// https://github.com/Reactive-Extensions/RxJS

let game = {
    init() {
        this.codeCount = 10;
        this.dealy = 2000;//the display time, unit second.
        this.state = 0;
        this.inputResult = new Set();
        this.idx = 0;
    },

    generateCode() {
        this.code = "abccjkjkeeiit";
    },

    read(idx) {
        if (this.code.length < idx) {
            return;
        }
        console.log(this.code.charAt(idx));
        this.idx = idx;
        return this.code.charAt(idx);
    },
    hasNext() {
        return this.code.length > this.idx;
    },
    isGameOver() {
      return false;
    },

    play() {
        let timer = setInterval(() => {
            if(this.isGameOver() || !this.hasNext()) {
                this.showResult();
                clearInterval(timer);
                return;
            }

            console.log(1);
            if(this.hasNext()) {
                let char = this.read(this.idx);
                this.idx++;
                document.getElementById("message").innerText = char;
            }
        }, this.dealy);
    },
    input() {
        this.inputResult.add(this.idx);
    },

    showResult() {
      this.calcResult();
    },

    calcResult() {
        let result = ["显示结果：<br>"];
        let inputArray = Array.from(this.inputResult);
        for(let i = 0; i< this.code.length; i++) {
            result.push(inputArray.includes(i) ? `<b>${this.code.charAt(i)}</b>` : this.code.charAt(i));
        }
        document.getElementById("result").innerHTML = result.join(" ");
    }
};

window.onload = function () {
    game.init();
    game.generateCode();
    game.play();
    document.onkeypress = function () {
        game.input();
        console.log(111);
    };
}
