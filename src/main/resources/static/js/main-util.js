/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-07-05 10:06:59
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-07-15 10:12:46
 * @FilePath: \总监日记表单\js\main-util.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
var util = {
    getuserlisthtml: function(userlist,wodename){ //array
        var str = "";
        var name ="";    
        var tmp = "";
        for(var i=0;i<userlist.length;i++){
            name = userlist[i].username;
            if(userlist[i].state=="online"){
                if(wodename==name){
                    tmp=
                    "<tr class='user'"+" id="+"'user-"+name+"'>"+
                    "<td class='name'>"+name+"(我)"+"</td>"+
                    "<td class='online'>在线</td><td class='online'></td></tr>";
                }
                else{
                    tmp=
                    "<tr class='user'"+" id="+"'user-"+name+"'>"+
                    "<td class='name'>"+name+"</td>"+
                    "<td class='online'>在线</td>"+
                    "<td><button type='button' class='invite'>邀请</button><td></tr>";
                }
                str += tmp;
            }
            else if(userlist[i].state=="gaming"){
                if(wodename==name){
                    tmp =
                    "<tr class='user'"+" id="+"'user-"+name+"'>"+
                    "<td class='name'>"+name+"(我)"+"</td>"+
                    "<td class='gaming'>游戏中</td><td></td></tr>";
                }
                else {
                    tmp =
                        "<tr class='user'"+" id="+"'user-"+name+"'>"+
                        "<td class='name'>"+name+"</td>"+
                        "<td class='gaming'>游戏中</td><td></td></tr>";
                }

                str += tmp;
            }
            tmp = "";
        }
        return str;
    },
    getmsgutil:function (reason){
        var str ="";
        if(reason == "invite"){
            str = "<div class='invitefrom'></div><div class='choice'> <button id="
                +"'inviteaccept'>同意<img class='inviteimg' src='../media/accept.png'></button>"
                +"<button id='refuse'>拒绝 <img class='inviteimg' src='../media/refuse.png'></button></div>";
        }
        else if(reason == "regret"){
            str = "<div class='invitefrom'></div><div class='choice'> <button id="
                +"'regretaccept'>同意<img class='inviteimg' src='../media/accept.png'></button>"
                +"<button id='refuse'>拒绝 <img class='inviteimg' src='../media/refuse.png'></button></div>";
        }
        return str;
    },
    gettimeutil:function (time){
        var str="";
        if(time.h<10){
            str = "0"+time.h;
        }
        else {
            str =time.h;
        }
        if(time.m<10){
            str = str+":0"+time.m;
        }
        else {
            str = str+":"+time.m;
        }
        if(time.s<10){
            str = str+":0"+time.s;
        }
        else {
            str = str+":"+time.s;
        }
        return str;
        console.log("time=" +str);
    }
}
