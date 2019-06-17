package com.wing.lynne.jdkApiDemo;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.MessageFormat;

/**
 * 俩种方式实现字符串表达式的运算
 *
 * 将操作数用{index}标识，便于后续动态替换
 * 将操作符用$index}标识，便于后续动态替换
 *
 * 使用scriptengine或者spring spel expression计算被替换后的字符串的值
 */
public class MathExpression {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");

    public static void main(String[] args) throws ScriptException {

        String expression = "({0}$0}{1})$1}{2}$2}{3}";
        String expression1 = "({0}$0}{1})$1}{2}$2}{3}";
        String expression2 = "({0}$0}{1})$1}{2}$2}{3}";
        String expression3 = "({0}$0}{1})$1}{2}$2}{3}";

        Double[] paramArray = {1.4, 2.5, 3.2, 4.1};
        Double[] paramArray1 = {1.5, 2.5, 3.0, 3.0};
        Double[] paramArray2 = {1.4, 2.5, 3.0, 4.1};
        Double[] paramArray3 = {1.4, 2.5, 3.2, 4.9};
        Object[] operatorArray = {'+', '*', '/'};

        expression = MessageFormat.format(expression, paramArray);
        expression1 = MessageFormat.format(expression1, paramArray1);
        expression2 = MessageFormat.format(expression2, paramArray2);
        expression3 = MessageFormat.format(expression3, paramArray3);

        System.out.println(expression);

        expression = expression.replaceAll("\\$", "{");
        expression1 = expression1.replaceAll("\\$", "{");
        expression2 = expression2.replaceAll("\\$", "{");
        expression3 = expression3.replaceAll("\\$", "{");

        System.out.println(expression);

        expression = MessageFormat.format(expression, operatorArray);
        expression1 = MessageFormat.format(expression1, operatorArray);
        expression2 = MessageFormat.format(expression2, operatorArray);
        expression3 = MessageFormat.format(expression3, operatorArray);

        System.out.println(expression);


        Object result = engine.eval(expression);
        Object result1 = engine.eval(expression1);
        Object result2 = engine.eval(expression2);
        Object result3 = engine.eval(expression3);


        ExpressionParser parser = new SpelExpressionParser();

        Object value = parser.parseExpression(expression).getValue();
        Object value1 = parser.parseExpression(expression1).getValue();
        Object value2 = parser.parseExpression(expression2).getValue();
        Object value3 = parser.parseExpression(expression3).getValue();


        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

        System.out.println(value);
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);

    }
}
