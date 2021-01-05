package com.company;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class can read and execute a sequence of code obtained mainly
 * by translating Glypho code using {@code CodeTranslater}.
 *
 * <p> Principal method :   {@code void execute(String valid_code)}</p>
 * <p> Extra method :   {@code void executeInstruction(String valid_instruction)}</p>
 * <p> Future methods : </p>
 * {@code void executeInstruction(String valid_code, ArrayDeque memStack, Stack openedBracketsPos, int numberOfSkippedBrackets)};
 * <br>
 *
 *
 * @since java 11
 * @author Patrick Vitoga
 */
public class Interpreter {
    private final String code;
    private int skippedBrackets = 0;
    private final Stack<Integer> openedBrackets = new Stack<>();
    private final ArrayDeque<BigInteger> memoryStack = new ArrayDeque<>();
    private final Scanner scanner = new Scanner(System.in);


    public Interpreter(String code) {
        this.code = code;
    }

    public void execute() throws Exception {
        char[] instructions = code.toCharArray();

        int execution_point = 0;
        while (execution_point < instructions.length) {
            char instruction = instructions[execution_point];
            execution_point = executeInstruction(instruction, execution_point);
        }
    }

    public int executeInstruction(char instruction, int line) throws Exception {
        if (skippedBrackets != 0 && (instruction != ']' && instruction != '[')) return line + 1;

        switch (instruction) {
            case 'n':
                break;

            case 'i':
                String input_string = scanner.nextLine();

                // take care of negative numbers
                String sign = "+1";
                if (input_string.charAt(0) == '-') {
                    input_string = input_string.substring(1);
                    sign = "-1";
                }

                if (!input_string.chars().allMatch(Character :: isDigit)) throw new Exception("Exception:" + line);
                BigInteger input = new BigInteger(input_string);
                memoryStack.addLast(input.multiply(new BigInteger(sign)));
                break;

            case '>':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                BigInteger topStack = memoryStack.removeLast();
                memoryStack.addFirst(topStack);
                break;

            case '\\':
                if (memoryStack.size() < 2) throw new Exception("Exception:" + line);
                BigInteger firstNumber = memoryStack.removeLast();
                BigInteger secondNumber = memoryStack.removeLast();
                memoryStack.addLast(firstNumber);
                memoryStack.addLast(secondNumber);
                break;

            case '1':
                memoryStack.addLast(new BigInteger("1"));
                break;

            case '<':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                BigInteger bottomStack = memoryStack.removeFirst();
                memoryStack.addLast(bottomStack);
                break;

            case 'd':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                BigInteger topStackNumber = memoryStack.peekLast();
                memoryStack.addLast(topStackNumber);
                break;

            case '+':
                if (memoryStack.size() < 2) throw new Exception("Exception:" + line);
                BigInteger sumOperand1 = memoryStack.removeLast();
                BigInteger sumOperand2 = memoryStack.removeLast();
                memoryStack.addLast(sumOperand1.add(sumOperand2));
                break;

            case '[':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                if (skippedBrackets != 0 || memoryStack.peekLast().equals(new BigInteger("0"))) {
                    skippedBrackets++;
                    return line + 1;
                }
                openedBrackets.push(line);
                break;

            case 'o':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                BigInteger output = memoryStack.removeLast();
                System.out.println(output);
                break;

            case '*':
                if (memoryStack.size() < 2) throw new Exception("Exception:" + line);
                BigInteger mulOperand1 = memoryStack.removeLast();
                BigInteger mulOperand2 = memoryStack.removeLast();
                memoryStack.addLast(mulOperand1.multiply(mulOperand2));
                break;

            case 'e':
                if (memoryStack.size() < 4) throw new Exception("Exception:" + line);
                BigInteger [] numbers = {
                        memoryStack.removeLast(), memoryStack.removeLast(),
                        memoryStack.removeLast(), memoryStack.removeLast()
                };
                String command = CodeTranslater.convertInstruction(numbers);
                line = executeInstruction(command.charAt(0), line);
                return line;

            case '-':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                BigInteger toNegate = memoryStack.removeLast();
                memoryStack.addLast(toNegate.multiply(new BigInteger("-1")));
                break;

            case '!':
                if (memoryStack.size() < 1) throw new Exception("Exception:" + line);
                memoryStack.removeLast();
                break;

            case ']':
                if (skippedBrackets != 0) {
                    skippedBrackets--;
                    break;
                } else {
                    if (memoryStack.peekLast().equals(new BigInteger("0"))) {
                        openedBrackets.pop();
                    }
                    else {
                        line = openedBrackets.peek();
                    }
                }
                break;
        }

        return line + 1;
    }
}
