package io;

import domain.Solution;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputWriter {
    public OutputWriter(){}

    public void writeOutput(String fileName, Solution solution) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
