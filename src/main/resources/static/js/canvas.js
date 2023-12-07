const canvas = document.querySelector(".canvas");
const textCanvas = document.querySelector(".textCanvas")
const ctx = canvas.getContext("2d");
const colors = document.querySelector("#colorPicker");
const range = document.getElementById("jsRange");
const saveBtn = document.getElementById("jsSave");
const check = document.getElementById("jsCheck");
const resultCanvas = document.querySelector('#resultCanvas');
const resultCtx = resultCanvas.getContext("2d");
const textarea = document.querySelector("#textarea");
const textCtx = textCanvas.getContext("2d");
const fontSize = document.querySelector("#font-size");
const datepicker = document.querySelector(".datepicker");

datepicker.addEventListener("click", e => {
    e.stopPropagation();
})

datepicker.addEventListener('change', e => {
    const now = new Date.now();
    const target = e.target.value;
    console.log("now=" + now + ",target=" + target);
})

function vh(v) {
    var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
    return (v * h) / 100;
}

function vw(v) {
    var w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
    return (v * w) / 100;
}

canvas.width = vw(70);
canvas.height = vh(70);
textCanvas.width = vw(70);
textCanvas.height = vh(70);
textCtx.fillStyle = "rgba(0,0,0,0)";
textCtx.fillRect(0, 0, textCanvas.width, textCanvas.height);
textCtx.fillStyle = "black";
resultCanvas.width = vw(70);
resultCanvas.height = vh(70);

ctx.strokeStyle = "#000000"
ctx.lineWidth = 0.5;
ctx.fillStyle = "white";
ctx.fillRect(0, 0, canvas.width, canvas.height);

let painting = false;

function startPainting(event) {
    BodyScrollDisAble();

    painting = true;
}

function stopPainting(event) {
    BodyScrollAble();
    painting = false;
}

function onMouseMove(event) {
    const x = event.offsetX;
    const y = event.offsetY;
    if (!painting) {
        ctx.beginPath();
        ctx.moveTo(x, y);
    } else {
        ctx.lineTo(x, y);
        ctx.stroke();
    }
}

// function getMobilePosition(evt){
//     var x = evt.originalEvent.changedTouches[0].pageX - canvas.offset().left;
//     var y = evt.originalEvent.changedTouches[0].pageY - canvas.offset().top;
//     return {X:x, Y:y};
//  }; 
function getMobilePosition(evt) {
    var x = evt.changedTouches[0].pageX - (window.pageYOffset + canvas.getBoundingClientRect().left);

    var y = evt.changedTouches[0].pageY - (window.pageYOffset + canvas.getBoundingClientRect().top);

    return {X: x, Y: y};
};

function onTouchMove(event) {
    const x = getMobilePosition(event).X;
    const y = getMobilePosition(event).Y;
    if (!painting) {
        ctx.beginPath();
        ctx.moveTo(x, y);
    } else {
        ctx.lineTo(x, y);
        ctx.stroke();
    }
}

function startPaintingMobile(event) {
    BodyScrollDisAble();
    const x = getMobilePosition(event).X;
    const y = getMobilePosition(event).Y;
    ctx.beginPath();
    ctx.moveTo(x, y);
    painting = true;
}

function stopPaintingMobile(event) {
    BodyScrollAble();
    const x = getMobilePosition(event).X;
    const y = getMobilePosition(event).Y;
    ctx.lineTo(x, y);
    ctx.stroke();
    painting = false;
}

function BodyScrollDisAble() {
    document.body.style.overflow = "hidden";
};

function BodyScrollAble() {
    document.body.style.overflow = "auto";
};

if (canvas) {
    canvas.addEventListener("mousemove", onMouseMove);
    canvas.addEventListener("mousedown", startPainting);
    canvas.addEventListener("mouseup", stopPainting);
    canvas.addEventListener("mouseleave", stopPainting);

    canvas.addEventListener("touchstart", startPaintingMobile);
    canvas.addEventListener("touchend", stopPaintingMobile);
    canvas.addEventListener("touchmove", onTouchMove);
}

//color pick
function handleColorClick(event) {
    const color = colors.value;
    ctx.strokeStyle = color;
}

colors.addEventListener("input", handleColorClick);
// Array.from(colors).forEach(color => color.addEventListener("click", handleColorClick));

//brush size
function handleRangeChange(event) {
    const size = event.target.value;
    ctx.lineWidth = size;
}

if (range) {
    range.addEventListener("input", handleRangeChange);
}
;

//save
// function handleSaveClick() {
//     resultCtx.putImageData(ctx.getImageData(0,0,canvas.width,canvas.height),0,0);
//     drawText(resultCanvas);
//     const image = resultCanvas.toDataURL('image/png', 1.0);
//     const link = document.createElement("a");
//     link.href = image;
//     link.download = "PaintJS";
//     link.click();
// }
function handleSaveClick() {
    const deadline_value = document.querySelector("#deadline_value").value;
    if (deadline_value == null || deadline_value == "") {
        alert("오픈 날짜를 입력해주세요!");
        return;
    }

    const nowDate = new Date().getTime();
    const targetDate = new Date(deadline_value).getTime();
    if (targetDate <= nowDate) {
        alert("미래를 선택해주세요");
        return;
    }

    resultCtx.putImageData(ctx.getImageData(0, 0, canvas.width, canvas.height), 0, 0);
    drawText(resultCanvas);
    const imgDataUrl = resultCanvas.toDataURL('image/png');
    const blobBin = atob(imgDataUrl.split(',')[1]);
    let array = [];
    for (let i = 0; i < blobBin.length; i++) {
        array.push(blobBin.charCodeAt(i));
    }
    const file = new Blob([new Uint8Array(array)], {type: 'imag/png'});
    const formData = new FormData();
    // formData.append("content",imgDataUrl);
    formData.append("content", file);
    formData.append("deadline", deadline_value);
    formData.append("width", resultCanvas.width);
    formData.append("height", resultCanvas.height);

    $.ajax({
        type: 'post',
        url: '/message',
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log(data);
            alert("오픈시간이되면 이메일로 알려드리겠습니다😀");
            window.location = "/";
        }
    })
}


function handleCM(event) {
    event.preventDefault();
}

if (canvas) {
    canvas.addEventListener("contextmenu", handleCM)
}

if (saveBtn) {
    saveBtn.addEventListener("click", handleSaveClick);
}

//load font
const f = new FontFace('LeeSeoyun', 'url(../font/LeeSeoyun.ttf)');

//text canvas
const drawText = (targetCanvas) => {
    let targetCtx = targetCanvas.getContext("2d");
    targetCtx.font = `${fontSize.value}px LeeSeoyun`;
    if (targetCanvas !== resultCanvas) targetCtx.clearRect(0, 0, targetCanvas.width, targetCanvas.height);
    const textList = textarea.value.split('\n');
    const xOffset = fontSize.value;
    const maxWidth = targetCanvas.width - xOffset * 2;
    const height = parseInt(fontSize.value) + 5;
    const heightOffset = parseInt(fontSize.value) + 10;
    let heightIndex = 0;
    for (let i = 0; i < textList.length; i++) {
        let line = "";
        for (let j = 0; j < textList[i].length; j++) {
            const width = targetCtx.measureText(line + textList[i][j]).width;
            if (width < maxWidth) {
                line += textList[i][j];
            } else {
                targetCtx.fillText(line, xOffset, heightOffset + height * heightIndex++);
                line = textList[i][j];
            }
        }
        if (line != "") {
            targetCtx.fillText(line, xOffset, heightOffset + height * heightIndex++);
        }
    }
}
textarea.addEventListener("keyup", e => {
    drawText(textCanvas);
})
fontSize.addEventListener("change", e => {
    drawText(textCanvas);
})

//펜 지우개 toggle button
const radioPen = document.querySelector("#pen");
const radioEraser = document.querySelector("#eraser");
const labelPen = document.querySelector("#pen-label")
const labelEraser = document.querySelector("#eraser-label");


radioPen.addEventListener("click", e => {
    labelPen.classList.add("btn-secondary");
    labelEraser.classList.remove("btn-secondary");
    radioEraser.checked = false;
    radioPen.checked = true;
    ctx.strokeStyle = colors.value;
})

radioEraser.addEventListener("click", e => {
    labelEraser.classList.add("btn-secondary");
    labelPen.classList.remove("btn-secondary");
    radioPen.checked = false;
    radioEraser.checked = true;
    ctx.strokeStyle = "#ffffff"
})