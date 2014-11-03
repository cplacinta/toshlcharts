package com.placinta.toshlcharts.dao.jdbc;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.placinta.toshlcharts.dao.UserDao;
import com.placinta.toshlcharts.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.placinta.toshlcharts.model.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao implements UserDao {

  @Autowired
  private DataSource dataSource;

  @Override
  public User getUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public User insert(String username, String password) {
    String sql = "INSERT INTO users (username, password) VALUES (?, ?);";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertStatement = connection.prepareStatement(sql)) {

      insertStatement.setString(1, username);
      insertStatement.setString(2, password);
      insertStatement.execute();
    } catch (MySQLIntegrityConstraintViolationException e) {
      throw new UserExistsException("User '" + username + "' already exists. Please try again");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return getUser(username, password);
  }

}
