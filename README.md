## 1.项目介绍
基于Springboot和js打造的五子棋在线联机对战，可以多场游戏同时对决
## 2.配置数据库
修改application.yml文件中的数据库地址即可
目前只实现了注册功能，其他功能还在开发中
注册时的注册码可以自行在controller中设置
## 3.启动项目.
运行启动器类（WebsocketWuziqiApplication）启动项目
## 4.浏览器访问
为实现局域网下设备访问，需分别在main-main.js下配置websocket访问该设备的的ip地址,若不需要局域网则设置为127.0.0.1即可
端口号默认8088,可以自行在application.yml文件中修改
## 5.关于游戏计时功能
目前是采用每一秒服务器向每个游览器推送当前游戏时间的方式,如果同时的游戏数过可能会出现计时不准确的问题?小伙伴们有更好的思路可以交流。
## 6.其他待开发功能
用户输赢记录，及排行榜；历史游戏记录；更改，查看用户信息；游戏内聊天...
## 7. PPPPPPS
数据库要先创一个table<br>
id int AI PK<br>
username varchar(10) <br>
password varchar(15) <br>
totalgames int <br>
wingames int <br>
runawaygames int <br>
winrate double(4,2) <br>
runawayrate double(4,2) <br>
headid int<br>






