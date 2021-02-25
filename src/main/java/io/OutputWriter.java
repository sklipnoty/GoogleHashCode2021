package io;

import domain.Intersection;
import domain.Solution;
import domain.TrafficLightSchedule;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class OutputWriter {
    public OutputWriter(){}

    public void writeOutput(String fileName, Solution solution) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            Set<Intersection> intersectionSet = new HashSet<>();
            Map<Intersection, List<TrafficLightSchedule>> mapping = new HashMap<>();

            for(TrafficLightSchedule trafficLightSchedule : solution.trafficLightScheduleList) {
                intersectionSet.add(trafficLightSchedule.getIntersection());
            }

            mapping = (Map<Intersection, List<TrafficLightSchedule>>) solution.trafficLightScheduleList.stream().collect(Collectors.groupingBy(x->x.getIntersection()));

            List<String> outputs = new ArrayList<>();
            int totalNumberOfIntersections = 0;

            for(Intersection intersection : intersectionSet) {

                List<TrafficLightSchedule> trafficLightScheduleSingle = mapping.get(intersection);
                totalNumberOfIntersections = totalNumberOfIntersections +1;

                outputs.add("" +intersection.id);
                outputs.add("" + trafficLightScheduleSingle.size());

                for (TrafficLightSchedule trafficLightSchedule : trafficLightScheduleSingle) {
                    outputs.add(""+trafficLightSchedule.getIncomingStreet().name + " " + solution.durationOfSimulation);
                }
            }

            printWriter.println(totalNumberOfIntersections);

            for(String output : outputs) {
                printWriter.println(output);
            }

            printWriter.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
