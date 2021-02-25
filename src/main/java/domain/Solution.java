package domain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public List<TrafficLightSchedule> trafficLightScheduleList;

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

        for(Intersection intersection : mapping.keySet()) {
            System.out.println("==========================");

            for(TrafficLightSchedule trafficLightSchedule : trafficLightScheduleList) {
                System.out.println(trafficLightSchedule);
            }
        }



    }
}
