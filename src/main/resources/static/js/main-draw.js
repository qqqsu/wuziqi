
var chess = document.getElementById('chess');
var context = chess.getContext('2d');
var size = 17.5;
var blackchess = document.querySelector("#blackchess");
var whitechess = document.querySelector("#whitechess");

var drawEmptyBoard = function(){
    clearBorad();
    context.strokeStyle = "#BFBFBF";
    for(var i=0;i<15;i++){ //棋盘宽高580，旁白18，间距
        context.moveTo(size+i*2*size,size);//竖线 
        context.lineTo (size+i*2*size,525-size)
        context.stroke ();
        context.moveTo(size,size+i*2*size);//横线
        context.lineTo(525-size,size+i*2*size);
        context.stroke();
    }
}

var drawPiece = function (i, j, color){//i,j代表棋子的索引位置，flag标记黑棋白棋
    // context.beginPath();
    // context.arc(size+i*2*size, size+j*2*size,17,0,2*Math.PI);
    // context.closePath();
    // var gradient = context.createRadialGradient(size+i*2*size+2,size+j*2*size-2,13,size+i*2*size+2,size+j*2*size-2,0);
    if(color==1){//如果1则黑棋
        // gradient.addColorStop(0, "#0A0A0A");
        // gradient.addColorStop(1,"#636766");
        context.drawImage(blackchess,i*2*size-3,j*2*size);
    }else {//白棋
        context.drawImage(whitechess,i*2*size-3,j*2*size);
        // gradient.addColorStop(0, "#D1D1D1");
        // gradient.addColorStop(1,"#F9F9F9");
        // context.fillStyle=gradient;
        // context.fill();
    }

}

var clearBorad = function(){
    context.clearRect(0,0,525,525);
    context.beginPath();
}

var drawAllPieces = function (){
    for(var i=0;i<15;i++){
        for(var j=0;j<15;j++){
            if(game.board[i][j]==1) drawPiece(i,j,1);
            else if(game.board[i][j]==-1) drawPiece(i,j,-1);
        }
    }
}







