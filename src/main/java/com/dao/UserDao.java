package com.dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {

    // UseDao에서 인터페이스 ConnectionMaker 사용하게 변경
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AWSConnectionMaker();
    }

    // 💁🏻‍♀️constructor 오버로딩
    // test에서 connection 못쓰나?
    // 왜 생성자 두개나?
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = new AddStrategy().makePreparedStatement(conn);
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
        if (ps != null) {
            ps.close();
        } else if (conn != null)
            conn.close();
    }

    // 타입을 User로 설정해서 값을 User객체에서 받기
    // id로 해당 행 찾기
    public User findById(String id) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("SELECT id, name, password FROM lastusers WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery(); // 조회문
            rs.next();
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            } else if (ps != null) {
                ps.close();
            } else if (conn != null)
                conn.close();
        }
        if(user == null){
            throw new EmptyResultDataAccessException(1);  // 왜 1을?
        }
        return user;
    }

    public List<User> findAll() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("SELECT * FROM lastusers");

            rs = ps.executeQuery(); // 조회문
            User user;
            while (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            } else if (ps != null) {
                ps.close();
            } else if (conn != null)
                conn.close();
        }
        if(userList == null){
            throw new EmptyResultDataAccessException(0);  // 왜 1을?
        }
        return userList;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = new DeleteAllStrategy().makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            } else if (conn != null)
                conn.close();
        }
    }

    public int count() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cnt = 0;
        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("select count(*) from lastusers");
            rs = ps.executeQuery(); // 조회문
            while (rs.next()) {
                cnt = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            } else if (ps != null) {
                ps.close();
            } else if (conn != null)
                conn.close();
        }
        return cnt;
    }

}
