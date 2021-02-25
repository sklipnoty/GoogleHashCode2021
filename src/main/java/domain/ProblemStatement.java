package domain;

import java.util.List;
import java.util.Map;

public class ProblemStatement {
    public Integer durationOfSimulation;
    public Integer numberOfIntersections;
    public Integer numberOfStreets;
    public Integer numberOfCars;
    public Integer bonusPoints;

    public List<Car> allCars;
    public List<Street> streets;
    public Map<Integer, Intersection> intersectionMap;
    public Map<String, Street> streetMap;


    public void initProblem(List<Car> allCars, List<Street> streets, int durationOfSimulation, int numberOfIntersections, int bonusPoints, Map<Integer, Intersection> intersectionMap, Map<String, Street> streetMap) {
        this.durationOfSimulation = durationOfSimulation;
        this.numberOfIntersections = numberOfIntersections;
        this.bonusPoints = bonusPoints;

        this.allCars = allCars;
        this.streets = streets;
        this.intersectionMap = intersectionMap;
        this.streetMap = streetMap;
    }

    @Override
    public String toString() {
        return "ProblemStatement{" +
                "durationOfSimulation=" + durationOfSimulation +
                ", numberOfIntersections=" + numberOfIntersections +
                ", numberOfStreets=" + numberOfStreets +
                ", numberOfCars=" + numberOfCars +
                ", bonusPoints=" + bonusPoints +
                ", allCars=" + allCars +
                ", streets=" + streets +
                ", intersectionMap=" + intersectionMap +
                ", streetMap=" + streetMap +
                '}';
    }
}
