package com.placinta.toshlcharts;

import com.placinta.toshlcharts.model.User;
import com.placinta.toshlcharts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SaveUser {

  @Autowired
  private UserService userService;

  @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String username = request.getParameter("username");
    String password = (request.getParameter("password"));

    try {
      User user = userService.saveUser(username, password);
    } catch (RuntimeException e) {
      response.sendRedirect("index.jsp");
    }


  }


}