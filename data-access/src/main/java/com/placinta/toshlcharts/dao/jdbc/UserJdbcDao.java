package com.placinta.toshlcharts.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.placinta.UserDao;
import com.placinta.toshlcharts.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao implements UserDao {
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insert(User user) {
    System.out.println("In DAO");

    String sql = "INSERT INTO members (id, username, password) VALUES (?, ?, ?)";

    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, user.getUserName());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public User getUser(String username, String password) {
    System.out.println("In DAO");
    return null;
  }

  public User selectById(int id) {

    String sql = "SELECT * FROM CUSTOMER WHERE id = ?";

    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      User user = null;
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User(resultSet.getInt("id"), resultSet.getInt("username"), resultSet.getInt("password"));
      }
      resultSet.close();
      preparedStatement.close();
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

}