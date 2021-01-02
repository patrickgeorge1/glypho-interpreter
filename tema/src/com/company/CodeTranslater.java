package com.company;

import java.util.HashMap;
import java.util.Map;

public class CodeTranslater {
    private String source_code;
    Map<Integer, String> instructions = new HashMap<>() {{
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
            throw new Exception("Exception:<0> syntactical problem");

        StringBuilder translated_code = new StringBuilder();
        for (int i = 0; i < source_code.length(); i+= 4) {
            String instruction = source_code.substring(i, i + 4);
            translated_code.append(convertInstruction(instruction));
        }
        return translated_code.toString();
    }

    /**
     * convert 4 chars in corresponding instruction if possible
     * @return short form
     */
    public String convertInstruction(String instruction) throws Exception {
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
            throw new Exception("incorrct instruction");
        return instructions.get(matchingKey);
    }

}
