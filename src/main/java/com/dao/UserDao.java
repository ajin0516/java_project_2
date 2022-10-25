package com.dao;

import domain.User;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {

    UserDao userDao;
    public void add(User user) throws ClassNotFoundException, SQLException {
        Map<String ,String > env = System.getenv();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
            ps = conn.prepareStatement("INSERT INTO lastusers(id, name, password) VALUES (?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ps.close();
            conn.close();
        }
    }

    // 타입을 User로 설정해서 값을 User객체에서 받기
    // id로 해당 행 찾기
    public User findById(String id) throws ClassNotFoundException, SQLException {
        Map<String ,String > env = System.getenv();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
             ps = conn.prepareStatement("SELECT id, name, password FROM lastusers WHERE id = ?");
            ps.setString(1, id);
             rs = ps.executeQuery(); // 조회문
            rs.next();
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if(rs != null){
                rs.close();
            } else if (ps != null) {
                ps.close();
            }else if(conn != null)
            conn.close();
        }
        return user;
    }

    public List<User> findALl() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = null;
        Map<String ,String > env = System.getenv();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
             ps = conn.prepareStatement("SELECT * d FROM lastusers ORDER BY id");

             rs = ps.executeQuery(); // 조회문

            rs.next();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                userList.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if(rs != null){
                rs.close();
            } else if (ps != null) {
                ps.close();
            }else if(conn != null)
                conn.close();
        }
        return userList;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        Map<String ,String > env = System.getenv();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
             ps = conn.prepareStatement("DELETE FROM lastusers");

            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if(ps != null)
            ps.close();
        }
    }

    public int count() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String ,String > env = System.getenv();
        int cnt = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
            rs = ps.executeQuery(); // 조회문
            while (rs.next()) {
                cnt = rs.getInt(1);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if(rs != null){
                rs.close();
            } else if (ps != null) {
                ps.close();
            }else if(conn != null)
                conn.close();
        }
        return cnt;
    }

    public Connection connectionMaker() throws ClassNotFoundException, SQLException {
        Map<String ,String > env = System.getenv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        return conn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
//        userDao.add();
//        User user = userDao.findById("2");
        userDao.add(new User("4","rara","3435"));
//        System.out.println(user.getId());
//        System.out.println(user.getName());
//        System.out.println(user.getPassword());
    }
}
