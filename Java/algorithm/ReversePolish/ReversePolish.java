package com.reversePolish.deom;

import java.util.Stack;

/**
 * using reverse polish notation to calculate string calculation clause
 */
public class ReversePolish {

    public static String getNotation(String notation) {
        char[] chars = notation.toCharArray();
        Stack<String> stack = new Stack<>();
        Stack<Character> methods = new Stack<>();
        String tmpNumber = "";
        methods.push('#');

        for (char singleChar : chars) {
            if (Character.isDigit(singleChar)) {
                tmpNumber = tmpNumber + singleChar;
            } else {
                if (tmpNumber != "") {
                    stack.push(tmpNumber);
                    tmpNumber = "";
                }
                switch (singleChar) {
                    case '(':
                        methods.push(singleChar);
                        break;
                    case ')':
                        while (methods.peek() != '(') {
                            stack.push(String.valueOf(methods.pop()));
                        }
                        methods.pop();
                        break;
                    case '+':
                    case '-':
                        while (methods.peek() != '#') {
                            if (methods.peek() == '(') {
                                break;
                            } else {
                                stack.push(String.valueOf(methods.pop()));
                            }
                        }
                        methods.push(singleChar);
                        break;
                    case '*':
                    case '/':
                        while (methods.peek() != '#') {
                            if (methods.peek() == '(' || methods.peek() == '+' || methods.peek() == '-') {
                                break;
                            }
                            if (methods.peek() == '*' || methods.peek() == '/') {
                                stack.push(String.valueOf(methods.pop()));
                            }
                        }
                        methods.push(singleChar);
                        break;
                }

            }
        }

        stack.push(tmpNumber);
        if (methods.peek() != '#') {
            stack.push(String.valueOf(methods.pop()));
        }

        return stack.toString();
    }

    public static float getResult(String notation) {
        char[] chars = notation.toCharArray();
        Stack<String> stack = new Stack<>();
        Stack<Character> methods = new Stack<>();
        String tmpNumber = "";
        methods.push('#');

        for (char singleChar : chars) {
            if (Character.isDigit(singleChar)) {
                tmpNumber = tmpNumber + singleChar;
            } else {
                if (tmpNumber != "") {
                    stack.push(tmpNumber);
                    tmpNumber = "";
                }
                switch (singleChar) {
                    case '(':
                        methods.push(singleChar);
                        break;
                    case ')':
                        while (methods.peek() != '(') {
                            stack.push(String.valueOf(calculator(stack, methods)));
                        }
                        methods.pop();
                        break;
                    case '+':
                    case '-':
                        while (methods.peek() != '#') {
                            if (methods.peek() == '(') {
                                break;
                            } else {
                                stack.push(String.valueOf(calculator(stack,methods)));
                            }
                        }
                        methods.push(singleChar);
                        break;
                    case '*':
                    case '/':
                        while (methods.peek() != '#') {
                            if (methods.peek() == '(' || methods.peek() == '+' || methods.peek() == '-') {
                                break;
                            }
                            if (methods.peek() == '*' || methods.peek() == '/') {
                                stack.push(String.valueOf(calculator(stack,methods)));
                            }
                        }
                        methods.push(singleChar);
                        break;
                }

            }
        }

        stack.push(tmpNumber);
        if (methods.peek() != '#') {
            stack.push(String.valueOf(calculator(stack,methods)));
        }

        return Float.parseFloat(stack.pop());
    }

    private static float calculator(Stack<String> stack, Stack<Character> methods) {
        float left;
        float right;
        float result = 0;
        right = Float.parseFloat(stack.pop());
        left = Float.parseFloat(stack.pop());
        switch (methods.pop()) {
            case '+':
                result = left + right;
                break;
            case '-':
                result = left - right;
                break;
            case '*':
                result = left * right;
                break;
            case '/':
                result = left / right;
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        String middleNotation = "12*(3+4)-6+8/2+2";
        String notation = getNotation(middleNotation);
        System.out.println(notation);

        float result = getResult(middleNotation);
        System.out.println(result);
    }
}
