package com.dao;

import domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    User user;

    @Test
    void addAndFind() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user1 = new User("7","haha","1324");
        userDao.add(user1);
        User addFind = userDao.findById("7");
        assertEquals("haha",addFind.getName());
    }



}