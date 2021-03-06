package com.placinta.toshlcharts.controllers;

import com.placinta.toshlcharts.model.User;
import com.placinta.toshlcharts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
  public void login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws IOException {
    response.setContentType("text/html");

    User user = userService.getUser(username, password);
    if (user == null) {
      response.sendRedirect("index.jsp");
      return;
    }

    PrintWriter out = response.getWriter();
    out.println("User: " + user.getUserName());
    out.println("password: " + user.getPassword());
    out.flush();
  }

}
