package com.company;


import static java.lang.System.exit;

public class Main {
    public static Integer base = 10;

    public static void main(String[] args) {
        // check for args
        if (args.length < 1) {
            System.err.println("  [ERROR]: not enough args");
            System.out.println("  \"make run input=path/to/source/code base=[2..36]?optional\"");
        }
        if (args.length == 2) base = Integer.parseInt(args[1]);
        //System.out.println("base: " + base);

        // read raw source code
        String source_code = "";
        try {
            InputReader ir = new InputReader(args[0]);
            ir.readFromFile();
            source_code = ir.getSouceCode();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        //System.out.println(source_code);

        // translate raw Glypho code to short form
        try {
            CodeTranslater ct = new CodeTranslater(source_code);
            source_code = ct.translate();
        } catch (Exception e) { System.err.println(e.getMessage()); exit(-1); }
        //System.out.println(source_code);

        // execute short form code
        try {
            Interpreter interpreter = new Interpreter(source_code);
            interpreter.execute();
        } catch (Exception e) { System.err.println(e.getMessage()); exit(-2); }

        exit(0);
    }
}
