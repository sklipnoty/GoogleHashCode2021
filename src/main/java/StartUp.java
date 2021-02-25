import domain.ProblemStatement;
import domain.Solver;
import io.InputReader;

public class StartUp {
    public static void main(String args[]) {

        InputReader inputReader = new InputReader();
        ProblemStatement problemStatement = inputReader.readProblemStatement("a.txt");
        System.out.println(problemStatement);

        Solver solver = new Solver(problemStatement);
        solver.solve();

    }
}
