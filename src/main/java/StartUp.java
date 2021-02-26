import domain.ProblemStatement;
import domain.Solution;
import domain.Solver;
import domain.TrafficLightSchedule;
import io.InputReader;
import io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class StartUp {
    public static void main(String args[]) {

        List<String> files = new ArrayList<>();
      //  files.add("a");
        files.add("b");
      //  files.add("c");
      //  files.add("d");
      //  files.add("e");
      //  files.add("f");

        for(String file : files) {
            InputReader inputReader = new InputReader();
            ProblemStatement problemStatement = inputReader.readProblemStatement(file + ".txt");
            //System.out.println(problemStatement);

            Solver solver = new Solver(problemStatement);
            List<TrafficLightSchedule> trafficLightScheduleList = solver.greedySolveV2();
            Solution solution = new Solution(trafficLightScheduleList);
            solution.durationOfSimulation = problemStatement.durationOfSimulation;
            // solution.prepareOutput();

            OutputWriter outputWriter = new OutputWriter();
            outputWriter.writeOutput(file + "out.txt", solution);
        }
    }
}
