package com.company;

import java.io.*;

/**
 * This class is used to read glypho code
 * from a file.
 *
 * @since java 11
 * @author Patrick Vitoga
 */
public class InputReader {
    private final StringBuilder souce_code = new StringBuilder();
    private String file_name = "";

    public InputReader(String file_name) {
        this.file_name = file_name;
    }

    private InputReader() {}

    /**
     * Read glyph code from file
     * @throws Exception for FileNotFound or IOException
     */
    public void readFromFile() throws Exception {
        File souce_file = new File(file_name);
        try {
            BufferedReader br = new BufferedReader(new FileReader(souce_file));
            String line;
            while ((line = br.readLine()) != null) {
                souce_code.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Souce code file not found");
        } catch (IOException e) {
            throw new Exception("Could not read form file");
        }
    }

    public String getSouceCode() {
        return souce_code.toString();
    }
}
