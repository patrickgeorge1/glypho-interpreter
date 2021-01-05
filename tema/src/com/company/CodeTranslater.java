package com.company;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class can trnaslate a sequence of Glypho code into it's short form
 *
 * <p> Principal methods :   {@code String translate()}
 * and <br> {@code String convertInstruction(String glyphoInstruction)}</p>
 *
 * @since java 11
 * @author Patrick Vitoga
 */
public class CodeTranslater {
    private String source_code;
    private static final Map<Integer, String> instructions = new HashMap<>() {{
       put(0, "n");  put(1, "i");  put(4, ">");
       put(5, "\\"); put(6, "1");  put(16, "<");
       put(17, "d"); put(18, "+"); put(20, "[");
       put(21, "o"); put(22, "*"); put(24, "e");
       put(25, "-"); put(26, "!"); put(27, "]");
    }};

    private CodeTranslater() {}

    public CodeTranslater(String souce_code) {
        this.source_code = souce_code;
    }

    /**
     * try to translate raw code to short form
     * @return shortened form
     * @throws Exception if lenght not % 4 or invalid instruction
     */
    public String translate() throws Exception {
        if (source_code.length() % 4 != 0)
            throw new Exception("Exception: 0" + " raw code not % 4"); // TODO
        Stack<Pair> braces = new Stack<>();

        StringBuilder translated_code = new StringBuilder();
        for (int i = 0; i < source_code.length(); i+= 4) {
            String raw_instruction = source_code.substring(i, i + 4);

            // check for instruction ==> throw exception
            String instruction = convertInstruction(raw_instruction);
            translated_code.append(instruction);

            // check for invalid braces ==> throw exception
            checkForBraces(braces, instruction, i/4);
        }
        // check to see if all braces were closed
        if (!braces.isEmpty())
            throw new Exception("Error: " + source_code.length() / 4 + "  not all braces were closed"); // TODO

        return translated_code.toString();
    }

    /**
     * convert 4 chars in corresponding instruction if possible
     * @return short form
     */
    public static String convertInstruction(String instruction) throws Exception {
        HashMap<Character, Integer> positions = new HashMap<>();
        int next_position = 0;

        Integer power = 64;
        Integer matchingKey = 0;

        for (Character c : instruction.toCharArray()) {
            if (!positions.containsKey(c)) {
                positions.put(c, next_position);
                next_position++;
            }
            matchingKey += power * positions.get(c);
            power /= 4;
        }
        String translation = instructions.getOrDefault(matchingKey, "none");
        if (translation.equals("none"))
            throw new Exception("invalid instruction");

        //System.out.println(instruction + " ---" + matchingKey + "---> " + instructions.get(matchingKey));
        return instructions.get(matchingKey);
    }

    /**
     * convert 4 BigInts in corresponding instruction if possible
     * @return short form
     */
    public static String convertInstruction(BigInteger[] numbers) throws Exception {
        HashMap<BigInteger, Integer> positions = new HashMap<>();
        int next_position = 0;

        Integer power = 64;
        Integer matchingKey = 0;

        for (BigInteger n : numbers) {
            if (!positions.containsKey(n)) {
                positions.put(n, next_position);
                next_position++;
            }
            matchingKey += power * positions.get(n);
            power /= 4;
        }
        String translation = instructions.getOrDefault(matchingKey, "none");
        if (translation.equals("none"))
            throw new Exception("invalid instruction");

        return instructions.get(matchingKey);
    }

    /**
     * checks if braces order is correct
     * @throws Exception if braces are placed incorrect
     */
    public void checkForBraces(Stack<Pair> braces, String instruction, Integer index) throws Exception {
        if (instruction.equals("[")) braces.push(new Pair(1, index));
        if (instruction.equals("]")) {
            if (braces.isEmpty()) {
                throw new Exception("Error:" + index);
            } else {
                braces.pop();
            }
        }
    }

    static class Pair {
        int type;
        int index;
        Pair(int type, int index) {
            this.type = type;
            this.index = index;
        }
    }

}
