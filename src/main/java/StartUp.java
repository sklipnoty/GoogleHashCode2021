import domain.*;
import io.InputReader;
import io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartUp {
    public static void main(String args[]) {

       // SequenceGenerator sequenceGenerator = new SequenceGenerator(500);
       // sequenceGenerator.findBestSequenceDuo(Arrays.asList(Arrays.asList(1,3,5,8), Arrays.asList(20,60)));

        List<String> files = new ArrayList<>();
       // files.add("a");
        files.add("b");
        files.add("c");
        files.add("d");
        files.add("e");
        files.add("f");

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
