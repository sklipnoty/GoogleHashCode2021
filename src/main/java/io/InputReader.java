package io;

import domain.ProblemStatement;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InputReader {
    public InputReader() {}

    public ProblemStatement readProblemStatement(String fileName) {
        Path path = Paths.get(fileName);
        Path absolutePath = path.toAbsolutePath();
        ProblemStatement problemStatement = new ProblemStatement();

        try {
            List<String> lines = Files.readAllLines(absolutePath);
            String[] firstLine = lines.get(0).split(" ");
            System.out.println(firstLine);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return problemStatement;

    }
}
