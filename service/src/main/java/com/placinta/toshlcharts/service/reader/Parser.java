package com.placinta.toshlcharts.service.reader;

import com.placinta.toshlcharts.model.Expense;
import com.placinta.toshlcharts.model.Tag;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by cristi on 11/15/14.
 */
@Component
public class Parser {

  @Autowired
  private static DataSource dataSource;


  public List<Expense> parse(InputStream inputStream) throws IOException, ParseException {

    List<String> lines = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
    List<Expense> expenses = new ArrayList<>();
    String line;

    for (int i = 1; i <= lines.size() - 1; i++) {
      line = lines.get(i);
      String splitBy = "\",\"";
      String[] splitLine = line.split(splitBy);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date date = formatter.parse(splitLine[0].replace("\"", ""));
      List<Tag> tags = getTags(splitLine[1]);

      Expense expense = new Expense();
      expense.setDate(date);
      expense.setTags(tags);
      expense.setAmount(getDouble(splitLine[2]));
      expense.setIncome(getDouble(splitLine[3]));
      expense.setCurrency(splitLine[4]);
      expense.setDescription(splitLine[5].replace("\"", ""));
      //System.out.println(expense.toString());

      expenses.add(expense);
    }
    return expenses;
  }

  private List<Tag> getTags(String tagList) {
    List<String> tagNames = new ArrayList<>(Arrays.asList(tagList.split(",")));
    List<Tag> tags = new ArrayList<>(tagNames.size());
    for (String tagName: tagNames) {
      Tag tag = new Tag();
      tag.setTagName(StringUtils.trim(tagName));
      tags.add(tag);
    }
    return tags;
  }

  private static double getDouble(String s) {
    if (s.length() > 0) {
      return Double.parseDouble(s.replace(",", ""));
    }
    return 0;
  }

}
