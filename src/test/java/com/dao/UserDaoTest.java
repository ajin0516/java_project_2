package com.dao;

import domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    // UserDao를 생성 할 때 생성자를 통해 다른 ConnectionMaker 구현체를 DI해줘도 UserDao의 모든 로직은 그대로 작동
    @Test
    void addAndFind() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        userDao.deleteAll();
        User user1 = new User("7","haha","1324");
        userDao.add(user1);
        User addFind = userDao.findById("7");
        assertEquals("haha",addFind.getName());

    }
    @Test
    void deleteAll() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        userDao.deleteAll();
        User user2 = new User("11","yaya","2020");
        userDao.add(user2);
        assertEquals(1,userDao.count());
    }
    @Test
    void count() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        userDao.deleteAll();
        User user1 = new User("1","aj","29");
        User user2 = new User("2","sy","26");
        userDao.add(user1);
        userDao.add(user2);
        assertEquals(2,userDao.count());
    }

//    @Test
//    void findAll() throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao();
//        userDao.deleteAll();
//        List<User> userList = userDao.findAll();
//        assertEquals(0, userList.size());

//        User user1 = new User("1","hong"," 88");
//        User user2 = new User("2","song"," 99");
//        userDao.add(user1);
//        userDao.add(user2);
//        userList = userDao.findALl();
//        assertEquals(2,userList.size());

//    }

}