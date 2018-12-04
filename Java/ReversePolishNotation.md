# Reverse Polish Notation(逆波兰表达式)
举个简单的例子，平常我们写的数学表达式a+b，就是一种中缀表达式，写成后缀表达式就是ab+。再举一个复杂的例子，中缀表达式(a+b)\*c-(a+b)/e的逆波兰式是ab+c\*ab+e/-。

在弄清楚概念以及题目的要求之后，接下来就要编写算法了。那么将一个表达式转换为逆波兰式的算法思想是什么呢？

- 首先，需要分配2个栈，栈s1用于临时存储运算符（含一个结束符号），此运算符在栈内遵循越往栈顶优先级越高的原则；栈s2用于输入逆波兰式，为方便起见，栈s1需先放入一个优先级最低的运算符，在这里假定为'#'；

- 从中缀式的左端开始逐个读取字符x，逐序进行如下步骤：

    - 若x是操作数，则分析出完整的运算数（在这里为方便，用字母代替数字），将x直接压入栈s2；

    - 若x是运算符，则分情况讨论：
        若x是'('，则直接压入栈s1；
        若x是')'，则将距离栈s1栈顶的最近的'('之间的运算符，逐个出栈，依次压入栈s2，此时抛弃'('；
        若x是除'('和')'外的运算符，则再分如下情况讨论：
            若当前栈s1的栈顶元素为'('，则将x直接压入栈s1；
            若当前栈s1的栈顶元素不为'('，则将x与栈s1的栈顶元素比较，若x的优先级大于栈s1栈顶运算符优先级，
               则将x直接压入栈s1。否则，将栈s1的栈顶运算符弹出，压入栈s2中，直到栈s1的栈顶运算符优先级别
               低于（不包括等于）x的优先级，或栈s2的栈顶运算符为'('，此时再则将x压入栈s1;

- 在进行完(2)后，检查栈s1是否为空，若不为空，则将栈中元素依次弹出并压入栈s2中（不包括'#'）；　　　　　　 

- 完成上述步骤后，栈s2便为逆波兰式输出结果。但是栈s2应做一下逆序处理，因为此时表达式的首字符位于栈底；

java 实现如下：

```java
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
```
