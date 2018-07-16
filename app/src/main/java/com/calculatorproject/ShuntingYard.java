package com.calculatorproject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Stack;

public class ShuntingYard {



    static String infixToPostfix (String infix) {

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
}
