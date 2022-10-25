package com.dao;

import domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {
    public void add() throws ClassNotFoundException, SQLException {
        Map<String ,String > env = System.getenv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        PreparedStatement ps = conn.prepareStatement("INSERT INTO lastusers(id, name, password) VALUES (?,?,?)");
        ps.setString(1,"1");
        ps.setString(2,"ajin");
        ps.setString(3,"486");

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void deleteAll(){

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add();
    }
}
