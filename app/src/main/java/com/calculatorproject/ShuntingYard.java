package com.calculatorproject;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.function.BiFunction;

public class ShuntingYard {
    private static Context mContext;

    static String infixToPostfix(String infix) {

        final String ops = "-+÷×^";

        StringBuilder stringBuilder = new StringBuilder();
        Stack<Integer> stack = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {
                continue;
            }

            char character = token.charAt(0);
            int index = ops.indexOf(character);

            if (index != -1) {
                if (stack.isEmpty()) {
                    stack.push(index);
                } else {
                    while (!stack.isEmpty()) {
                        int prec2 = stack.peek() / 2;
                        int prec1 = index / 2;

                        if (prec2 > prec1 || (prec2 == prec1 && character != '^')) {
                            stringBuilder.append(ops.charAt(stack.pop())).append(' ');
                        } else {
                            break;
                        }
                    }

                    stack.push(index);
                }
            } else if (character == '(') {
                stack.push(-2);
            } else if (character == ')') {

                while (stack.peek() != -2) {
                    stringBuilder.append(ops.charAt(stack.pop())).append(' ');
                }
                stack.pop();
            } else {
                stringBuilder.append(token).append(' ');
            }
        }

        while (!stack.isEmpty()) {
            stringBuilder.append(ops.charAt(stack.pop())).append(' ');
        }

        return stringBuilder.toString();
    }

    static BigDecimal evaluateRPN(String postfix) {

        Stack<BigDecimal> tokens = new Stack<>();

        for (String token : postfix.split(" ")) {
            Sign sign = Sign.find(token);

            if (sign != null) {
                calcSign(tokens, sign);
            } else {
                BigDecimal bigToken = new BigDecimal(token);
                tokens.push(bigToken);

            }
        }

        return tokens.pop();
    }


    protected static Stack<BigDecimal> calcSign(Stack<BigDecimal> tokens, Sign sign) {
        tokens.push(sign.apply(tokens.pop(), tokens.pop()));
        return tokens;
    }

    public enum Sign {

        ADD("+") {
            public BigDecimal apply(BigDecimal num1, BigDecimal num2) {
                return num2.add(num1);
            }
        },
        REMOVE("-") {
            public BigDecimal apply(BigDecimal num1, BigDecimal num2) {
                return num2.subtract(num1);
            }
        },
        MULTIPLY("×") {
            public BigDecimal apply(BigDecimal num1, BigDecimal num2) {
                return num2.multiply(num1);
            }
        },
        DIVIDE("÷") {
            public BigDecimal apply(BigDecimal num1, BigDecimal num2) {
                return num2.divide(num1);
            }
        };

        private final String operatorText;

        private Sign(String operatorText) {
            this.operatorText = operatorText;
        }

        public abstract BigDecimal apply(BigDecimal x1, BigDecimal x2);

        private static final Map<String, Sign> map;

        static {
            map = new HashMap<>();
            for (Sign sign : Sign.values()) {
                map.put(sign.operatorText, sign);
            }
        }

        public static Sign find(String sign) {
            return map.get(sign);
        }

    }

}