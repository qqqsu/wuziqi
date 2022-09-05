/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-30 21:58:39
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-07-05 17:43:35
 * @FilePath: \总监日记表单\js\main-main.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
// 获取本人用户名
$.ajax({
    type:'GET',
    url:'http://192.168.0.109:8088/login/getUserInfo',
    async:false,
    success:function(res){
        console.log(res);
        game.myname = res;
    }
})

var websocket = null;

websocket = new WebSocket("ws://192.168.0.109:8088/game");

websocket.onopen = function (res) {
    console.log("connection success");
}

websocket.onmessage = function (event) {
    var res = JSON.parse(event.data);
    console.log("receive",res);
    //更新在线用户列表
    if(res.messageType=="userlist"){
        userList(res);
    }

    else if(res.messageType=="invite"){
        invite(res);
    }
    else if(res.messageType=="gameStart"){
        gameStart(res);
    }

    else if(res.messageType=="gameret"){
        gameret(res);
    }

    else if(res.messageType=="gameTime"){
        gameTime(res);
    }

    else if(res.messageType=="gameRegret"){
        gameRegret(res);
    }

    else if(res.messageType=="gameEnd"){
        gameEnd(res);
    }

    else {
        console.log("get error message!");
    }
}
    
websocket.onerror = function () {
    console.log("error");
};

websocket.onclose = function () {
    alert("您已与服务器断开连接，请关闭游览器重试");
}

window.onbeforeunload = function () {
    closeWebSocket();
}

function closeWebSocket() {
    websocket.close();
}



var userList = function (res){
    //根据接受数据更新列表
    var userlist= util.getuserlisthtml(res.userlist,game.myname);
    $("#userlist").html(userlist);
    let list =new Object();
    list = res.userlist;
    list = JSON.stringify(list);
    var list1 = JSON.parse(list);
    for(var i=0;i<list1.length;i++) {
        let name = list1[i].username;
        //为好友绑定邀请事件
        if(name!=game.myname && list[i].state!="gaming" && game.isOnline){
            $("#user-"+name+" .invite").click(function(){
                websocket.send(JSON.stringify({
                    messageType:"invite",
                    fromName:game.myname,
                    toName:name
                }))
            })
        }
    }
}

var invite = function (res){
    msgmusic.play();
    //显示提示框
    $(".msg").html(util.getmsgutil("invite"))
    $(".invitefrom").text("收到了来自"+res.fromName+"的游戏邀请");
    $(".msg").fadeIn("slow");
    var inviteret = {
        messageType:"inviteret",
        fromName:game.myname,
        toName:res.fromName,
        accept:false,
    };
    //根据操作反馈给服务器信息
    $("#inviteaccept").click(function (){
        console.log("accept");
        inviteret.accept=true;
        websocket.send(JSON.stringify(inviteret));
        $(".msg").fadeOut("slow");
    })
    $("#refuse").click(function (){
        websocket.send(JSON.stringify(inviteret));
        $(".msg").fadeOut("slow");
    })
    setTimeout(() => {
        $(".msg").fadeOut("slow");
    }, 10000);
}

var gameStart = function (res){
    catshow("游戏开始！");
    startmusic.play();
    //更新界面信息
    clearBorad();
    $("#mycolor").show();
    $("#yourcolor").show();
    $(".td2").show();
    $(".colortd").show();
    $(".state-chess").show();
    $("#chessName").show();
    $("#chessName").text("黑方走");
    $(".state-chess").attr("src","../media/black1.png");
    game.isOnline = false;
    game.gameID=res.gameID;
    game.setEmpty();
    game.isgameon = true;
    if(res.playerA.name == game.myname) {
        game.yourname = res.playerB.name;
        game.mycolor = res.playerA.color;
        $("#myhead").attr("src","../media/h"+res.playerA.headID+".jpg");
        $("#yourhead").attr("src","../media/h"+res.playerB.headID+".jpg");
    }
    else {
        game.yourname = res.playerA.name;
        game.mycolor = res.playerB.color;
        $("#myhead").attr("src","../media/h"+res.playerB.headID.toString()+".jpg");
        $("#yourhead").attr("src","../media/h"+res.playerA.headID.toString()+".jpg");
    }
    $("#myname").text(game.myname);
    $("#yourname").text(game.yourname);
    if(game.mycolor==1){
        $("#mycolor").attr('src',"../media/black.png");
        $("#yourcolor").attr('src',"../media/white.png");
        game.ismyturn=true;
    }else {
        $("#mycolor").attr('src',"../media/white.png");
        $("#yourcolor").attr('src',"../media/black.png");
    }
    setTimeout(() => {
        drawEmptyBoard();
    }, 0);

}

var gameret = function (res){
    //更新棋盘信息，交换行棋顺序
    playmusic.play();
    game.board = res.chesses;
    if(res.nextOrder==game.mycolor) game.ismyturn=true;
    else game.ismyturn=false;
    $(".statechess").show();
    if(res.nextOrder==1){
        $("#chessName").text("黑方走");
        $(".state-chess").attr("src","../media/black1.png");
    }
    else{
        $("#chessName").text("白方走");
        $(".state-chess").attr("src","../media/white1.png");
    }
    drawEmptyBoard();
    drawAllPieces(game.board);
}

var gameTime = function (res){
    if(game.mycolor==1){
        $("#myttime").text(util.gettimeutil(res.times.btTime));
        $("#mystime").text(util.gettimeutil(res.times.bsTime));
        $("#yourttime").text(util.gettimeutil(res.times.wtTime));
        $("#yourstime").text(util.gettimeutil(res.times.wsTime));
    }
    else {
        $("#yourttime").text(util.gettimeutil(res.times.btTime));
        $("#yourstime").text(util.gettimeutil(res.times.bsTime));
        $("#myttime").text(util.gettimeutil(res.times.wtTime));
        $("#mystime").text(util.gettimeutil(res.times.wsTime));
    }
}

var gameRegret = function (res){
    msgmusic.play();
    //显示悔棋提示框
    $(".msg").html(util.getmsgutil("regret"));
    console.log("fromname="+res.fromName);
    $(".invitefrom").text("对方请求悔棋");
    $(".msg").fadeIn("slow");
    var gameRegretret = {
        messageType:"gameRegretret",
        gameID:game.gameID,
        fromName:game.myname,
        toName:res.fromName,
        accept:false,
    };
    //根据操作反馈给服务端是否同意悔棋
    $("#regretaccept").click(function (){
        console.log("accept");
        gameRegretret.accept=true;
        websocket.send(JSON.stringify(gameRegretret));
        $(".msg").fadeOut("slow");
    })
    $("#refuse").click(function (){
        websocket.send(JSON.stringify(gameRegretret));
        $(".msg").fadeOut("slow");
    })
    setTimeout(() => {
        $(".msg").fadeOut("slow");
    }, 10000);
}

var gameEnd = function (res){
    if(res.winner == game.mycolor){
        winmusic.play();
        if(res.reason == "timeout"){
            catshow("对方超时！");
        }else if(res.reason == "runaway"){
            catshow("对方逃跑！");
        }
        else catshow("您胜利了！");
    }
    else {
        losemusic.play();
        if(res.reason == "timeout") {
            catshow("您已超时！");
        }else catshow("您失败了！");
    }
    //更新界面和游戏信息
    $(".td2").hide();
    $(".colortd").hide();
    $(".state-chess").hide();
    $("#chessName").hide();
    $("#myhead").attr("src","../media/h0.jpg");
    $("#yourhead").attr("src","../media/h0.jpg");
    game.isOnline = true;
    game.setEmpty();
    game.mycolor=0;
    game.gameID=0;
    game.ismyturn=false;
    game.isgameon = false;
}