
var game = {
    isgameon:false,
    myname:"",
    yourname:"",
    gameID:1,
    mycolor:0,
    ismyturn:false,
    headID:0,
    isOnline:true,   //自己在线状态用来限制在游戏时邀请另一位玩家
    board:new Array(15),
    setEmpty:function(){
        for(var i=0;i<15;i++){
            this.board[i] = new Array(15);
            for(var j=0;j<15;j++){
            this.board[i][j]=0;
            }
        }
    },
    isEmpty:function(i,j){
        if(this.board[i][j]==0) return true;
        else return false;
    },
}

chess.onclick = function (e){
    console.log("click chess");
    var x = e.offsetX;
    var y = e.offsetY;
    var i = Math.floor(x/2/size);
    var j = Math.floor(y/2/size);
    if(game.ismyturn && game.isEmpty(i,j)){ //我的回合且下棋位置正确
            websocket.send(JSON.stringify({
                messageType:"game",
                gameID:game.gameID,
                fromName:game.myname,
                xposition:i,
                yposition:j,
                color:game.mycolor,
            }));
    }
}

$("#giveup").click(function (){
    if(game.isgameon){
        websocket.send(JSON.stringify({
            messageType:"gameGiveup",
            gameID:game.gameID,
            fromName:game.myname,
        }));
    }
})

$("#regret").click(function (){
    if(game.isgameon && (!game.ismyturn)){
        websocket.send(JSON.stringify({
            messageType:"gameRegret",
            gameID:game.gameID,
            fromName:game.myname,
            toName:game.yourname,
        }));
    }
})