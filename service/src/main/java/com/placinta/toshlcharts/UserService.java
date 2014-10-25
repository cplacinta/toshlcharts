package com.placinta.toshlcharts;

import com.placinta.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by cristi on 10/7/14.
 */

@Service
@Transactional
public class UserService {

  @Autowired
  private UserDao userDao;

  public void saveUser(User user) {
    userDao.insert(user);
  }

  public User getUser(String username, String password) {
    System.out.println("In Service");
    return userDao.getUser(username, password);
  }

}
