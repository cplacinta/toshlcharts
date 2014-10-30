package com.placinta.toshlcharts.dao.jdbc;

import com.placinta.toshlcharts.dao.UserDao;
import com.placinta.toshlcharts.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao implements UserDao {

  @Autowired
  private DataSource dataSource;

  @Override
  public User getUser(String username, String password) {

    String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";

    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      User user = null;
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
      }
      resultSet.close();
      preparedStatement.close();
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public User insert(String username, String password) {
    String sqlUserName = "SELECT 1 FROM users WHERE username = ?;";
    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(sqlUserName);
         PreparedStatement insertStatement = connection.prepareStatement(sql)) {

      selectStatement.setString(1, username);

      if (selectStatement.executeUpdate() > 0) {
        System.out.println("User already exists. Please try again");
        throw new RuntimeException("User already exists. Please try again");
      }

      insertStatement.setString(1, username);
      insertStatement.setString(2, password);
      insertStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return getUser(username, password);
  }

}
