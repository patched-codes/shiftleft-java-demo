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

/**
 * Search login
 */
@Controller
public class SearchController {

    @RequestMapping(value = "/search/user", method = RequestMethod.GET)
    public String doGetSearch(@RequestParam String foo, HttpServletResponse response, HttpServletRequest request) {
        java.lang.Object message = new Object();
        if (isSafeExpression(foo)) {
            try {
                ExpressionParser parser = new SpelExpressionParser();
                Expression exp = parser.parseExpression(foo);
                message = exp.getValue();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            message = "Invalid input";
        }
        return message.toString();
    }

    private boolean isSafeExpression(String foo) {
        // implement your own validation logic here
        // for example, you can check if foo contains only allowed characters or patterns
        return true; // replace with your actual validation logic
    }
}
