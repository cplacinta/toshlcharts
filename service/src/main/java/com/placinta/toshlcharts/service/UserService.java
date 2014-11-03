package com.placinta.toshlcharts.service;

import com.placinta.toshlcharts.dao.UserDao;
import com.placinta.toshlcharts.model.User;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by cristi on 10/7/14.
 */

@Service
@Transactional
public class UserService {

  @Autowired
  private UserDao userDao;

  public User saveUser(String username, String password) {
    return userDao.insert(username, hashPassword((password)));
  }

  public User getUser(String username, String password) {
    return userDao.getUser(username, hashPassword((password)));
  }

  private static String hashPassword(String password) {
    String hashedPassword = "";
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(password.getBytes("UTF-8"));
      hashedPassword = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      //TODO handle exception
      e.printStackTrace();
    }
    return hashedPassword;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

}
