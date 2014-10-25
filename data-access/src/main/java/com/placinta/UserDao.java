package com.placinta;

import com.placinta.toshlcharts.User;

public interface UserDao {
  void insert(User user);

  User getUser(String username, String password);

}
