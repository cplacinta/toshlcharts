package com.placinta.toshlcharts.model;

public class User
{

  private int Id;
  private String userName;
  private String password;

  public User(int id, int userName, int password) {

  }


  //getter and setter methods
  public int getId() {
    return Id;
  }

  public void setId(int id) {
    this.Id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}