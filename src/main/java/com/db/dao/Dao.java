package com.db.dao;

import com.db.domain.User;

import java.sql.*;
import java.util.Map;

public class Dao {
    public void add() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        // Driver 연결
        Class.forName("com.mysql.cj.jdbc.Driver");
        // DB 연결
        Connection c = DriverManager.getConnection(dbHost, dbUser, dbPassword);
        // 쿼리문 작성
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, "01");
        ps.setString(2, "Kyeongrok");
        ps.setString(3, "password");

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/likelion-db", "root", dbPassword);

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public static void main (String[]args) throws SQLException, ClassNotFoundException {
        Dao dao = new Dao();
        dao.add();
    }
}
