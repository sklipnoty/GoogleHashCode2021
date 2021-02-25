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

            printWriter.println(intersectionSet.size());

            for(Intersection intersection : intersectionSet) {
                printWriter.println(intersection.id);
                printWriter.println(mapping.get(intersection).size());

                for(TrafficLightSchedule trafficLightSchedule : mapping.get(intersection)) {
                    printWriter.println(trafficLightSchedule.getIncomingStreet().name + " " + trafficLightSchedule.getDuration());
                }
            }

            printWriter.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
