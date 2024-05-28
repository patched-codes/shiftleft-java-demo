package io.shiftleft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Search login
 */
@Controller
public class SearchController {

  @RequestMapping(value = "/search/user", method = RequestMethod.GET)
  public String doGetSearch(@RequestParam String foo, HttpServletResponse response, HttpServletRequest request) {
    java.lang.Object message = new Object();
    try {
      if (validateInput(foo)) {
        ExpressionParser parser = new SpelExpressionParser();
        String sanitizedFoo = StringEscapeUtils.escapeSql(foo);
        Expression exp = parser.parseExpression(sanitizedFoo);
        message = (Object) exp.getValue();
      } else {
        throw new IllegalArgumentException("Invalid input");
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return message.toString();
  }

  private boolean validateInput(String input) {
    // Basic validation logic; can be improved for specific use cases
    return input.matches("^[a-zA-Z0-9_]*$");
  }
}
