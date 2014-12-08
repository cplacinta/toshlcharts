package com.placinta.toshlcharts.dao.jdbc;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.placinta.toshlcharts.dao.ExpensesDao;
import com.placinta.toshlcharts.model.Expense;
import com.placinta.toshlcharts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpensesJdbcDao implements ExpensesDao {

  @Autowired
  private DataSource dataSource;


  @Override
  public void insertExpense(List<Expense> expenses) {


    String sql =
      "INSERT INTO expenses (date,amount,income,currency,description, user_id) VALUES (?, ?, ?, ?, ?, ?);";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertStatement = connection
           .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      for (Expense expense : expenses) {
        insertStatement.setDate(1, new Date(expense.getDate().getTime()));
        insertStatement.setDouble(2, expense.getAmount());
        insertStatement.setDouble(3, expense.getIncome());
        insertStatement.setString(4, expense.getCurrency());
        insertStatement.setString(5, expense.getDescription());
        insertStatement.setInt(6, 0);
        insertStatement.executeUpdate();

        ResultSet resultSet = insertStatement.getGeneratedKeys();
        if (resultSet.next()) {
          expense.setId(resultSet.getInt(1));
          insertTags(expense);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void clearPreviousData(int userId) {

    String deleteFromExpensesTagsSql =
      "delete from expenses where user_id=?;";
    String deleteFromTagsSql =
      "delete from tags where user_id=?;";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertStatement = connection
           .prepareStatement(deleteFromExpensesTagsSql, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement insertStatementTags = connection
                      .prepareStatement(deleteFromTagsSql, Statement.RETURN_GENERATED_KEYS)) {
      insertStatement.setInt(1, userId);
      insertStatement.executeUpdate();
      insertStatementTags.setInt(1,userId);
      insertStatementTags.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void insertTags(Expense expense) throws SQLException {
    for (Tag tag : expense.getTags()) {
      int tagId = insertTag(tag.getTagName(), expense.getUserId());
      insertExpenseTag(expense.getId(), tagId);
    }
  }

  private void insertExpenseTag(int expenseId, int tagId) throws SQLException {
    if (tagId == 0) {
      return;
    }
    String sqlInsertExpenseTag =
      "INSERT INTO expenses_tags (expense_id, tag_id) VALUES (?, ?);";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertExpenseTagStatement = connection
           .prepareStatement(sqlInsertExpenseTag)) {
      insertExpenseTagStatement.setInt(1, expenseId);
      insertExpenseTagStatement.setInt(2, tagId);
      insertExpenseTagStatement.executeUpdate();
    }
  }

  public int insertTag(String tagName, int userId) {

    String sqlInsert = "INSERT INTO tags (name, user_id) VALUES (?, ?);";
    String selectTags = "SELECT id FROM tags WHERE user_id=? AND name=?";


    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertTagStatement = connection
           .prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement selectStatement = connection
           .prepareStatement(selectTags, Statement.RETURN_GENERATED_KEYS)) {

      selectStatement.setInt(1, userId);
      selectStatement.setString(2, tagName);
      ResultSet resultSetIdTags = selectStatement.executeQuery();
      if (resultSetIdTags.next()) {
        return resultSetIdTags.getInt(1);
      } else {
        insertTagStatement.setString(1, tagName);
        insertTagStatement.setInt(2, userId);
        insertTagStatement.executeUpdate();
        ResultSet resultSet = insertTagStatement.getGeneratedKeys();
        if (resultSet.next()) {
          return resultSet.getInt(1);
        }
      }
      throw new RuntimeException("Unable to retrieve tag id");
    } catch (MySQLIntegrityConstraintViolationException e) {

      return 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private List<String> listTagsNovember(Date date) throws SQLException {

    String selectTagsForNovember =
      "select name from tags as t  INNER join expenses_tags as et on et.tag_id=t.id inner join expenses as e " +
        "ON e.id=et.expense_id where date between '2014-10-1' and  '2014-11-1' group by t.name;";

    List<String> tagList;
    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertStatement = connection
           .prepareStatement(selectTagsForNovember)) {
      insertStatement.setDate(1, new Date(date.getTime()));
      insertStatement.executeUpdate();
      ResultSet resultSet = insertStatement.getGeneratedKeys();
      tagList = new ArrayList<>();
      if (resultSet.next()) {
        tagList.add(resultSet.getString(1));
      }
    }
    return tagList;
  }
}
