import domain.ProblemStatement;
import io.InputReader;

public class StartUp {
    public static void main(String args[]) {

        InputReader inputReader = new InputReader();
        ProblemStatement problemStatement = inputReader.readProblemStatement("a.txt");
        System.out.println(problemStatement);

    }
}
