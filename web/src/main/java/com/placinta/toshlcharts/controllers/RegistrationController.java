package com.placinta.toshlcharts.controllers;

import com.placinta.toshlcharts.model.User;
import com.placinta.toshlcharts.model.UserExistsException;
import com.placinta.toshlcharts.service.UserService;
import org.hibernate.dialect.HANARowStoreDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class RegistrationController {

  @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
  public void register(HttpServletResponse response, @RequestParam String username, @RequestParam String password)
      throws IOException {

    try {
      User user = userService.saveUser(username, password);

      PrintWriter out = response.getWriter();
      out.println("User: " + user.getUserName());
      out.println("password: " + user.getPassword());
      out.flush();
    } catch (UserExistsException e) {
      response.sendRedirect("index.jsp");
    }
  }

  @Autowired
  private UserService userService;

}