package com.placinta.toshlcharts.dao;

import com.placinta.toshlcharts.model.User;

public interface UserDao {
  void insert(User user);

  User getUser(String username, String password);

}
