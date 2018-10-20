// https://github.com/Reactive-Extensions/RxJS

let game = {
    init() {
        this.codeCount = 10;
        this.dealy = 1000;//the display time, unit second.
        this.state = 0;
        this.inputResultMap = new Map();
        this.idx = -1;
        this.n = 1;
    },

    generateCode() {
        //this.code = "abccjkjkeeiit";
        this.code = "abccede";
    },

    read(idx) {
        if (this.code.length < idx) {
            return;
        }
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
            this.idx++;
            if(this.isGameOver() || !this.hasNext()) {
                this.showResult();
                clearInterval(timer);
                return;
            }
            if(this.hasNext()) {
                let char = this.read(this.idx);
                this.showChar(char);
            }
        }, this.dealy);
    },
    showChar(char) {
        document.getElementById("message").innerText = "";
        setTimeout(()=>{
            document.getElementById("message").innerText = char;
        }, 200);
    },
    input() {
        this.inputResultMap.set(this.idx, this.isN_Block(this.idx));
    },
    isN_Block(idx) {
        if (this.n > idx) {
            return false;
        }
        return this.code.charAt(idx) === this.code.charAt(idx - this.n);
    },

    showResult() {
        let result = [this.showInput()];
        result.push(this.showCorrectResult());
        result.push(this.showGameResult());
        document.getElementById("result").innerHTML = result.join("<br><br>");
    },
    showInput () {
        let result = ["显示结果：<br>"];
        let inputResultMap = this.inputResultMap;
        for(let i = 0; i< this.code.length; i++) {
            if(inputResultMap.has(i)) {
                let rspElement = inputResultMap.get(i) ? "B" : "E";
                result.push(`<${rspElement}>${this.code.charAt(i)}</${rspElement}>`);
            } else {
                result.push(this.code.charAt(i));
            }
        }
        return result.join(" ");
    },
    showCorrectResult () {
        let result = ["显示正确答案：<br>"];
        for(let i = 0; i< this.code.length; i++) {
            result.push(this.isN_Block(i) ? `<B>${this.code.charAt(i)}</B>` : this.code.charAt(i));
        }
        return result.join(" ");
    },
    showGameResult() {
        let errorArray = Array.from(this.inputResultMap).filter((array)=>{return array[1] === false;});
        if(errorArray.length > 0) {
            let result = [];
            errorArray.forEach((array)=>{
                let idx = array[0];
                result.push(this.code.charAt(idx));
            });
            return "以下字符输入有误：<br>" + result.join(", ");
        }

        return "恭喜全部答对了"
    }
};

window.onload = function () {
    game.init();
    game.generateCode();
    game.play();
    document.onkeypress = function () {
        game.input();
    };
}
