package com.placinta.toshlcharts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;

//@Component
public class SaveUser extends HttpServlet {

  private UserService userService;

  //@Autowired
/*  public SaveUser(UserService userService) {

    this.userService = userService;
  }*/

}