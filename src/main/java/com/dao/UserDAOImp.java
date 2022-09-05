package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOImp implements UserDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        String addsql = "insert into userinfo(username,password,headid) values(?,?,?)";
        Object[] args = {user.getUsername(),user.getPassword(),user.getHeadID()};
        int update = jdbcTemplate.update(addsql,args);
        //返回值update代表添加了几行
    }

    @Override
    public void updateUser(User user) {
        String updatesql = "update userinfo set username=?,password=?where username=?";
        Object[] args = {user.getUsername(), user.getPassword(),user.getUsername()};//注意参数顺序
        int update = jdbcTemplate.update(updatesql,args);
    }

    @Override
    public void delete(String username) {
        String deletesql = "delete from userinfo where username=?";
        int update = jdbcTemplate.update(deletesql,username);
    }

    @Override
    public User selectUser(String name) {
        String selectsql = "select * from userinfo where username=?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(selectsql,new BeanPropertyRowMapper<User>(User.class),name);
            return user;
        }catch (Exception e){ //解决查询结果为0时报错的问题
            return null;
        }
    }

    @Override
    public List<User> selectAllUsers() {
        String selectsql = "select * from userinfo";
        List<User> users= (List<User>) jdbcTemplate.queryForObject(selectsql,new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public void updateGame(String username, int gameResult) {
        User user = selectUser(username);
        String updatesql = "update userinfo set wingames=?,runawaygames=?,totalgames=?,winrate=?,runawayrate=? where username=?";
        if(gameResult==1){
            user.setWinGames(user.getWinGames()+1);
        }
        else if(gameResult==0){
            user.setRunAwayGames(user.getRunAwayGames()+1);
        }
        user.setTotalGames(user.getTotalGames()+1);
        user.calRate();
        Object[] args = {user.getWinGames(),user.getRunAwayGames(),user.getTotalGames(),user.getWinRate(),user.getRunAwayRate(),user.getUsername()};
        int update = jdbcTemplate.update(updatesql,args);
    }
}
