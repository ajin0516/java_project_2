package com.dao;
// 조립의 역할

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 왜 분리? -> 갈아끼울 수 있다? 새로운 곳에 연결하려면 local,aws처럼 인터페이스 구현체 만들어야 하는 거 아닌가?
// 이해불가
@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao awsUserDao(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(new AWSConnectionMaker());
        return userDao;
    }

    @Bean
    public UserDao lacalUserDao(){
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }

}
