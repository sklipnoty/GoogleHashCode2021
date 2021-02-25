package domain;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public List<TrafficLightSchedule> trafficLightScheduleList;
    public Integer durationOfSimulation;
    public int numberOfIntersections = 0;

    public Solution(List<TrafficLightSchedule> trafficLightScheduleList) {
        this.trafficLightScheduleList = trafficLightScheduleList;
    }

    public void prepareOutput(){

        Set<Intersection> intersectionSet = new HashSet<>();

        for(TrafficLightSchedule trafficLightSchedule : trafficLightScheduleList) {
            intersectionSet.add(trafficLightSchedule.getIntersection());
        }

        Map<Intersection, List<TrafficLightSchedule>> mapping = (Map<Intersection, List<TrafficLightSchedule>>) trafficLightScheduleList.stream().collect(Collectors.groupingBy(x->x.getIntersection()));

        List<TrafficLightSchedule> list = new ArrayList<>();

        for(Intersection intersection : mapping.keySet()) {
            System.out.println("Intersection \n\n\n");

            List<TrafficLightSchedule> listToRemove = new ArrayList<>();
            List<TrafficLightSchedule> intersectionList = mapping.get(intersection);

            intersectionList.sort(Comparator.comparing(TrafficLightSchedule::getPickedAtDuration));

            for(int i = 0; i < intersectionList.size();i++){
               // System.out.println(trafficLightScheduleList.get(i).getIncomingStreet().name + " " + trafficLightScheduleList.get(i).getPickedAtDuration());
                // pick one
                TrafficLightSchedule trafficLightSchedule = intersectionList.get(i);

                boolean isTheSameIncoming = true;
                int j = i+1;
                int totalDuration = 1;

                //check if next ones have the same
                while(isTheSameIncoming && j < intersectionList.size()) {
                    TrafficLightSchedule next = intersectionList.get(j);

                    if(next.getIncomingStreet().equals(trafficLightSchedule.getIncomingStreet())) {
                        totalDuration += 1;
                        listToRemove.add(next);
                        j = j+1;
                        i = i+1;
                    } else {
                        isTheSameIncoming = false;
                    }
                }
                trafficLightSchedule.setDuration(totalDuration);
            }

            for(TrafficLightSchedule remove : listToRemove) {
                intersectionList.remove(remove);
            }

            list.addAll(intersectionList);

        }

        trafficLightScheduleList = list;



    }
}
