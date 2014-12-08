package com.placinta.toshlcharts.dao;

import com.placinta.toshlcharts.model.Expense;
import java.util.List;

public interface ExpensesDao {

  void insertExpense(List<Expense> expense);
  void clearPreviousData(int userId);
}
