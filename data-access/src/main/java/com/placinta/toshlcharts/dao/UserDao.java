package com.placinta.toshlcharts.dao;

import com.placinta.toshlcharts.model.User;

public interface UserDao {

  User getUser(String username, String password);

  User insert(String username, String password);

}
