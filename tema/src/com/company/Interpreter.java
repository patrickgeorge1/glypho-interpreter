package com.company;

import java.util.Stack;

/**
 * This class can read and execute a sequence of code obtained mainly
 * by translating Glypho code using {@code CodeTranslater}.
 *
 * <p> Principal method :   {@code void interpret(String valid_code)}</p>
 *
 * @since java 11
 * @author Patrick Vitoga
 */
public class Interpreter {
    public String code;

    public Interpreter(String code) {
        this.code = code;
    }

    public void interpret() throws Exception {

    }

    public static void  interpretInstruction(String instruction, Stack<Integer> stack) {

    }
}
