package domain;

import java.util.List;
import java.util.Objects;

public class Car {

    public Integer numberOfStreets;
    public List<Street> streetList;
    public Street currentStreet;
    public Integer id;

    public Integer currentNeededDuration = 0;

    public Car(int id, int numberOfStreets, List<Street> streetList, Street startingStreet) {
        this.numberOfStreets = numberOfStreets;
        this.streetList = streetList;
        this.currentStreet = startingStreet;
        this.id = id;
    }

    public Integer calcPathCost() {
        int cost = 0;

        for(Street street : streetList) {
            cost += street.time;
        }

        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getCurrentNeededDuration() {
        return currentNeededDuration;
    }

    public void setCurrentNeededDuration(Integer currentNeededDuration) {
        this.currentNeededDuration = currentNeededDuration;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberOfStreets=" + numberOfStreets +
                ", currentNeededDuration=" + currentNeededDuration +
                '}';
    }
}
