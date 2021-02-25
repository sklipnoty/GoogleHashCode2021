import domain.ProblemStatement;
import domain.Solution;
import domain.Solver;
import domain.TrafficLightSchedule;
import io.InputReader;
import io.OutputWriter;

import java.util.List;

public class StartUp {
    public static void main(String args[]) {

        InputReader inputReader = new InputReader();
        ProblemStatement problemStatement = inputReader.readProblemStatement("b.txt");
        //System.out.println(problemStatement);

        Solver solver = new Solver(problemStatement);
        List<TrafficLightSchedule> trafficLightScheduleList = solver.greedySolve();
        Solution solution = new Solution(trafficLightScheduleList);
        solution.durationOfSimulation = problemStatement.durationOfSimulation;
       // solution.prepareOutput();

        OutputWriter outputWriter = new OutputWriter();
        outputWriter.writeOutput("bout.txt", solution);
    }
}
