package domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solver {

    public ProblemStatement problemStatement;

    public Solver(ProblemStatement problemStatement) {
        this.problemStatement = problemStatement;
    }

    public void solve() {

    }

    public  Map<Intersection, Street> determineIntersectionPriority() {
        Map<Intersection,Street> priorityMap = new HashMap<>();

        for (Intersection intersection : this.problemStatement.intersectionMap.values()) {

            Map<Integer, Street> maxMap = new HashMap<>();

            for (Street street : intersection.incoming) {

                if(street.carList.size() == 0)
                    continue;

                int totalCost = 0;

                for (Car car : street.getCarList()) {
                    totalCost += car.calcPathCost();

                    if(street.getCarList().size() == 0) {
                        totalCost += Integer.MAX_VALUE;
                    }
                }

                maxMap.putIfAbsent(totalCost, street);
            }

            if(!maxMap.isEmpty()) {
                Integer minValue = Collections.min(maxMap.keySet());
                // fill in priorities
                priorityMap.putIfAbsent(intersection, maxMap.get(minValue));
            }
        }

        return priorityMap;
    }

    public List<TrafficLightSchedule> getInitialScheduleBasedOnPriority(
            Map<Intersection, Street> priorityMap,
            Integer currentSimulationDuration,
            List<Car> travellingCars) {

        List<TrafficLightSchedule> scheduleList = new ArrayList<>();

        for (Intersection intersection : priorityMap.keySet()) {
            Street street  = priorityMap.get(intersection);
            TrafficLightSchedule trafficLightSchedule = new TrafficLightSchedule(intersection, street, 1);
            trafficLightSchedule.pickedAtDuration = currentSimulationDuration;

            Car travelling = street.carList.get(0);
            travelling.currentStreet = travelling.streetList.get(0);
            travelling.setCurrentNeededDuration(travelling.currentStreet.time);
            street.carList.remove(0);
            travellingCars.add(travelling);
            scheduleList.add(trafficLightSchedule);
        }

        return scheduleList;
    }

    public void moveCars(List<Car> travellingCars) {
        List<Car> carsToRemove = new ArrayList<>();

        for(Car car : travellingCars) {
            car.setCurrentNeededDuration(car.getCurrentNeededDuration() - 1);

            if(car.getCurrentNeededDuration() == 0) {
                car.streetList.remove(car.currentStreet);
               // System.out.println("Move Car " + car.id + " Removing" + car.currentStreet);
                car.currentStreet.carList.add(car);
                car.numberOfStreets = car.numberOfStreets - 1;
                carsToRemove.add(car);
            }

            if(car.streetList.isEmpty()) {
                // cars is done travelling
                carsToRemove.add(car);
                car.currentStreet.carList.remove(car);
            }
        }

        for(Car car : carsToRemove) {
            travellingCars.remove(car);
        }

        for(Car car : travellingCars) {
           // System.out.println(car.id + " " + car.streetList.size() + " " + car.numberOfStreets);
        }
    }

    public List<TrafficLightSchedule> greedySolveV2(){
        List<TrafficLightSchedule> allSchedules = new ArrayList<>();
        List<Car> travellingCars = new ArrayList<>();

        //this.problemStatement.durationOfSimulation
        for(int i = 0; i < this.problemStatement.durationOfSimulation; i++) {
            //System.out.println("Simulating duration " + i);

            // Foreach intersection determine the best starting street!
            Map<Intersection, Street> priority = determineIntersectionPriority();

            // Make a schedule foreach street
            List<TrafficLightSchedule> schedule = getInitialScheduleBasedOnPriority(priority, i, travellingCars);
            allSchedules.addAll(schedule);

            // Move cars forward with 1 simulation unit.
            moveCars(travellingCars);

        }

        //Optimize schedules!
        return mergeSchedules(allSchedules);
    }

    private List<TrafficLightSchedule> mergeSchedules(List<TrafficLightSchedule> allSchedules) {
        List<TrafficLightSchedule> trafficLightScheduleListFinal = new ArrayList<>();

        Map<Intersection, List<TrafficLightSchedule>> mapping = (Map<Intersection, List<TrafficLightSchedule>>) allSchedules.stream().collect(Collectors.groupingBy(x->x.getIntersection()));

        for(Intersection intersection : mapping.keySet()) {

            System.out.println("Intersection " + intersection.id);

            List<TrafficLightSchedule> sorted = mapping.get(intersection);
            sorted.sort(Comparator.comparing(TrafficLightSchedule::getPickedAtDuration));

            Map<Integer, List<TrafficLightSchedule>> sortedMap = (Map<Integer, List<TrafficLightSchedule>>) sorted.stream().collect(Collectors.groupingBy(x->x.pickedAtDuration));

            Set<Street> incomingStreets = new HashSet<>();
            List<Street> incomingStreetsList = new ArrayList<>();

            for(Integer simulationRound : sortedMap.keySet()) {
                System.out.println(simulationRound + " " + sortedMap.get(simulationRound).size() + " " + sortedMap.get(simulationRound).get(0).incomingStreet.name);
                incomingStreets.add(sortedMap.get(simulationRound).get(0).incomingStreet);
                incomingStreetsList.add(sortedMap.get(simulationRound).get(0).incomingStreet);
            }

            List<Street> streetList = new ArrayList<>(incomingStreets);

            if(incomingStreets.size() == 1) {
                // no problem just turn that street green the whole time
                trafficLightScheduleListFinal.add(new TrafficLightSchedule(intersection, streetList.get(0), problemStatement.durationOfSimulation));
            } else if(incomingStreets.size() > 1) {
                // find good correlation
                // bad approach: Modify duration for occurences > 1 // simply turn them on cyclicly for 1 sec
                Map<Street, Long> countMap = incomingStreetsList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                for(Street street : streetList) {
                    trafficLightScheduleListFinal.add(new TrafficLightSchedule(intersection, street, 1));
                }
            }

        }

        return trafficLightScheduleListFinal;
    }

    public void consoleFancy(List<TrafficLightSchedule> schedules) {
        // Show output
        Map<Intersection, List<TrafficLightSchedule>> mapping = (Map<Intersection, List<TrafficLightSchedule>>) schedules.stream().collect(Collectors.groupingBy(x->x.getIntersection()));

        for(Intersection intersection : mapping.keySet()) {
            List<TrafficLightSchedule> sorted = mapping.get(intersection);
            sorted.sort(Comparator.comparing(TrafficLightSchedule::getPickedAtDuration));

            for (TrafficLightSchedule trafficLightSchedule : sorted) {
                System.out.println("intersection " + intersection.id + " street prior = " + trafficLightSchedule.incomingStreet.name + " duration picked " + trafficLightSchedule.pickedAtDuration);
            }
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
