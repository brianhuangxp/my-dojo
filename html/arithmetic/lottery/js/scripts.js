(function() {
    let running = false;
    const rotate = 30;
    const eachDuration = 40;
    const itemTemplate = '<div class="layer"><div class="item">%label%</div><div class="amount">X%amount%</div></div>';
    let data = [];
    let currentOffset = 0;
    onload = onLoad;

    function onLoad() {
        getJson("data.json", function (rsp) {
            data = rsp;
        });
        renderItems(data);
        $(".rotate-btn").click(onclick);
    }

    function onclick() {
        if (!running) {
            running = true;
            let next = calcNextItem(data);
            let rotateElement = $(".rotation");
            rotateElement.css('transform', `rotate(${next.rotate}deg)skewX(60deg)`);
            rotateElement.css('transition-duration', `${next.duration}ms`);
            setTimeout(function () {
                currentOffset = currentOffset % data.length;
                rotateElement.css('transition-duration', `0s`);
                rotateElement.css('transform', `rotate(${rotate * (currentOffset + 1)}deg)skewX(60deg)`);
                running = false;
                showMessage(next.item);
            }, next.duration);
        }
    }

    function showMessage(item, callback) {
        $("#message").text(`${item.label}X${item.amount}`);
        let quote = $(".quote");
        quote.toggleClass("show", true);
        setTimeout(()=>{
            quote.toggleClass("show", false);
            $.isFunction(callback) && callback();
        }, 3000)
    }

    function renderItems(data) {
        let itemListElement = document.getElementById("itemList");
        let html =[];
        data.forEach(item =>{
            let label = item.label;
            let amount = item.amount;
            html.push(itemTemplate.replace('%label%', label).replace('%amount%', amount));
        });
        itemListElement.outerHTML = html.join("");
    }

    function getJson(url, onSuccess) {
        $.ajax({
            contentType: "application/json",
            type: 'Get',
            async: false,
            url: url,
            dataType: "json",
            timeout: 30000,
            created: Date.now(),
            success: function (rsp) {
                $.isFunction(onSuccess) && onSuccess(rsp);
                onSuccess && $.isFunction(onSuccess.then) && onSuccess.then(rsp);
            }
        });
    }

    function calcNextItem(data) {
        let item = nextItem(data);
        let curIdx = currentOffset;
        let nextIdx = data.indexOf(item);
        let offset = nextIdx;
        if (nextIdx <= curIdx) {
            offset = offset + data.length;
        }
        offset += nextInt(1, 3) * data.length;
        currentOffset = offset;
        let result = {
            rotate: rotate * (offset + 1),
            duration: eachDuration * offset,
            item: item
        };
        console.log(item);
        return result;
    }

    function nextItem(data) {
        let totalWeight = 0;
        let offset = 0;
        data.forEach(item=>{
            totalWeight += item.weight;
        });
        let next = nextInt(0, totalWeight);
        for(let idx = 0; idx < data.length; idx++) {
            let item = data[idx];
            next -= item.weight;
            if (next <= 0) {
                return item;
            }
        }
    }

    function nextInt(min, max) {
        let between = max - min;
        let random = Math.round(Math.random()*between);
        return random + min;
    }
})();