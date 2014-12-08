package com.placinta.toshlcharts.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Expense {
  private static final SimpleDateFormat formatter =  new SimpleDateFormat("dd.MM.yyyy");

  private int id;
  //TODO: User instead uof user id
  private int userId;
  private Date date;
  private List<Tag> tags;
  private double amount;
  private double income;
  private String currency;
  private String description;

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getIncome() {
    return income;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Date: "+ formatter.format(date) +":   Tag: " + tags + "   Amount: " + amount+"    Income:" + income +
        "    Currency:" + currency + "     Description:" + description;
  }
}
