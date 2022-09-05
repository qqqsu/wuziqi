/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-30 20:03:24
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-07-03 23:46:36
 * @FilePath: \总监日记表单\js\login.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
// 批量显示头像，并绑定相应的事件

//头像框设置
var heads = "";
var str="";
for(var i=1;i<10;i++){
    str= "<label class='box'><input type='radio' name='radio' value='"+i+"'><span class='option'><img src='../media/h"
        +i
        +".jpg'></span></label>"
    heads+=str;
}
$(".content").html(heads);

//切换为注册界面
$("#registerbtn").click(function (){
    $(".content").css("display","flex");
    $("#choosehead").text("请选择头像");
    $("#registerbtn").css("display","none");
    $("#loginbtn").css("display","none");
    $("#returnloginbtn").css("display","block");
    $("#checkregisterbtn").css("display","block");
    $("#renumber").css("display","block");
    $("#checkpwd").css("display","block");
    $("#registernumber").css("display","block");
    $("#checkpassword").css("display","block");
    $(".text").css("font-size","18px");
    $("h3").css("font-size","18px");
    $("h3").css("padding","10px 0");
    setInputEmpty();
})

//切换为登录界面
$("#returnloginbtn").click(function (){
    $(".content").css("display","none");
    $("#choosehead").text("");
    $("#registerbtn").css("display","block");
    $("#loginbtn").css("display","block");
    $("#returnloginbtn").css("display","none");
    $("#checkregisterbtn").css("display","none");
    $("#renumber").css("display","none");
    $("#checkpwd").css("display","none");
    $("#registernumber").css("display","none");
    $("#checkpassword").css("display","none");
    $(".text").css("font-size","22px");
    $("h3").css("font-size","22px");
    $("h3").css("padding","20px 0");
    setInputEmpty();
})

//登录提交，并进行非空，非重复，密码正确校验
var username;
var password;
var data = {};
$.ajaxSetup({contentType: "application/json; charset=utf-8"})
$("#loginbtn").click(function (){
    username = $("#username").val(); //按Java User类传输
    password = $("#password").val();
    data['username'] = username;
    data['password'] = password;
    if(!isValid(username)){
        alert("用户名不能为空");
        return;
    }
    if(!isValid(password)){
        alert("密码不能为空");
        return;
    }
    $.post("http://192.168.0.109:8088/login/login", JSON.stringify(data), function (res) {
        if (res.flag) window.location.href = "../html/main.html";
        else if (res.message == "pwdwrong") alert("密码错误");
        else if (res.message == "notexit") alert("该用户不存在");
        else alert("不能重复登录");
    }, 'json');
})

$("#checkregisterbtn").click(function (){
    var headID = $('.content .box input:radio:checked').val();
    username = $("#username").val(); //按Java User类传输
    password = $("#password").val();
    checkpassword = $("#checkpassword").val();
    registernumber = $("#registernumber").val();
    data['username'] = username;
    data['password'] = password;
    data['registernumber'] = registernumber;
    data['headID'] = headID;
    if(typeof(headID)=="undefined"){
        alert("请选择头像");
        return;
    }
    if(!isValid(username)){
        alert("用户名不能为空");
        return;
    }
    if(!isValid(password)){
        alert("密码不能为空");
        return;
    }
    if(username.length>10){
        alert("用户名超过10个字符，请重新输入");
        return;
    }
    if(password != checkpassword){
        alert("两次输入密码不匹配，请重新输入");
        return;
    }
    $.post("http://192.168.0.109:8088/register/register", JSON.stringify(data), function (res) {
        if (res.flag) {
            alert("注册成功！");
            setInputEmpty();
        }
        else alert("注册失败！");
    }, 'json');
})









function setInputEmpty(){
    $(".text").val("");
}

function isValid(input){
    if(input==null || input.trim()=="") return false;
    else return true;
}