package domain;

import java.util.*;

public class Solver {

    public ProblemStatement problemStatement;

    public Solver(ProblemStatement problemStatement) {
        this.problemStatement = problemStatement;
    }

    public void solve() {

    }

    public  Map<Intersection, AbstractMap.SimpleEntry<Street, Integer>> determineIntersectionPriority() {
        Map<Intersection, AbstractMap.SimpleEntry<Street, Integer>> priorityMap = new HashMap<>();

        for (Intersection intersection : this.problemStatement.intersectionMap.values()) {

            Map<Integer, Street> maxMap = new HashMap<>();

            for (Street street : intersection.incoming) {
                int totalCost = 0;

                for (Car car : street.getCarList()) {
                    totalCost += car.calcPathCost();

                    if(street.getCarList().size() == 0) {
                        totalCost += Integer.MAX_VALUE;
                    }
                }

                maxMap.putIfAbsent(totalCost, street);
            }

            Integer minValue = Collections.min(maxMap.keySet());

            // fill in priorities
            priorityMap.putIfAbsent(intersection,new AbstractMap.SimpleEntry<Street, Integer>(maxMap.get(minValue), minValue));
        }

        return priorityMap;
    }

    public List<TrafficLightSchedule> greedySolveV2(){
        for(int i = 0; i < this.problemStatement.durationOfSimulation; i++) {

            // Foreach intersection determine the best starting street!
            Map<Intersection, AbstractMap.SimpleEntry<Street, Integer>> priority = determineIntersectionPriority();


        }
    }

    public List<TrafficLightSchedule> greedySolve(){

        int duration = 0;
        List<Car> travellingCars = new ArrayList<>();
        List<TrafficLightSchedule> trafficLightScheduleList = new ArrayList<>();

        List<Car> carsToRemove = new ArrayList<>();

        for(int i = 0; i < this.problemStatement.durationOfSimulation; i++) {

            // for each car duration - 1
            for(Car car : travellingCars) {
                if(car.currentStreet.getCarList().contains(car)){
                    car.currentStreet.getCarList().remove(car);
                }

                car.streetList.remove(car.currentStreet);

                if(car.streetList.isEmpty()) {
                    carsToRemove.add(car);
                } else {
                    car.setCurrentNeededDuration(car.getCurrentNeededDuration() - 1);
                    if(car.getCurrentNeededDuration() == 0) {
                        car.currentStreet = car.streetList.get(0);
                        car.currentStreet.getCarList().add(car);
                    }
                }
            }

            for(Car car : carsToRemove) {
                travellingCars.remove(car);
            }

            Map<Intersection, Street> priorityMap = new HashMap<>();

            for (Intersection intersection : this.problemStatement.intersectionMap.values()) {

                Map<Integer, Street> maxMap = new HashMap<>();

                for (Street street : intersection.incoming) {
                    int totalCost = 0;

                    for (Car car : street.getCarList()) {
                        totalCost += car.calcPathCost();

                        if(street.getCarList().size() == 0) {
                            totalCost += Integer.MAX_VALUE;
                        }
                    }

                    maxMap.putIfAbsent(totalCost, street);
                }

                Integer minValue = Collections.min(maxMap.keySet());

                // fill in priorities
                priorityMap.putIfAbsent(intersection, maxMap.get(minValue));
            }


            for (Intersection intersection : priorityMap.keySet()) {

                if(priorityMap.get(intersection).getCarList().size() == 0)
                    continue;

                TrafficLightSchedule trafficLightSchedule = new TrafficLightSchedule(intersection, priorityMap.get(intersection), 1);
                //System.out.println(trafficLightSchedule);
                trafficLightSchedule.pickedAtDuration = i;
                Car travelling = priorityMap.get(intersection).carList.get(0);
                travelling.setCurrentNeededDuration(priorityMap.get(intersection).time);
                travellingCars.add(travelling);
                trafficLightScheduleList.add(trafficLightSchedule);

                break;
            }

        }

        return trafficLightScheduleList;
    }



}
