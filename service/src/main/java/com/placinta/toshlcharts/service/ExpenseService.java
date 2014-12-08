package com.placinta.toshlcharts.service;

import com.placinta.toshlcharts.dao.ExpensesDao;
import com.placinta.toshlcharts.model.Expense;
import com.placinta.toshlcharts.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cristi on 11/15/14.
 */
@Service
@Transactional
public class ExpenseService {

  private final ExpensesDao expensesDao;

  @Autowired
  public ExpenseService(ExpensesDao expensesDao) {
    this.expensesDao = expensesDao;
  }

  public void saveExpense(List<Expense> expense, boolean isFullImport) {
    if (isFullImport) {
      User user = new User(0, "ds", "sa");
      expensesDao.clearPreviousData(user.getId());
    }
    expensesDao.insertExpense(expense);
  }

}
