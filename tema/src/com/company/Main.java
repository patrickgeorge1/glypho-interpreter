package com.company;


public class Main {

    public static void main(String[] args) {
        // check for args
        if (args.length < 1) {
            System.out.println("  [ERROR]: not enough args");
            System.out.println("  \"make run input=path/to/source/code base=[2..36]?optional\"");
        }

        // read raw source code
        String source_code = "";
        try {
            InputReader ir = new InputReader(args[0]);
            ir.readFromFile();
            source_code = ir.getSouceCode();
        } catch (Exception e) { System.out.println(e.getMessage()); return; }
        System.out.println(source_code);

        // translate raw Glypho code to short form
        try {
            CodeTranslater ct = new CodeTranslater(source_code);
            source_code = ct.translate();
        } catch (Exception e) { System.out.println(e.getMessage()); return; }
        System.out.println(source_code);

    }
}
