package com.placinta.toshlcharts.controllers;

import com.placinta.toshlcharts.fileupload.MultipartFileWrapper;
import com.placinta.toshlcharts.model.Expense;
import com.placinta.toshlcharts.service.ExpenseService;
import com.placinta.toshlcharts.service.reader.Parser;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fileUpload")
public class ParserController {

  private final Parser parser;
  private final ExpenseService expenseService;

  @Autowired
  public ParserController(Parser parser, ExpenseService expenseService) {
    this.parser = parser;
    this.expenseService = expenseService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String redirectToForm() {
    return "file";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String fileUploaded(MultipartFileWrapper multipartFileWrapper,
    @RequestParam(value = "fullImport", required = false) String fullImport) throws IOException, ParseException {

    List<Expense> expenses = parser.parse(multipartFileWrapper.getFile().getInputStream());
    expenseService.saveExpense(expenses, fullImport != null);
    return "successFile";
  }
}
