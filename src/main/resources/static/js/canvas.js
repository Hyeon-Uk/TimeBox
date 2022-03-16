const canvas = document.querySelector(".canvas");
const textCanvas=document.querySelector(".textCanvas")
const ctx = canvas.getContext("2d");
const colors=document.querySelector("#colorPicker");
const range = document.getElementById("jsRange");
const saveBtn = document.getElementById("jsSave");
const check = document.getElementById("jsCheck");
const resultCanvas=document.querySelector('#resultCanvas');
const resultCtx=resultCanvas.getContext("2d");
const textarea=document.querySelector("#textarea");
const textCtx=textCanvas.getContext("2d");
const fontSize=document.querySelector("#font-size");
const datepicker=document.querySelector(".datepicker");

datepicker.addEventListener("click",e=>{
    e.stopPropagation();
})

datepicker.addEventListener('change',e=>{
    const now=new Date.now();
    const target=e.target.value;
    console.log("now="+now+",target="+target);
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
textCanvas.width=vw(70);
textCanvas.height=vh(70);
textCtx.fillStyle = "rgba(0,0,0,0)";
textCtx.fillRect(0, 0, textCanvas.width, textCanvas.height);
textCtx.fillStyle="black";
resultCanvas.width=vw(70);
resultCanvas.height=vh(70);

ctx.strokeStyle = "#2c2c2c"
ctx.lineWidth = 0.5;
ctx.fillStyle = "white";
ctx.fillRect(0, 0, canvas.width, canvas.height);

let painting = false;

function startPainting(event) {
    painting = true;
}

function stopPainting(event) {
    painting = false;
}

function onMouseMove(event) {
    const x = event.offsetX;
    const y = event.offsetY;
    if(!painting) {
        ctx.beginPath();
        ctx.moveTo(x, y);
    } else {
        ctx.lineTo(x, y);
        ctx.stroke();
    }
}

if (canvas) {
    canvas.addEventListener("mousemove", onMouseMove);
    canvas.addEventListener("mousedown", startPainting);
    canvas.addEventListener("mouseup", stopPainting);
    canvas.addEventListener("mouseleave", stopPainting);

    canvas.addEventListener("touchstart",startPainting);
    canvas.addEventListener("touchend",stopPainting);
    canvas.addEventListener("touchmove",onMouseMove);
}

//color pick
function handleColorClick(event) {
    const color = colors.value;
    console.log(color);
    ctx.strokeStyle = color;
}

colors.addEventListener("input",handleColorClick);
// Array.from(colors).forEach(color => color.addEventListener("click", handleColorClick));

//brush size
function handleRangeChange(event) {
    const size = event.target.value;
    ctx.lineWidth = size;
}

if (range) {
    range.addEventListener("input", handleRangeChange);
};

//save
function handleSaveClick() {
    resultCtx.putImageData(ctx.getImageData(0,0,canvas.width,canvas.height),0,0);
    drawText(resultCanvas);
    const image = resultCanvas.toDataURL('image/png', 1.0);
    const link = document.createElement("a");
    link.href = image;
    link.download = "PaintJS";
    link.click();
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


//text canvas
const drawText=(targetCanvas)=>{
    let targetCtx=targetCanvas.getContext("2d");
    targetCtx.font=`${fontSize.value}px bold` ;
    if(targetCanvas!==resultCanvas) targetCtx.clearRect(0,0,targetCanvas.width,targetCanvas.height);
    const textList=textarea.value.split('\n');
    const xOffset=fontSize.value;
    const maxWidth=targetCanvas.width-xOffset*2;
    const height=parseInt(fontSize.value)+5;
    const heightOffset=parseInt(fontSize.value)+10;
    let heightIndex=0;
    for(let i=0;i<textList.length;i++){
        let line="";
        for(let j=0;j<textList[i].length;j++){
            const width=targetCtx.measureText(line+textList[i][j]).width;
            if(width<maxWidth){
                line+=textList[i][j];
            }
            else{
                targetCtx.fillText(line,xOffset,heightOffset+height*heightIndex++);
                line=textList[i][j];
            }
        }
        if(line!=""){
            targetCtx.fillText(line,xOffset,heightOffset+height*heightIndex++);
        }
    }
}
textarea.addEventListener("keyup",e=>{
    drawText(textCanvas);
})
fontSize.addEventListener("change",e=>{
    drawText(textCanvas);
})