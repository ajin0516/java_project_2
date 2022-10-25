package com.dao;
// 조립의 역할
public class UserDaoFactory {

    public UserDao awsUserDao(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(new AWSConnectionMaker());
        return userDao;
    }

    public UserDao lacakUserDao(){
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }

}
