package com.placinta.toshlcharts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Controller
public class FormLogin extends HttpServlet {

  private final UserService userService;

  @Autowired
  public FormLogin(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String username = request.getParameter("username");
    String password = encrypt(request.getParameter("password"));
    out.println("User: " + username);
    out.println("password: " + password);
    out.flush();


    if (userService != null) {
      User user = userService.getUser(username, password);

      if (user == null) {
        response.sendRedirect("index");
      }
    }

    /*try {
      out.println("Password: " + encrypt(password) );
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }

  private static String encrypt(String password) {
    String sha1 = "";
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(password.getBytes("UTF-8"));
      sha1 = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return sha1;
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
